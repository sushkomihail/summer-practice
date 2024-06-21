package com.sushkomihail.window;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.simulation.Simulation;
import com.sushkomihail.ui.Fonts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 700;

    private final Canvas societyCanvas = new SocietyCanvas(new Renderer());
    private final Canvas isolationCanvas = new IsolationCanvas(new Renderer());
    private final StatisticsView statisticsView = new StatisticsView();
    private final Settings settings = new Settings(this);

    public MainWindow(Simulation simulation) {
        setTitle("Симуляция эпидемии");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar(simulation));

        JPanel simulationContainer = new JPanel();
        TitledBorder simulationBorder = new TitledBorder("Симуляция");
        simulationBorder.setTitleFont(Fonts.H1.getFont());
        simulationContainer.setBorder(simulationBorder);
        simulationContainer.setLayout(new BorderLayout());
        simulationContainer.add(societyCanvas, BorderLayout.CENTER);
        simulationContainer.add(isolationCanvas, BorderLayout.WEST);
        simulationContainer.add(statisticsView, BorderLayout.NORTH);

        add(simulationContainer, BorderLayout.CENTER);
        add(settings, BorderLayout.EAST);
        setVisible(true);
    }

    public Canvas getSocietyCanvas() {
        return societyCanvas;
    }

    public Canvas getIsolationCanvas() {
        return isolationCanvas;
    }

    public StatisticsView getStatisticsView() {
        return statisticsView;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setIsolationCanvasVisibility(boolean isIsolationUsed) {
        if (isIsolationUsed) {
            setSize(WIDTH + isolationCanvas.getWidth(), getHeight());
        } else {
            setSize(WIDTH, getHeight());
        }

        isolationCanvas.setVisible(isIsolationUsed);
    }
}
