package com.sushkomihail.simulation;

import com.sushkomihail.graphics.Chart;
import com.sushkomihail.math.Random;
import com.sushkomihail.unit.InfectedState;
import com.sushkomihail.unit.UninfectedState;
import com.sushkomihail.unit.Unit;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.Settings;
import com.sushkomihail.window.MainWindow;

import javax.swing.*;
import java.awt.*;
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

    private final MainWindow window;
    private final ArrayList<Unit> units = new ArrayList<>();
    private final Statistics statistics = new Statistics();
    private Chart chart;
    private Thread simulationThread;
    private boolean isRunning;
    private boolean hasBeenReset = true;

    private float c;

    public Simulation() {
        window = new MainWindow(this);
        unit = new Unit(window.getSocietyCanvas());
        //chart = new Chart(window.getStatisticsView().getChartCanvas());
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
        c += deltaTime;

        if (c >= 0.5f) {
            chart.addDataValue(Color.BLACK, statistics.getUninfectedCount());
            chart.addDataValue(Color.RED, statistics.getInfectedCount());
            chart.addDataValue(Color.BLUE, statistics.getRecoveredCount());
            c = 0;
        }
    }

    private void start() {
        if (hasBeenReset) {
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
        //updateChart(deltaTime);
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

        //window.getStatisticsView().getChartCanvas().repaint();
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
