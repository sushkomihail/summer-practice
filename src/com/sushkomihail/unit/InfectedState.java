package com.sushkomihail.unit;

import com.sushkomihail.math.Random;
import com.sushkomihail.math.Vector;
import com.sushkomihail.graphics.Graphics;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.Canvas;

import java.awt.*;
import java.util.ArrayList;

public class InfectedState implements UnitState {
    private static final Color INFECTION_AREA_COLOR = new Color(255, 115, 115, 100);
    private static final Color NON_ISOLATED_INFECTION_AREA_COLOR = new Color(255, 220, 98, 100);

    private final Unit unit;
    private final Virus virus;

    private float elapsedInfectionDetectionTime;
    private float elapsedInfectionInterval;
    private float elapsedRecoveryTime;
    private boolean hasIsolationAttempt = true;
    private boolean isIsolated;

    public InfectedState(Unit unit, Virus virus) {
        this.unit = unit;
        this.virus = virus;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

    public void tryIsolate(float deltaTime, Canvas isolationCanvas, float isolationProbability) {
        if (!hasIsolationAttempt || isIsolated) {
            return;
        }

        elapsedInfectionDetectionTime += deltaTime;

        if (elapsedInfectionDetectionTime >= virus.getDetectionTime()) {
            if (Random.isEventHappened(isolationProbability)) {
                unit.setMovementCanvas(isolationCanvas);
                unit.initializeMovement();
                isIsolated = true;
            }

            hasIsolationAttempt = false;
        }
    }

    private boolean isInfectionIntervalElapsed(float deltaTime) {
        elapsedInfectionInterval += deltaTime;

        if (elapsedInfectionInterval >= virus.getInfectionInterval()) {
            elapsedInfectionInterval = 0;
            return true;
        }

        return false;
    }

    public void tryInfect(float deltaTime, ArrayList<Unit> units) {
        if (!isInfectionIntervalElapsed(deltaTime)) {
            return;
        }

        for (Unit unit : units) {
            if (!(unit.getState() instanceof UninfectedState)) {
                continue;
            }

            if (unit.isUsingDistancing() && this.unit.isUsingDistancing()) {
                continue;
            }

            float distanceToCarrier = Vector.getDistance(this.unit.getPosition(), unit.getPosition());

            if (distanceToCarrier > virus.getInfectionRadius()) {
                continue;
            }

            if (Random.isEventHappened(virus.getInfectionProbability())) {
                unit.setState(new InfectedState(unit, virus));
            }
        }
    }

    public void recover(float deltaTime) {
        elapsedRecoveryTime += deltaTime;

        if (elapsedRecoveryTime >= virus.getRecoveryTime()) {
            unit.setState(new RecoveredState(unit));
        }
    }

    @Override
    public void render(Graphics2D graphics) {
        Color unitColor = UnitColor.INFECTED.getColor();
        Color infectionAreaColor = INFECTION_AREA_COLOR;

        if (!hasIsolationAttempt && !isIsolated) {
            unitColor = UnitColor.NON_ISOLATED.getColor();
            infectionAreaColor = NON_ISOLATED_INFECTION_AREA_COLOR;
        }

        Graphics.fillCircle(graphics, unit.getPosition(), virus.getInfectionRadius(), infectionAreaColor);
        Graphics.fillCircle(graphics, unit.getPosition(), unit.getRadius(), unitColor);
    }
}
