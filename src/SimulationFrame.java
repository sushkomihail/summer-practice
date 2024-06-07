import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 700;

    public SimulationFrame(JPanel societyPanel, JPanel isolationPanel, JPanel parametersPanel) {
        setTitle("Симуляция эпидемии");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(societyPanel, BorderLayout.CENTER);
        add(isolationPanel, BorderLayout.WEST);
        add(parametersPanel, BorderLayout.EAST);
        setVisible(true);
    }
}
