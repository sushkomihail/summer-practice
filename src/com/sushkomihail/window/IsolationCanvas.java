package com.sushkomihail.window;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.ui.Fonts;

import javax.swing.border.TitledBorder;
import java.awt.*;

public class IsolationCanvas extends Canvas {
    private static final int WIDTH = 200;

    public IsolationCanvas(Renderer renderer) {
        super(renderer);
        setPreferredSize(new Dimension(WIDTH, 0));
        TitledBorder border = new TitledBorder("Карантин");
        border.setTitleFont(Fonts.H1.getFont());
        setBorder(border);
    }
}
