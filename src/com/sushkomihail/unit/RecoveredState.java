package com.sushkomihail.unit;

import com.sushkomihail.graphics.Graphics;

import java.awt.*;

public class RecoveredState implements UnitState {
    private final Unit unit;

    private static final Color UNIT_COLOR = new Color(113, 179, 208);

    public RecoveredState(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void render(Graphics2D graphics) {
        Graphics.fillCircle(graphics, unit.getPosition(), unit.getRadius(), UNIT_COLOR);
    }
}
