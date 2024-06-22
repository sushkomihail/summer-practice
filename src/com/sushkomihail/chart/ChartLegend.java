package com.sushkomihail.chart;

import com.sushkomihail.ui.Fonts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChartLegend extends JPanel {
    private static final int COLOR_PANEL_SIZE = 10;

    public ChartLegend() {
        TitledBorder border = new TitledBorder("Легенда");
        border.setTitleFont(Fonts.H2.getFont());
        setBorder(border);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    private JPanel createDesignation(Color color, String designation) {
        JPanel designationContainer = new JPanel();

        JPanel colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(COLOR_PANEL_SIZE, COLOR_PANEL_SIZE));
        colorPanel.setBackground(color);
        designationContainer.add(colorPanel);

        JLabel designationLabel = new JLabel(" -- " + designation);
        designationContainer.add(designationLabel);

        return designationContainer;
    }

    public void addDesignation(Color color, String designation) {
        add(createDesignation(color, designation));
    }
}
