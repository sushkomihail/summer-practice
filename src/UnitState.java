import java.awt.*;

public interface UnitState {
    void update(float deltaTime);

    void draw(Graphics2D graphics);
}
