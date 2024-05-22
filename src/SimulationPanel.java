import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    public SimulationPanel() {
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Unit unit : SimulationPresenter.units) {
            unit.draw(graphics2D);
        }
    }
}
