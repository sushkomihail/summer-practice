package com.sushkomihail.unit;

import com.sushkomihail.graphics.Graphics;

import java.awt.*;

public class UninfectedState implements UnitState {
    private final Unit unit;

    private static final Color UNIT_COLOR = new Color(64, 64, 64);

    public UninfectedState(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void render(Graphics2D graphics) {
        Graphics.fillCircle(graphics, unit.getPosition(), unit.getRadius(), UNIT_COLOR);
    }
}
