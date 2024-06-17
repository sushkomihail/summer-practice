package com.sushkomihail.window;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private final Canvas societyCanvas = new SocietyCanvas(new Renderer());
    private final Canvas isolationCanvas = new IsolationCanvas(new Renderer());
    private final Settings settings;

    public Window(Simulation simulation) {
        setTitle("Симуляция эпидемии");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(societyCanvas, BorderLayout.CENTER);
        add(isolationCanvas, BorderLayout.WEST);
        settings = new Settings(this, simulation);
        add(settings, BorderLayout.EAST);
        setVisible(true);
    }

    public Canvas getSocietyCanvas() {
        return societyCanvas;
    }

    public Canvas getIsolationCanvas() {
        return isolationCanvas;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setIsolationCanvasVisibility(boolean isIsolationUsed) {
        isolationCanvas.setVisible(isIsolationUsed);
    }
}
