import java.awt.*;

public class Unit {
    private final Vector position;
    private Vector targetPosition;
    private Vector moveDirection;
    private Virus virus;
    private float sneezeAccumulator;

    private static final float SPEED = 50;

    public Unit() {
        position = SimulationPanel.getRandomPosition();
        targetPosition = position;
    }

    public Vector getPosition() {
        return position;
    }

    public Virus getVirus() {
        return virus;
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    public boolean isInfected() {
        return virus != null;
    }

    public void move(float deltaTime) {
        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5f) {
            targetPosition = SimulationPanel.getRandomPosition();
            moveDirection = Vector.subtract(targetPosition, position).normalize();
        }

        float deltaX = moveDirection.getX() * SPEED * deltaTime;
        float deltaY = moveDirection.getY() * SPEED * deltaTime;
        position.setX(position.getX() + deltaX);
        position.setY(position.getY() + deltaY);
    }

    public boolean trySneeze(float deltaTime) {
        if (!isInfected()) {
            return false;
        }

        sneezeAccumulator += deltaTime;
        return sneezeAccumulator >= virus.getSneezingInterval();
    }

    public void draw(Graphics2D graphics) {
        int x;
        int y;

        if (isInfected()) {
            graphics.setColor(Config.VIRUS_COLOR);
            x = (int) (position.getX() - virus.getInfectionRadius());
            y = (int) (position.getY() - virus.getInfectionRadius());
            graphics.fillOval(x, y, virus.getInfectionRadius() * 2, virus.getInfectionRadius() * 2);
            graphics.setColor(Config.INFECTED_UNIT_COLOR);
        }

        x = (int) (position.getX() - Config.UNIT_DIAMETER / 2);
        y = (int) (position.getY() - Config.UNIT_DIAMETER / 2);
        graphics.fillOval(x, y, Config.UNIT_DIAMETER, Config.UNIT_DIAMETER);
        graphics.setColor(Config.UNIT_COLOR);
    }
}
