package com.sushkomihail.window;

import com.sushkomihail.ui.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VirusSettings extends JPanel {
    private final MaskedTextField infectionRadiusText = new MaskedTextField(2, "30", MaskType.INT.getMask());
    private final MaskedTextField infectionIntervalText = new MaskedTextField(5, "0.5", MaskType.FLOAT.getMask());
    private final ProbabilitySlider infectionProbabilitySlider = new ProbabilitySlider(0.5f);
    private final MaskedTextField detectionTimeText = new MaskedTextField(5, "1", MaskType.FLOAT.getMask());
    private final MaskedTextField recoveryTimeText = new MaskedTextField(5, "4", MaskType.FLOAT.getMask());

    public VirusSettings() {
        TitledBorder border = new TitledBorder("Параметры вируса");
        border.setTitleFont(Fonts.H2.getFont());
        setBorder(border);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Радиус заражения"));
        add(infectionRadiusText);

        add(new JLabel("Интервал заражения"));
        add(infectionIntervalText);

        add(new JLabel("Вероятность заражения"));
        add(infectionProbabilitySlider);

        add(new JLabel("Время обнаружения"));
        add(detectionTimeText);

        add(new JLabel("Время выздоровления"));
        add(recoveryTimeText);

        UiExtensions.setAlignmentXForAllComponentsInContainer(this, LEFT_ALIGNMENT);
    }

    public int getInfectionRadius() {
        return Integer.parseInt(infectionRadiusText.getText());
    }

    public float getInfectionInterval() {
        return  Float.parseFloat(infectionIntervalText.getText());
    }

    public float getInfectionProbability() {
        return infectionProbabilitySlider.getValue();
    }

    public float getDetectionTime() {
        return Float.parseFloat(detectionTimeText.getText());
    }

    public float getRecoveryTime() {
        return Float.parseFloat(recoveryTimeText.getText());
    }
}
