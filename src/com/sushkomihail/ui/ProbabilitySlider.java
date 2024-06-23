package com.sushkomihail.ui;

import javax.swing.*;

public class ProbabilitySlider extends JPanel {
    private static final int MIN = 0;
    private static final int MAX = 10;
    private static final int TICK_SPACING = 1;

    private final JSlider slider = new JSlider(MIN, MAX);

    public ProbabilitySlider(float defaultValue) {
        JLabel label = new JLabel();
        label.setText("P = " + defaultValue);
        add(label);
        slider.setValue((int) (defaultValue * 10));
        slider.setMajorTickSpacing(TICK_SPACING);
        slider.setPaintTicks(true);
        slider.addChangeListener(l -> label.setText("P = " + slider.getValue() / 10.0f));
        add(slider);
    }

    public float getValue() {
        return slider.getValue() / 10.0f;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        UiExtensions.setEnabledComponentsOf(this, isEnabled);
    }
}
