public class Simulation implements Runnable {
    private final Virus virus = new Virus();
    private int population = 100;
    private float unitMoveSpeed = 1F;
    private boolean isRunning = true;

    public Unit[] units;

    private static Simulation instance;

    public Simulation() {
        createPopulation();
        new Thread(this).start();
        instance = this;
    }

    public static Simulation getInstance() {
        if (instance == null) {
            instance = new Simulation();
        }

        return instance;
    }

    private void createPopulation() {
        units = new Unit[population];

        for (int i = 0; i < population - 1; i++) {
            units[i] = new Unit();
        }

        units[population - 1] = new InfectedUnit(virus);
    }

    private void tryInfect(Vector carrierPosition) {
        for (int i = 0; i < population; i++) {
            if (units[i] instanceof InfectedUnit || units[i] instanceof RecoveredUnit) {
                continue;
            }

            float distanceToCarrier = Vector.getDistance(carrierPosition, units[i].getPosition());

            if (distanceToCarrier <= virus.getInfectionRadius()) {
                float infectionProbability = (float) Math.random();

                if (infectionProbability <= virus.getInfectionProbability()) {
                    InfectedUnit infectedUnit = new InfectedUnit(virus);
                    infectedUnit.copyMovement(units[i]);    // ...
                    units[i] = infectedUnit;
                }
            }
        }
    }

    private void update(float deltaTime) {
        for (int i = 0; i < population; i++) {
            units[i].move(deltaTime);

            if (units[i] instanceof InfectedUnit infectedUnit) {
                if (infectedUnit.canInfect(deltaTime)) {
                    tryInfect(infectedUnit.getPosition());
                }

                if (infectedUnit.tryRecover(deltaTime)) {
                    RecoveredUnit recoveredUnit = new RecoveredUnit();
                    recoveredUnit.copyMovement(units[i]);
                    units[i] = recoveredUnit;
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        while (isRunning){
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;

            if (deltaTime >= Config.TARGET_TIME_BETWEEN_FRAMES) {
                update(deltaTime / 1000000000.0f);
                SimulationPanel.getInstance().repaint();
                previousTime = currentTime;
            }
        }
    }
}
