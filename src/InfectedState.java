import java.awt.*;
import java.util.ArrayList;

public class InfectedState implements UnitState {
    private final DrawingPanel isolationPanel;
    private final Unit unit;
    private float isolationAccumulator;
    private float infectionAccumulator;
    private float recoveryAccumulator;
    private boolean isIsolated;

    private static final Color UNIT_COLOR = new Color(214, 60, 51);
    private static final Color INFECTION_AREA_COLOR = new Color(255, 115, 115, 100);

    public InfectedState(Unit unit, DrawingPanel isolationPanel) {
        this.unit = unit;
        this.isolationPanel = isolationPanel;
        //tryIsolate(unit);
    }

    private boolean canIsolate(float deltaTime) {
        isolationAccumulator += deltaTime;
        return isolationAccumulator >= unit.getVirus().getDetectionTime();
    }

    private void tryIsolate(Unit unit, float deltaTime) {
        if (!unit.useIsolation() || !canIsolate(deltaTime)) {
            return;
        }

        float isolationProbability = (float) Math.random();

        if (isolationProbability <= unit.getIsolationProbability()) {
            unit.setDrawingPanel(isolationPanel);
        }

        isIsolated = true;
    }

    private boolean canInfect(float deltaTime) {
        infectionAccumulator += deltaTime;

        if (infectionAccumulator >= unit.getVirus().getInfectionInterval()) {
            infectionAccumulator = 0;
            return true;
        }

        return false;
    }

    private void tryInfect(float deltaTime) {
        if (!canInfect(deltaTime) || unit.getDrawingPanel() instanceof IsolationPanel) {
            return;
        }

        ArrayList<Unit> units = UnitsManager.getInstance().getUnits();

        for (Unit unit : units) {
            if (unit.getState() instanceof InfectedState || unit.getState() instanceof RecoveredState) {
                continue;
            }

            float distanceToCarrier = Vector.getDistance(this.unit.getPosition(), unit.getPosition());

            if (distanceToCarrier <= this.unit.getVirus().getInfectionRadius()) {
                float infectionProbability = (float) Math.random();

                if (infectionProbability <= this.unit.getVirus().getInfectionProbability()) {
                    unit.setState(new InfectedState(unit, isolationPanel));
                    //tryIsolate(deltaTime, unit);
                }
            }
        }
    }

    private boolean isRecovered(float deltaTime) {
        recoveryAccumulator += deltaTime;
        return recoveryAccumulator >= unit.getVirus().getRecoveryTime();
    }

    private void tryRecover(float deltaTime) {
        if (isRecovered(deltaTime)) {
            unit.setState(new RecoveredState(unit));
        }
    }

    @Override
    public void update(float deltaTime) {
        if (!isIsolated) {
            tryIsolate(unit, deltaTime);
        }

        unit.move(deltaTime);
        tryInfect(deltaTime);
        tryRecover(deltaTime);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(UNIT_COLOR);
        unit.draw(graphics);

        int x = (int) (unit.getPosition().getX() - unit.getVirus().getInfectionRadius());
        int y = (int) (unit.getPosition().getY() - unit.getVirus().getInfectionRadius());
        graphics.setColor(INFECTION_AREA_COLOR);
        graphics.fillOval(x, y, unit.getVirus().getInfectionRadius() * 2, unit.getVirus().getInfectionRadius() * 2);
    }
}
