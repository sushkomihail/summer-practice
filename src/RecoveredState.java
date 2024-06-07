import java.awt.*;

public class RecoveredState implements UnitState {
    private final Unit unit;

    private static final Color UNIT_COLOR = new Color(113, 179, 208);

    public RecoveredState(Unit unit) {
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
