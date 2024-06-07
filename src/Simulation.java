import java.util.Iterator;

public class Simulation implements Runnable {
    private DrawingPanel societyPanel;
    private DrawingPanel isolationPanel;

    private Virus virus;
    private Unit unit;
    private int population = 100;
    private boolean useDistancing = false;
    private boolean useIsolation = true;
    private boolean isRunning = false;
    private Thread simulationThread;

    public Simulation() {
        initializeModels();
        initializeView();
        createPopulation();
    }

    private void initializeModels() {
        virus = new Virus();
        unit = new Unit(virus);
    }

    private void initializeView() {
        societyPanel = new SocietyPanel(UnitsManager.getInstance().getUnits());
        isolationPanel = new IsolationPanel(UnitsManager.getInstance().getUnits());
        new SimulationFrame(
                societyPanel,
                isolationPanel,
                new ParametersPanel(this, new UnitSettingsPanel(unit), new VirusSettingsPanel(virus))
        );
    }

    public boolean useIsolation() {
        return useIsolation;
    }

    public Virus getVirus() {
        return virus;
    }

    public Unit getUnit() {
        return unit;
    }

    private void createPopulation() {
        UnitsManager.getInstance().getUnits().clear();
        UnitsManager.getInstance().getUnits().clear();

        for (int i = 0; i < population - 1; i++) {
            Unit newUnit = unit.clone();
            newUnit.setDrawingPanel(societyPanel);
            newUnit.setState(new UninfectedState(newUnit));
            UnitsManager.getInstance().getUnits().add(newUnit);
        }

        Unit infectedUnit = unit.clone();
        infectedUnit.setDrawingPanel(societyPanel);
        infectedUnit.setState(new InfectedState(infectedUnit, isolationPanel));
        UnitsManager.getInstance().getUnits().add(infectedUnit);
    }

    private void update(float deltaTime) {
        for (Unit unit : UnitsManager.getInstance().getUnits()) {
            unit.updateState(deltaTime);
        }
    }

    private void repaint() {
        societyPanel.repaint();

        if (useIsolation) {
            isolationPanel.repaint();
        }
    }

    public void start() {
        createPopulation();
        isRunning = true;

        if (simulationThread == null || simulationThread.isInterrupted()) {
            simulationThread = new Thread(this);
        }

        simulationThread.start();
    }

    public void stop() {
        isRunning = false;
        simulationThread.interrupt();
    }

    public void reset() {
        societyPanel.repaint();

        if (useIsolation) {
            isolationPanel.repaint();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;

            if (deltaTime >= Config.TARGET_TIME_BETWEEN_FRAMES) {
                update(deltaTime / 1000000000.0f);
                repaint();
                previousTime = currentTime;
            }
        }
    }
}
