import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class SocietyPanel extends DrawingPanel {
    public SocietyPanel(ArrayList<Unit> units) {
        super(units);
        setDoubleBuffered(true);
        setBorder(new TitledBorder("Общество"));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Unit unit : UnitsManager.getInstance().getUnits()) {
            if (unit.getDrawingPanel() instanceof SocietyPanel) {
                unit.drawState(graphics2D);
            }
        }
    }
}
