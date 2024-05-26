import javax.swing.*;
import java.util.ArrayList;

public class SimulationPresenter implements Runnable {
    private final JPanel simulationPanel;
    public static Unit[] units;
    private ArrayList<Unit> infectedUnits;
    public static Virus virus;
    private int virusesCount;
    private boolean isRunning;
    private int population = 50;
    private float unitMoveSpeed = 1F;

    public SimulationPresenter(JPanel simulationPanel) {
        this.simulationPanel = simulationPanel;
        createPopulation();
        createVirus();
        new Thread(this).start();
    }

    private void createPopulation() {
        units = new Unit[population];

        for (int i = 0; i < population; i++) {
            units[i] = new Unit();
        }
    }

    private void createVirus() {
        virus = new Virus(30, 5, 0.3f);
        units[0].setVirus(virus);
    }

    private void tryInfect(Unit carrier) {
        for (Unit unit : units) {
            if (unit.isInfected()) {
                continue;
            }

            float distanceToCarrier = Vector.getDistance(carrier.getPosition(), unit.getPosition());

            if (distanceToCarrier <= carrier.getVirus().getInfectionRadius()) {
                float infectionProbability = (int) (Math.random() * 101);

                if (infectionProbability <= carrier.getVirus().getInfectionProbability()) {
                    unit.setVirus(carrier.getVirus());
                }
            }
        }
    }

    private void update(float deltaTime) {
        for (Unit unit : units) {
            unit.move(deltaTime);

            if (unit.trySneeze(deltaTime)) {
                tryInfect(unit);
            }
        }
    }

    // ...
    @Override
    public void run() {
        isRunning = true;
        long previousTime = System.nanoTime();

        while (isRunning){
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;

            if (deltaTime >= Config.TARGET_TIME_BETWEEN_FRAMES) {
                update(deltaTime / 1000000000.0f);
                simulationPanel.repaint();
                previousTime = currentTime;
            }
        }
    }
}
