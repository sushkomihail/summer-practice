import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class DrawingPanel extends JPanel {
    private final ArrayList<Unit> units;

    public DrawingPanel(ArrayList<Unit> units) {
        this.units = units;
    }

    public Vector getRandomPosition() {
        Dimension size = getSize();
        int x = (int)(Math.random() * (size.width + 1));
        int y = (int)(Math.random() * (size.height + 1));
        return new Vector(x, y);
    }
}
