package com.sushkomihail.ui;

import javax.swing.*;
import java.awt.*;

public class UiExtensions {
    public static void setAlignmentXForAllComponentsInContainer(Container container, float alignment) {
        for (Component component : container.getComponents()) {
            JComponent jComponent = (JComponent) component;
            jComponent.setAlignmentX(alignment);
        }
    }

    public static void trySetSystemTheme() {
        try {
            String systemThemeName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(systemThemeName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
