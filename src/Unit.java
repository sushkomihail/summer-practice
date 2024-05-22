import java.awt.*;

public class Unit {
    private final Vector position;
    private Vector targetPosition;
    private Vector moveDirection;

    public Unit() {
        position = getRandomPosition();
        targetPosition = position;
    }

    private Vector getRandomPosition() {
        int x = (int)(Math.random() * (Config.WINDOW_WIDTH - Config.UNIT_DIAMETER + 1));
        int y = (int)(Math.random() * (Config.WINDOW_HEIGHT - Config.UNIT_DIAMETER + 1));
        return new Vector(x, y);
    }

    public void move(float moveSpeed) {
        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5F) {
            targetPosition = getRandomPosition();
            moveDirection = Vector.subtract(targetPosition, position).normalize();
        }

        float deltaX = moveDirection.getX() * moveSpeed;
        float deltaY = moveDirection.getY() * moveSpeed;
        position.setX(position.getX() + deltaX);
        position.setY(position.getY() + deltaY);
    }

    public void draw(Graphics2D graphics) {
        graphics.fillOval((int) position.getX(), (int) position.getY(), Config.UNIT_DIAMETER, Config.UNIT_DIAMETER);
//        graphics.setColor(Color.ORANGE);
//        graphics.fillOval((int) targetPosition.getX(), (int) targetPosition.getY(), Config.UNIT_DIAMETER, Config.UNIT_DIAMETER);
//        graphics.setColor(Color.BLACK);
    }
}
