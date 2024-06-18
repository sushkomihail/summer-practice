package com.sushkomihail.window;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.math.Random;
import com.sushkomihail.math.Vector;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private final Renderer renderer;

    public Canvas(Renderer renderer) {
         this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Vector getRandomPositionInside(int unitRadius) {
        Dimension size = getSize();
        int x = (int) Random.getFloat(unitRadius, size.width - unitRadius);
        int y = (int) Random.getFloat(unitRadius, size.height - unitRadius);
        return new Vector(x, y);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        renderer.render(graphics2D);
    }
}
