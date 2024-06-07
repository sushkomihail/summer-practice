import java.awt.*;

public class Unit implements Cloneable {
    private DrawingPanel drawingPanel;
    private Vector position;
    private Vector targetPosition;
    private Vector moveDirection;
    private int speed = 50;
    private final Virus virus;
    private float isolationProbability = 0.5f;
    private boolean useIsolation = true;
    private UnitState state;

    private static final int UNIT_RADIUS = 8;

    public Unit(Virus virus) {
        this.virus = virus;
    }

    public Virus getVirus() {
        return virus;
    }

    public UnitState getState() {
        return state;
    }

    public int getSpeed() {
        return speed;
    }

    public Vector getPosition() {
        return position;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setState(UnitState state) {
        this.state = state;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        position = drawingPanel.getRandomPosition();
        targetPosition = position;
    }

    public float getIsolationProbability() {
        return isolationProbability;
    }

    public boolean useIsolation() {
        return useIsolation;
    }

    public void move(float deltaTime) {
        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5f) {
            targetPosition = drawingPanel.getRandomPosition();
            moveDirection = Vector.subtract(targetPosition, position).normalize();
        }

        float deltaX = moveDirection.getX() * speed * deltaTime;
        float deltaY = moveDirection.getY() * speed * deltaTime;
        position.setX(position.getX() + deltaX);
        position.setY(position.getY() + deltaY);
    }

    public void draw(Graphics2D graphics) {
        int x = (int) (position.getX() - UNIT_RADIUS);
        int y = (int) (position.getY() - UNIT_RADIUS);
        graphics.fillOval(x, y, UNIT_RADIUS * 2, UNIT_RADIUS * 2);
    }

    public void updateState(float deltaTime) {
        state.update(deltaTime);
    }

    public void drawState(Graphics2D graphics) {
        state.draw(graphics);
    }

    @Override
    public Unit clone() {
        try {
            return (Unit) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
