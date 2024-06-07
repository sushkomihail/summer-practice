import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ParametersPanel extends JPanel {
    private final Simulation simulation;
    private final VirusSettingsPanel virusSettingsPanel;
    private final UnitSettingsPanel unitSettingsPanel;

    public ParametersPanel(Simulation simulation, UnitSettingsPanel unitSettingsPanel, VirusSettingsPanel virusSettingsPanel) {
        this.simulation = simulation;

        setBorder(new TitledBorder("Параметры симуляции"));
        setLayout(new BorderLayout());

        JPanel parameterFieldsPanel = new JPanel();
        parameterFieldsPanel.setLayout(new BoxLayout(parameterFieldsPanel, BoxLayout.Y_AXIS));
        this.virusSettingsPanel = virusSettingsPanel;
        parameterFieldsPanel.add(virusSettingsPanel);
        this.unitSettingsPanel = unitSettingsPanel;
        parameterFieldsPanel.add(unitSettingsPanel);
        parameterFieldsPanel.add(createSimulationControlButton());

        add(parameterFieldsPanel, BorderLayout.NORTH);
    }

    private JButton createSimulationControlButton() {
        String startText = "Начать симуляцию";
        String stopText = "Остановить симуляцию";
        String startButtonText = simulation.isRunning() ? stopText : startText;
        JButton simulationControlButton = new JButton(startButtonText);
        simulationControlButton.addActionListener(event -> {
            if (simulation.isRunning()) {
                simulation.stop();
                simulation.reset();
                simulationControlButton.setText(startText);
            }
            else {
                unitSettingsPanel.setUpUnit();
                virusSettingsPanel.setUpVirus();
                simulation.start();
                simulationControlButton.setText(stopText);
            }
        });
        return simulationControlButton;
    }
}
