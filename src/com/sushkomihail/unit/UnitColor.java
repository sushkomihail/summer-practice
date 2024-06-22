package com.sushkomihail.unit;

import java.awt.*;

public enum UnitColor {
    UNINFECTED(new Color(64, 64, 64)),
    INFECTED(new Color(214, 60, 51)),
    RECOVERED(new Color(113, 179, 208));

    private final Color color;

    public Color getColor() {
        return color;
    }

    UnitColor(Color color) {
        this.color = color;
    }
}
