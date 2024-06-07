import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIExtensions {
    public static final String INT_PATTERN = "^[0-9]+$";
    public static final String FLOAT_PATTERN = "^[0-9]+((\\.|\\,)[0-9]+)?$";

    private static boolean isValueCorrect(String value, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(value);
        return matcher.find();
    }

    public static JTextField createTextField(int maxLength, String defaultText, String textPattern) {
        JTextField textField = new JTextField();
        textField.setDocument(new FixedLengthDocument(maxLength));
        textField.setText(defaultText);
        textField.addActionListener(e -> {
            if (isValueCorrect(textField.getText(), textPattern)) {
                System.out.println("correct");
            }
            else {
                System.out.println("incorrect");
            }
        });
        return textField;
    }

    /**
     *
     * Возможно, нужно изменить
     */
    public static JSlider createSlider(JLabel valueLabel, int minValue, int maxValue) {
        JSlider slider = new JSlider(minValue, maxValue, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(
                event -> valueLabel.setText("P = " + slider.getValue() / 10.0f)
        );
        return slider;
    }
}
