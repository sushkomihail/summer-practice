package com.sushkomihail.window;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.ui.Fonts;

import javax.swing.border.TitledBorder;

public class SocietyCanvas extends Canvas {
    public SocietyCanvas(Renderer renderer) {
        super(renderer);
        TitledBorder border = new TitledBorder("Общество");
        border.setTitleFont(Fonts.H2.getFont());
        setBorder(border);
    }
}
