import java.awt.*;

public class InfectedUnit extends Unit {
    private final Virus virus;
    private float infectionAccumulator;
    private float recoveryAccumulator;

    private static final Color INFECTION_AREA_COLOR = new Color(255, 115, 115, 100);

    public InfectedUnit(Virus virus) {
        this.virus = virus;
        unitColor = new Color(214, 60, 51);
    }

    public boolean canInfect(float deltaTime) {
        infectionAccumulator += deltaTime;

        if (infectionAccumulator >= virus.getInfectionInterval()) {
            infectionAccumulator = 0;
            return true;
        }

        return false;
    }

    public boolean tryRecover(float deltaTime) {
        recoveryAccumulator += deltaTime;
        return recoveryAccumulator >= virus.getRecoveryTime();
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        int x = (int) (position.getX() - virus.getInfectionRadius());
        int y = (int) (position.getY() - virus.getInfectionRadius());
        graphics.setColor(INFECTION_AREA_COLOR);
        graphics.fillOval(x, y, virus.getInfectionRadius() * 2, virus.getInfectionRadius() * 2);
    }
}
