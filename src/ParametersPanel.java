import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ParametersPanel extends JPanel {
    private JButton simulationControlButton;

    private static final Color BACKGROUND_COLOR = new Color(0x98B62D);
    private static final String[] VIRUSES = {
            "Чума",
            "Оспа",
            "Холера",
            "Эбола",
            "СПИД",
            "Коронавирус"
    };
    private static final String START_SIMULATION_TEXT = "Начать симуляцию";
    private static final String STOP_SIMULATION_TEXT = "Остановить симуляцию";

    public ParametersPanel() {
        //setBackground(BACKGROUND_COLOR);
        setBorder(new TitledBorder("Параметры симуляции"));
        setLayout(new BorderLayout());

        JPanel parameterFieldsPanel = new JPanel();
        parameterFieldsPanel.setLayout(new BoxLayout(parameterFieldsPanel, BoxLayout.Y_AXIS));
        parameterFieldsPanel.add(new JLabel("Популяция"));
        parameterFieldsPanel.add(new JTextField());
        parameterFieldsPanel.add(new JLabel("Скорость перемещения юнитов"));
        parameterFieldsPanel.add(new JSlider(10, 100));
        parameterFieldsPanel.add(new JLabel("Вирус"));
        JComboBox<String> comboBox = new JComboBox<>(VIRUSES);
        parameterFieldsPanel.add(comboBox);
        initializeSimulationControlButton();
        parameterFieldsPanel.add(simulationControlButton);

        add(parameterFieldsPanel, BorderLayout.NORTH);
    }

    private void initializeSimulationControlButton() {
        String startButtonText = Simulation.getInstance().isRunning() ? STOP_SIMULATION_TEXT : START_SIMULATION_TEXT;
        simulationControlButton = new JButton(startButtonText);

        simulationControlButton.addActionListener(event -> {
            boolean isRunning = Simulation.getInstance().isRunning();
            //Simulation.getInstance().setRunning(!isRunning);

            if (Simulation.getInstance().isRunning()) {
                simulationControlButton.setText(STOP_SIMULATION_TEXT);
            }
            else {
                simulationControlButton.setText(START_SIMULATION_TEXT);
            }
        });
    }
}
