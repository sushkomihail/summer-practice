package com.sushkomihail.window;

import com.sushkomihail.simulation.Statistics;
import com.sushkomihail.ui.Fonts;
import com.sushkomihail.unit.UnitTitle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class StatisticsView extends JPanel {
    private final JLabel uninfectedCountLabel = new JLabel(UnitTitle.UNINFECTED.getTitle("х") + "    0");
    private final JLabel infectedCountLabel = new JLabel(UnitTitle.INFECTED.getTitle("х") + "    0");
    private final JLabel recoveredCountLabel = new JLabel(UnitTitle.RECOVERED.getTitle("х") + "    0");
    private final JButton chartButton = new JButton("График распространения вируса");

    public StatisticsView(JFrame chartWindow) {
        TitledBorder border = new TitledBorder("Статистика");
        border.setTitleFont(Fonts.H2.getFont());
        setBorder(border);
        setLayout(new BorderLayout());

        JPanel dataContainer = new JPanel();
        dataContainer.setLayout(new BoxLayout(dataContainer, BoxLayout.Y_AXIS));
        dataContainer.add(uninfectedCountLabel);
        dataContainer.add(infectedCountLabel);
        dataContainer.add(recoveredCountLabel);

        chartButton.setEnabled(false);
        chartButton.addActionListener(l -> chartWindow.setVisible(true));

        add(chartButton, BorderLayout.CENTER);
        add(dataContainer, BorderLayout.WEST);
    }

    public JButton getChartButton() {
        return chartButton;
    }

    private void updateStatisticsValueLabelText(JLabel label, String title, int statisticsValue) {
        label.setText(title + "    " + statisticsValue);
    }

    public void update(Statistics statistics) {
        updateStatisticsValueLabelText(
                uninfectedCountLabel, UnitTitle.UNINFECTED.getTitle("х"), statistics.getUninfectedCount()
        );
        updateStatisticsValueLabelText(
                infectedCountLabel, UnitTitle.INFECTED.getTitle("х"), statistics.getInfectedCount()
        );
        updateStatisticsValueLabelText(
                recoveredCountLabel, UnitTitle.RECOVERED.getTitle("х"), statistics.getRecoveredCount()
        );
    }
}
