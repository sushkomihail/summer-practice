import java.awt.*;

public class Unit {
    private Vector targetPosition;
    private Vector moveDirection;
    protected Vector position;
    protected Color unitColor;

    private static final float SPEED = 50;
    protected static final int UNIT_RADIUS = 8;

    public Unit() {
        unitColor = new Color(64, 64, 64);
        position = SimulationPanel.getInstance().getRandomPosition();
        targetPosition = position;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getTargetPosition() {
        return targetPosition;
    }

    public Vector getMoveDirection() {
        return moveDirection;
    }

    public void copyMovement(Unit sourceUnit) {
        targetPosition = sourceUnit.getTargetPosition();
        moveDirection = sourceUnit.getMoveDirection();
        position = sourceUnit.getPosition();
    }

    public void move(float deltaTime) {
        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5f) {
            targetPosition = SimulationPanel.getInstance().getRandomPosition();
            moveDirection = Vector.subtract(targetPosition, position).normalize();
        }

        float deltaX = moveDirection.getX() * SPEED * deltaTime;
        float deltaY = moveDirection.getY() * SPEED * deltaTime;
        position.setX(position.getX() + deltaX);
        position.setY(position.getY() + deltaY);
    }

    public void draw(Graphics2D graphics) {
        int x = (int) (position.getX() - UNIT_RADIUS);
        int y = (int) (position.getY() - UNIT_RADIUS);
        graphics.setColor(unitColor);
        graphics.fillOval(x, y, UNIT_RADIUS * 2, UNIT_RADIUS * 2);
    }
}
