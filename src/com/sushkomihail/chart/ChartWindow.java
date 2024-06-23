package com.sushkomihail.chart;

import com.sushkomihail.graphics.Renderer;
import com.sushkomihail.ui.Fonts;
import com.sushkomihail.window.Canvas;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChartWindow extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private final Canvas chartCanvas = new Canvas(new Renderer());
    private final ChartLegend legend = new ChartLegend();

    public ChartWindow() {
        setTitle("График развития эпидемии");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        JPanel container = new JPanel();
        TitledBorder border = new TitledBorder("Изменение количества юнитов с течением времени");
        border.setTitleFont(Fonts.H2.getFont());
        container.setBorder(border);
        container.setLayout(new BorderLayout());

        container.add(legend, BorderLayout.SOUTH);

        chartCanvas.setBackground(Color.WHITE);
        container.add(chartCanvas, BorderLayout.CENTER);

        add(container);
    }

    public Canvas getChartCanvas() {
        return chartCanvas;
    }

    public ChartLegend getLegend() {
        return legend;
    }
}
