package com.sushkomihail.unit;

import com.sushkomihail.math.Random;
import com.sushkomihail.math.Vector;
import com.sushkomihail.graphics.Graphics;
import com.sushkomihail.virus.Virus;
import com.sushkomihail.window.Canvas;

import java.awt.*;

public class InfectedState implements UnitState {
    private static final Color UNIT_COLOR = new Color(214, 60, 51);
    private static final Color INFECTION_AREA_COLOR = new Color(255, 115, 115, 100);

    private final Unit unit;
    private final Virus virus;

    private float elapsedInfectionDetectionTime;
    private float elapsedInfectionInterval;
    private float elapsedRecoveryTime;
    private boolean hasIsolationAttempt = true;

    public InfectedState(Unit unit, Virus virus) {
        this.unit = unit;
        this.virus = virus;
    }

    public void tryIsolate(float deltaTime, Canvas isolationCanvas, float isolationProbability) {
        if (!hasIsolationAttempt) {
            return;
        }

        elapsedInfectionDetectionTime += deltaTime;

        if (elapsedInfectionDetectionTime >= virus.getDetectionTime()) {
            if (Random.isEventHappened(isolationProbability)) {
                unit.setMovementCanvas(isolationCanvas);
                unit.initializeMovement();
            }
        }

        hasIsolationAttempt = false;
    }

    private boolean canInfect(float deltaTime, Unit unit) {
        elapsedInfectionInterval += deltaTime;

        if (unit.isUsingDistancing() && this.unit.isUsingDistancing()) {
            return false;
        }

        float distanceToCarrier = Vector.getDistance(this.unit.getPosition(), unit.getPosition());

        if (distanceToCarrier > virus.getInfectionRadius()) {
            return false;
        }

        if (elapsedInfectionInterval >= virus.getInfectionInterval()) {
            elapsedInfectionInterval = 0;
            return true;
        }

        return false;
    }

    public void tryInfect(float deltaTime, Unit unit) {
        if (canInfect(deltaTime, unit)) {
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
        Graphics.fillCircle(graphics, unit.getPosition(), virus.getInfectionRadius(), INFECTION_AREA_COLOR);
        Graphics.fillCircle(graphics, unit.getPosition(), unit.getRadius(), UNIT_COLOR);
    }
}
