import java.awt.*;

public class UninfectedState implements UnitState {
    private final Unit unit;

    private static final Color UNIT_COLOR = new Color(64, 64, 64);

    public UninfectedState(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void update(float deltaTime) {
        unit.move(deltaTime);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(UNIT_COLOR);
        unit.draw(graphics);
    }
}
