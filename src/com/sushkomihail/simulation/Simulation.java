package com.sushkomihail.simulation;

import com.sushkomihail.chart.Chart;
import com.sushkomihail.chart.ChartWindow;
import com.sushkomihail.math.Random;
import com.sushkomihail.unit.*;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.MainWindow;

import javax.swing.*;
import java.util.ArrayList;

public class Simulation implements Runnable {
    private static final int TARGET_FPS = 60;
    private static final float UPDATE_INTERVAL = 1000000000.0f / TARGET_FPS;

    private final Virus virus = new Virus();
    private final Unit unit;
    private int population = 100;
    private int startInfectedUnitsCount = 1;
    private boolean isIsolationUsed = false;
    private float isolationProbability = 0.7f;
    private boolean isDistancingUsed = false;
    private float distancingProbability = 0.7f;

    private final ChartWindow chartWindow = new ChartWindow();
    private final MainWindow mainWindow = new MainWindow(this, chartWindow);
    private final Chart chart = new Chart(chartWindow.getChartCanvas());
    private final ArrayList<Unit> units = new ArrayList<>();
    private final Statistics statistics = new Statistics();
    private Thread simulationThread;
    private float elapsedChartUpdateInterval;
    private boolean isRunning;
    private boolean hasBeenReset = true;

    public Simulation() {
        chartWindow.getLegend().addDesignation(UnitColor.UNINFECTED.getColor(), UnitTitle.UNINFECTED.getTitle("е"));
        chartWindow.getLegend().addDesignation(UnitColor.INFECTED.getColor(), UnitTitle.INFECTED.getTitle("е"));
        chartWindow.getLegend().addDesignation(UnitColor.RECOVERED.getColor(), UnitTitle.RECOVERED.getTitle("е"));

        unit = new Unit(mainWindow.getSocietyCanvas());
    }

    private void applySettings() {
        virus.applySettings(mainWindow.getSettings().getVirusSettings());
        unit.applySettings(mainWindow.getSettings().getUnitSettings());
        population = mainWindow.getSettings().getPopulation();
        startInfectedUnitsCount = mainWindow.getSettings().getStartInfectedUnitsCount();
        isIsolationUsed = mainWindow.getSettings().isIsolationUsed();
        isolationProbability = mainWindow.getSettings().getIsolationProbability();
        isDistancingUsed = mainWindow.getSettings().isDistancingUsed();
        distancingProbability = mainWindow.getSettings().getDistancingProbability();
    }

    private void trySetUsingDistancing(Unit unit) {
        if (!isDistancingUsed) {
            return;
        }

        if (Random.isEventHappened(distancingProbability)) {
            unit.setUsingDistancing(true);
        }
    }

    private void createPopulation() {
        for (int i = 0; i < population; i++) {
            units.add(new Unit(unit));
            units.get(i).initializeMovement();

            if (i < startInfectedUnitsCount) {
                units.get(i).setState(new InfectedState(units.get(i), virus));
            } else {
                units.get(i).setState(new UninfectedState(units.get(i)));
            }

            trySetUsingDistancing(units.get(i));
        }
    }

    private void tryUpdateChart(float deltaTime) {
        elapsedChartUpdateInterval += deltaTime;

        if (elapsedChartUpdateInterval < Chart.UPDATE_INTERVAL) {
            return;
        }

        chart.getData(UnitColor.UNINFECTED.getColor()).add(statistics.getUninfectedCount());
        chart.getData(UnitColor.INFECTED.getColor()).add(statistics.getInfectedCount());
        chart.getData(UnitColor.RECOVERED.getColor()).add(statistics.getRecoveredCount());
        elapsedChartUpdateInterval = 0;
    }

    private void start() {
        if (hasBeenReset) {
            mainWindow.getSettings().setEnabled(false);
            mainWindow.getStatisticsView().getChartButton().setEnabled(true);
            applySettings();
            createPopulation();
        }

        isRunning = true;
        hasBeenReset = false;

        if (simulationThread == null || simulationThread.isInterrupted()) {
            simulationThread = new Thread(this);
        }

        simulationThread.start();
    }

    private void update(float deltaTime) {
        for (Unit unit : units) {
            unit.move(deltaTime);

            if (unit.getState() instanceof InfectedState infectedUnit) {
                if (isIsolationUsed) {
                    infectedUnit.tryIsolate(deltaTime, mainWindow.getIsolationCanvas(), isolationProbability);
                }

                if (!infectedUnit.isIsolated()) {
                    infectedUnit.tryInfect(deltaTime, units);
                }

                infectedUnit.recover(deltaTime);
            }
        }

        statistics.update(units);
        tryUpdateChart(deltaTime);
        mainWindow.getStatisticsView().update(statistics);

        if (statistics.getInfectedCount() == 0) {
            stop();
            mainWindow.getMenu().getControlItem().setText("Старт");
            mainWindow.getMenu().getControlItem().setEnabled(false);
            JOptionPane.showMessageDialog(mainWindow, "Симуляция завершена!");
        }
    }

    private void stop() {
        isRunning = false;
        simulationThread.interrupt();
    }

    private void repaint() {
        mainWindow.getSocietyCanvas().repaint();

        if (isIsolationUsed) {
            mainWindow.getIsolationCanvas().repaint();
        }

        chartWindow.getChartCanvas().repaint();
    }

    public void control() {
        if (isRunning) {
            stop();
            mainWindow.getMenu().getControlItem().setText("Старт");
        } else {
            start();
            mainWindow.getMenu().getControlItem().setText("Стоп");
        }
    }

    public void reset() {
        isRunning = false;
        hasBeenReset = true;

        simulationThread.interrupt();

        units.clear();
        statistics.clear();
        chart.clearDataMap();

        mainWindow.getSettings().setEnabled(true);

        mainWindow.getSocietyCanvas().getRenderer().clear();
        mainWindow.getIsolationCanvas().getRenderer().clear();

        mainWindow.getMenu().getControlItem().setText("Старт");
        mainWindow.getMenu().getControlItem().setEnabled(true);

        mainWindow.getStatisticsView().update(statistics);
        mainWindow.getStatisticsView().getChartButton().setEnabled(false);

        repaint();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        while (isRunning) {
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - previousTime;

            if (deltaTime >= UPDATE_INTERVAL) {
                update(deltaTime / 1000000000.0f);
                repaint();
                previousTime = currentTime;
            }
        }
    }
}
