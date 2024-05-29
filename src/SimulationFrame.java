import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private final JPanel simulationPanel;
    private final JPanel parametersPanel;

    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 700;

    public SimulationFrame() {
        setTitle("Симуляция эпидемии");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        simulationPanel = new SimulationPanel();
        add(simulationPanel, BorderLayout.CENTER);

        parametersPanel = new ParametersPanel();
        add(parametersPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public JPanel getSimulationPanel() {
        return simulationPanel;
    }

    public JPanel getParametersPanel() {
        return parametersPanel;
    }
}
