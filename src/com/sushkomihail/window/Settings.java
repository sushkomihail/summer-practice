package com.sushkomihail.window;

import com.sushkomihail.ui.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Settings extends JPanel {
    private final JPanel container = new JPanel();
    private final MaskedTextField populationText = new MaskedTextField(3, "200", Mask.INT.getMask());
    private final MaskedTextField startInfectedUnitsCountText = new MaskedTextField(1, "1", Mask.INT.getMask());
    private final JCheckBox isIsolationUsedBox = new JCheckBox("Карантин", false);
    private final ProbabilitySlider isolationProbabilitySlider = new ProbabilitySlider(0.7f);
    private final JCheckBox isDistancingUsedBox = new JCheckBox("Дистанцирование", false);
    private final ProbabilitySlider distancingProbabilitySlider = new ProbabilitySlider(0.7f);
    private final VirusSettings virusSettings = new VirusSettings();
    private final UnitSettings unitSettings = new UnitSettings();

    public Settings(MainWindow window) {
        TitledBorder border = new TitledBorder("Настройки симуляции");
        border.setTitleFont(Fonts.H1.getFont());
        setBorder(border);
        setLayout(new BorderLayout());

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(new JLabel("Популяция"));
        container.add(populationText);

        container.add(new JLabel("Начальное количество больных"));
        container.add(startInfectedUnitsCountText);

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

        add(container, BorderLayout.NORTH);
    }

    public int getPopulation() {
        return Integer.parseInt(populationText.getText());
    }

    public int getStartInfectedUnitsCount() {
        return Integer.parseInt(startInfectedUnitsCountText.getText());
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

    @Override
    public void setEnabled(boolean isEnabled) {
        UiExtensions.setEnabledComponentsOf(container, isEnabled);
        UiExtensions.setEnabledComponentsOf(virusSettings, isEnabled);
        UiExtensions.setEnabledComponentsOf(unitSettings, isEnabled);
    }
}
