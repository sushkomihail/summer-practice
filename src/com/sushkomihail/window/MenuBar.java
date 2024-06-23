package com.sushkomihail.window;

import com.sushkomihail.simulation.Simulation;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    JMenuItem controlItem = new JMenuItem("Старт");

    public MenuBar(Simulation simulation) {
        JMenu controlMenu = new JMenu("Управление");

        controlItem.addActionListener(l -> simulation.control());

        JMenuItem resetItem = new JMenuItem("Сбросить");
        resetItem.addActionListener(l -> simulation.reset());

        controlMenu.add(controlItem);
        controlMenu.add(resetItem);
        add(controlMenu);
    }

    public JMenuItem getControlItem() {
        return controlItem;
    }
}
