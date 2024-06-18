package com.sushkomihail.window;

import com.sushkomihail.simulation.Simulation;
import com.sushkomihail.ui.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Settings extends JPanel {
    private final MaskedTextField populationText = new MaskedTextField(3, "200", Mask.INT.getMask());
    private final JCheckBox isIsolationUsedBox = new JCheckBox("Карантин", false);
    private final ProbabilitySlider isolationProbabilitySlider = new ProbabilitySlider(0.7f);
    private final JCheckBox isDistancingUsedBox = new JCheckBox("Дистанцирование", false);
    private final ProbabilitySlider distancingProbabilitySlider = new ProbabilitySlider(0.7f);
    private final VirusSettings virusSettings = new VirusSettings();
    private final UnitSettings unitSettings = new UnitSettings();

    public Settings(Window window, Simulation simulation) {
        TitledBorder border = new TitledBorder("Настройки симуляции");
        border.setTitleFont(Fonts.H1.getFont());
        setBorder(border);
        setLayout(new BorderLayout());
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(new JLabel("Популяция"));
        container.add(populationText);

        window.setIsolationCanvasVisibility(isIsolationUsedBox.isSelected());
        isIsolationUsedBox.addItemListener(e -> window.setIsolationCanvasVisibility(isIsolationUsedBox.isSelected()));
        container.add(isIsolationUsedBox);

        container.add(new JLabel("Вероятность помещения в карантин"));
        container.add(isolationProbabilitySlider);

        container.add(isDistancingUsedBox);

        container.add(new JLabel("Вероятность соблюдения дистанцирования"));
        container.add(distancingProbabilitySlider);

        container.add(virusSettings);
        container.add(unitSettings);

        UiExtensions.setAlignmentXForAllComponentsInContainer(container, LEFT_ALIGNMENT);

        JButton simulationControlButton = new JButton("Старт");
        simulationControlButton.addActionListener(l -> simulation.manage(simulationControlButton));
        container.add(simulationControlButton);

        add(container, BorderLayout.NORTH);
    }

    public int getPopulation() {
        return Integer.parseInt(populationText.getText());
    }

    public boolean isIsolationUsed() {
        return isIsolationUsedBox.isSelected();
    }

    public float getIsolationProbability() {
        return isolationProbabilitySlider.getValue();
    }

    public boolean isDistancingUsed() {
        return isDistancingUsedBox.isSelected();
    }

    public float getDistancingProbability() {
        return distancingProbabilitySlider.getValue();
    }

    public VirusSettings getVirusSettings() {
        return  virusSettings;
    }

    public UnitSettings getUnitSettings() {
        return unitSettings;
    }
}
