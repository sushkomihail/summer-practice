package com.sushkomihail.simulation;

import com.sushkomihail.chart.Chart;
import com.sushkomihail.chart.ChartWindow;
import com.sushkomihail.math.Random;
import com.sushkomihail.unit.*;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.Settings;
import com.sushkomihail.window.MainWindow;

import javax.swing.*;
import java.util.ArrayList;

public class Simulation implements Runnable {
    private static final int TARGET_FPS = 60;
    private static final float UPDATE_INTERVAL = 1000000000.0f / TARGET_FPS;
    private static final float CHART_UPDATE_INTERVAL = 0.5f;

    private final Virus virus = new Virus();
    private final Unit unit;
    private int population = 100;
    private int startInfectedUnitsCount = 1;
    private boolean isIsolationUsed = false;
    private float isolationProbability = 0.7f;
    private boolean isDistancingUsed = false;
    private float distancingProbability = 0.7f;

    private final ChartWindow chartWindow = new ChartWindow();
    private final MainWindow window = new MainWindow(this, chartWindow);
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

        unit = new Unit(window.getSocietyCanvas());
    }

    private void applySettings(Settings settings) {
        virus.applySettings(settings.getVirusSettings());
        unit.applySettings(settings.getUnitSettings());
        population = settings.getPopulation();
        startInfectedUnitsCount = settings.getStartInfectedUnitsCount();
        isIsolationUsed = settings.isIsolationUsed();
        isolationProbability = settings.getIsolationProbability();
        isDistancingUsed = settings.isDistancingUsed();
        distancingProbability = settings.getDistancingProbability();
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

    private void spreadInfection(float deltaTime, InfectedState infectedUnit) {
        if (infectedUnit.isIsolated()) {
            return;
        }

        for (Unit unit : units) {
            if (unit.getState() instanceof UninfectedState) {
                infectedUnit.tryInfect(deltaTime, unit);
            }
        }
    }

    private void updateChart(float deltaTime) {
        elapsedChartUpdateInterval += deltaTime;

        if (elapsedChartUpdateInterval >= CHART_UPDATE_INTERVAL) {
            chart.getData(UnitColor.UNINFECTED.getColor()).add(statistics.getUninfectedCount());
            chart.getData(UnitColor.INFECTED.getColor()).add(statistics.getInfectedCount());
            chart.getData(UnitColor.RECOVERED.getColor()).add(statistics.getRecoveredCount());
            elapsedChartUpdateInterval = 0;
        }
    }

    private void start() {
        if (hasBeenReset) {
            window.getStatisticsView().getChartButton().setEnabled(true);
            applySettings(window.getSettings());
            createPopulation();
        }

        isRunning = true;

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
                    infectedUnit.tryIsolate(deltaTime, window.getIsolationCanvas(), isolationProbability);
                }

                spreadInfection(deltaTime, infectedUnit);
                infectedUnit.recover(deltaTime);
            }
        }

        statistics.update(units);
        updateChart(deltaTime);
        window.getStatisticsView().update(statistics);
    }

    private void stop() {
        isRunning = false;
        hasBeenReset = false;
        simulationThread.interrupt();
    }

    private void repaint() {
        window.getSocietyCanvas().repaint();

        if (isIsolationUsed) {
            window.getIsolationCanvas().repaint();
        }

        chartWindow.getChartCanvas().repaint();
    }

    public void control(JMenuItem controlItem) {
        if (isRunning) {
            stop();
            controlItem.setText("Старт");
        } else {
            start();
            controlItem.setText("Стоп");
        }
    }

    public void reset(JMenuItem controlItem) {
        isRunning = false;
        hasBeenReset = true;
        simulationThread.interrupt();
        units.clear();
        statistics.clear();
        window.getStatisticsView().update(statistics);
        window.getStatisticsView().getChartButton().setEnabled(false);
        window.getSocietyCanvas().getRenderer().clear();
        window.getIsolationCanvas().getRenderer().clear();
        repaint();
        controlItem.setText("Старт");
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
