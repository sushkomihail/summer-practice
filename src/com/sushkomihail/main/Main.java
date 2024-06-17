package com.sushkomihail.main;

import com.sushkomihail.simulation.Simulation;
import com.sushkomihail.ui.UiExtensions;

public class Main {
    public static void main(String[] args) {
        UiExtensions.trySetSystemTheme();
        new Simulation();
    }
}