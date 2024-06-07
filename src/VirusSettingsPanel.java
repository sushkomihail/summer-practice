import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VirusSettingsPanel extends JPanel {
    private final Virus virus;
    private final JTextField infectionRadiusTextField;
    private final JTextField infectionIntervalTextField;
    private final JSlider infectionProbabilitySlider;
    private final JTextField recoveryTimeTextField;

    public VirusSettingsPanel(Virus virus) {
        this.virus = virus;

        setBorder(new TitledBorder("Параметры вируса"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Радиус заражения"));
        String defaultText = Integer.toString(virus.getInfectionRadius());
        infectionRadiusTextField = UIExtensions.createTextField(2, defaultText, UIExtensions.INT_PATTERN);
        add(infectionRadiusTextField);

        add(new JLabel("Интервал заражения"));
        defaultText = Float.toString(virus.getInfectionInterval());
        infectionIntervalTextField = UIExtensions.createTextField(5, defaultText, UIExtensions.FLOAT_PATTERN);
        add(infectionIntervalTextField);

        add(new JLabel("Вероятность заражения"));
        JPanel probabilityPanel = new JPanel();
        JLabel probabilityLabel = new JLabel();
        infectionProbabilitySlider = UIExtensions.createSlider(probabilityLabel, 0, 10);
        probabilityLabel.setText("P = " + infectionProbabilitySlider.getValue() / 10.0f);
        probabilityPanel.add(probabilityLabel);
        probabilityPanel.add(infectionProbabilitySlider);
        add(probabilityPanel);

        add(new JLabel("Время выздоровления"));
        defaultText = Float.toString(virus.getRecoveryTime());
        recoveryTimeTextField = UIExtensions.createTextField(5, defaultText, UIExtensions.FLOAT_PATTERN);
        add(recoveryTimeTextField);
    }

    public void setUpVirus() {
        virus.setInfectionRadius(Integer.parseInt(infectionRadiusTextField.getText()));
        virus.setInfectionInterval(Float.parseFloat(infectionIntervalTextField.getText()));
        virus.setInfectionProbability(infectionProbabilitySlider.getValue() / 10.0f);
        virus.setRecoveryTime(Float.parseFloat(recoveryTimeTextField.getText()));
    }
}
