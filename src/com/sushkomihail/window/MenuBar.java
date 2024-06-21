package com.sushkomihail.window;

import com.sushkomihail.simulation.Simulation;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar(Simulation simulation) {
        JMenu controlMenu = new JMenu("Управление");

        JMenuItem controlItem = new JMenuItem("Старт");
        controlItem.addActionListener(l -> simulation.control(controlItem));

        JMenuItem resetItem = new JMenuItem("Сбросить");
        resetItem.addActionListener(l -> simulation.reset(controlItem));

        controlMenu.add(controlItem);
        controlMenu.add(resetItem);
        add(controlMenu);

        JMenu aboutMenu = new JMenu("О программме");
        add(aboutMenu);
    }
}
