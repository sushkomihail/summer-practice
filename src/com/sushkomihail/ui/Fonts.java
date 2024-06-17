package com.sushkomihail.ui;

import java.awt.*;

public enum Fonts {
    H1(new Font("Arial", Font.BOLD, 14)),
    H2(new Font("Arial", Font.BOLD | Font.ITALIC, 12));

    private final Font font;

    public Font getFont() {
        return font;
    }

    Fonts(Font font) {
        this.font = font;
    }
}
