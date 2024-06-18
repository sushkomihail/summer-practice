package com.sushkomihail.window;

import com.sushkomihail.ui.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VirusSettings extends JPanel {
    private final MaskedTextField infectionRadiusText = new MaskedTextField(2, "30", Mask.INT.getMask());
    private final MaskedTextField infectionIntervalText = new MaskedTextField(3, "0.5", Mask.FLOAT.getMask());
    private final ProbabilitySlider infectionProbabilitySlider = new ProbabilitySlider(0.5f);
    private final MaskedTextField detectionTimeText = new MaskedTextField(3, "1", Mask.FLOAT.getMask());
    private final MaskedTextField recoveryTimeText = new MaskedTextField(3, "4", Mask.FLOAT.getMask());

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
