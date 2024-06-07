import javax.swing.*;
import javax.swing.border.TitledBorder;

public class UnitSettingsPanel extends JPanel {
    private final Unit unit;
    private final JTextField speedTextField;

    public UnitSettingsPanel(Unit unit) {
        this.unit = unit;
        setBorder(new TitledBorder("Параметры юнита"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Скорость"));
        String defaultText = Integer.toString(unit.getSpeed());
        speedTextField = UIExtensions.createTextField(3, defaultText, UIExtensions.INT_PATTERN);
        add(speedTextField);
    }

    public void setUpUnit() {
        unit.setSpeed(Integer.parseInt(speedTextField.getText()));
    }
}