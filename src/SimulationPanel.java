import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    Dimension size;

    public SimulationPanel() {
        setDoubleBuffered(true);
        size = getSize(); // ...
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Unit unit : SimulationPresenter.units) {
            unit.draw(graphics2D);
        }
    }

    public static Vector getRandomPosition() {
        int x = (int)(Math.random() * (688 + 1));
        int y = (int)(Math.random() * (665 + 1));
        return new Vector(x, y);
    }
}
