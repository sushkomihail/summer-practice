import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulationPanel extends JPanel {
    private static SimulationPanel instance;

    public SimulationPanel() {
        setBorder(new TitledBorder("Окно симуляции"));
        setDoubleBuffered(true);
        instance = this;
    }

    public static SimulationPanel getInstance() {
        if (instance == null) {
            instance = new SimulationPanel();
        }

        return instance;
    }

    // ...
    public Vector getRandomPosition() {
        Dimension size = getSize();
        int x = (int)(Math.random() * (size.width + 1));
        int y = (int)(Math.random() * (size.height + 1));
        return new Vector(x, y);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Unit unit : Simulation.getInstance().units) {
            unit.draw(graphics2D);
        }
    }
}
