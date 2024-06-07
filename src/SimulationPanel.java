import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulationPanel extends JPanel {
    public SimulationPanel(JPanel societyPanel, JPanel isolationPanel) {
        setBorder(new TitledBorder("Окно симуляции"));
        setLayout(new BorderLayout());
        add(societyPanel, BorderLayout.CENTER);
        add(isolationPanel, BorderLayout.WEST);
    }
}
