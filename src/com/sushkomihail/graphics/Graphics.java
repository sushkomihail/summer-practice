package com.sushkomihail.graphics;

import com.sushkomihail.math.Vector;

import java.awt.*;

public class Graphics {
    public static void fillCircle(Graphics2D graphics, Vector position, int radius, Color color) {
        int x = (int) (position.getX() - radius);
        int y = (int) (position.getY() - radius);
        graphics.setColor(color);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }
}
