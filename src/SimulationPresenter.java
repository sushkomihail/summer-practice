import javax.swing.*;

public class SimulationPresenter implements Runnable {
    private final JPanel simulationPanel;
    public static Unit[] units;
    private boolean isRunning;
    private int population = 50;
    private float unitMoveSpeed = 1F;

    public SimulationPresenter(JPanel simulationPanel) {
        this.simulationPanel = simulationPanel;
        isRunning = true;
        createPopulation();
        new Thread(this).start();
    }

    private void createPopulation() {
        units = new Unit[population];

        for (int i = 0; i < population; i++) {
            units[i] = new Unit();
        }
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        while (isRunning){
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;

            if (deltaTime >= Config.TARGET_TIME_BETWEEN_FRAMES) {
                for (Unit unit : units) {
                    unit.move(unitMoveSpeed);
                }

                simulationPanel.repaint();
                previousTime = currentTime;
            }
        }
    }
}
