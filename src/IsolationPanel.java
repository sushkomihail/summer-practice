import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class IsolationPanel extends DrawingPanel {
    private static final int WIDTH = 200;

    public IsolationPanel(ArrayList<Unit> units) {
        super(units);
        setDoubleBuffered(true);
        setBorder(new TitledBorder("Карантин"));
        setPreferredSize(new Dimension(WIDTH, 0));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Unit unit : UnitsManager.getInstance().getUnits()) {
            if (unit.getDrawingPanel() instanceof IsolationPanel) {
                unit.drawState(graphics2D);
            }
        }
    }
}
