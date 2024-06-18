package com.sushkomihail.simulation;

import com.sushkomihail.math.Random;
import com.sushkomihail.unit.InfectedState;
import com.sushkomihail.unit.UninfectedState;
import com.sushkomihail.unit.Unit;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.Settings;
import com.sushkomihail.window.Window;

import javax.swing.*;
import java.util.ArrayList;

public class Simulation implements Runnable {
    private static final int TARGET_FPS = 60;
    private static final float UPDATE_INTERVAL = 1000000000.0f / TARGET_FPS;

    private int population = 100;
    private boolean isIsolationUsed = false;
    private float isolationProbability = 0.7f;
    private boolean isDistancingUsed = false;
    private float distancingProbability = 0.7f;
    private final Virus virus = new Virus();
    private final Unit unit;

    private final Window window;
    private final ArrayList<Unit> units = new ArrayList<>();
    private Thread simulationThread;
    private boolean isRunning;

    public Simulation() {
        window = new Window(this);
        unit = new Unit(window.getSocietyCanvas());
        //createPopulation();
    }

    private void applySettings(Settings settings) {
        population = settings.getPopulation();
        isIsolationUsed = settings.isIsolationUsed();
        isolationProbability = settings.getIsolationProbability();
        isDistancingUsed = settings.isDistancingUsed();
        distancingProbability = settings.getDistancingProbability();
        virus.applySettings(settings.getVirusSettings());
        unit.applySettings(settings.getUnitSettings());
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
        units.clear();

        for (int i = 0; i < population; i++) {
            units.add(new Unit(unit));
            units.get(i).initializeMovement();

            if (i == 0) {
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

    private void start() {
        window.getSocietyCanvas().getRenderer().clear();
        window.getIsolationCanvas().getRenderer().clear();
        applySettings(window.getSettings());
        createPopulation();

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
    }

    private void stop() {
        isRunning = false;
        simulationThread.interrupt();
    }

    private void repaint() {
        window.getSocietyCanvas().repaint();

        if (isIsolationUsed) {
            window.getIsolationCanvas().repaint();
        }
    }

    public void manage(JButton controlButton) {
        if (!isRunning) {
            start();
            controlButton.setText("Стоп");
        } else {
            stop();
            controlButton.setText("Старт");
        }
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
