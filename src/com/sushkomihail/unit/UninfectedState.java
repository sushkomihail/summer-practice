package com.sushkomihail.unit;

import com.sushkomihail.graphics.Graphics;

import java.awt.*;

public class UninfectedState implements UnitState {
    private final Unit unit;

    public UninfectedState(Unit unit) {
        this.unit = unit;
    }

    @Override
    public void render(Graphics2D graphics) {
        Graphics.fillCircle(graphics, unit.getPosition(), unit.getRadius(), UnitColor.UNINFECTED.getColor());
    }
}
