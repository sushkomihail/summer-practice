package com.sushkomihail.window;

import com.sushkomihail.ui.Fonts;
import com.sushkomihail.ui.MaskType;
import com.sushkomihail.ui.MaskedTextField;
import com.sushkomihail.ui.UiExtensions;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class UnitSettings extends JPanel {
    private final JTextField speedText = new MaskedTextField(3, "50", MaskType.INT.getMask());

    public UnitSettings() {
        TitledBorder border = new TitledBorder("Параметры юнита");
        border.setTitleFont(Fonts.H2.getFont());
        setBorder(border);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Скорость"));
        add(speedText);

        UiExtensions.setAlignmentXForAllComponentsInContainer(this, LEFT_ALIGNMENT);
    }

    public int getSpeed() {
        return Integer.parseInt(speedText.getText());
    }
}