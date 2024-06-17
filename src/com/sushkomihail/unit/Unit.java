package com.sushkomihail.unit;

import com.sushkomihail.graphics.RenderObject;
import com.sushkomihail.math.Vector;
import com.sushkomihail.window.Canvas;
import com.sushkomihail.window.UnitSettings;

import java.awt.*;

public class Unit implements RenderObject {
    private static final int RADIUS = 8;

    private int speed = 50;
    private boolean isUsingDistancing;

    private Canvas movementCanvas;
    private UnitState state;
    private Vector position;
    private Vector targetPosition;
    private Vector moveDirection;

    public Unit(Canvas movementCanvas) {
        this.movementCanvas = movementCanvas;
    }

    public Unit(Unit unit) {
        speed = unit.speed;
        isUsingDistancing = unit.isUsingDistancing;
        setMovementCanvas(unit.movementCanvas);
        state = unit.state;
        position = unit.position;
        targetPosition = unit.targetPosition;
        moveDirection = unit.moveDirection;
    }

    public int getRadius() {
        return RADIUS;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isUsingDistancing() {
        return isUsingDistancing;
    }

    public UnitState getState() {
        return state;
    }

    public Vector getPosition() {
        return position;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setUsingDistancing(boolean isUsingDistancing) {
        this.isUsingDistancing = isUsingDistancing;
    }

    public void setState(UnitState state) {
        this.state = state;
    }

    public void setMovementCanvas(Canvas movementCanvas) {
        if (this.movementCanvas != null) {
            this.movementCanvas.getRenderer().removeRenderObject(this);
        }

        movementCanvas.getRenderer().addRenderObject(this);
        this.movementCanvas = movementCanvas;
    }

    public void applySettings(UnitSettings settings) {
        speed = settings.getSpeed();
    }

    public void initializeMovement() {
        position = movementCanvas.getRandomPositionInside(RADIUS);
        targetPosition = movementCanvas.getRandomPositionInside(RADIUS);
        moveDirection = Vector.subtract(targetPosition, position).normalize();
    }

    public void move(float deltaTime) {
        if (isUsingDistancing) {
            return;
        }

        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5f) {
            targetPosition = movementCanvas.getRandomPositionInside(RADIUS);
            moveDirection = Vector.subtract(targetPosition, position).normalize();
        }

        float dx = moveDirection.getX() * speed * deltaTime;
        float dy = moveDirection.getY() * speed * deltaTime;
        position.move(dx, dy);
    }

    @Override
    public void render(Graphics2D graphics) {
        if (state != null) {
            state.render(graphics);
        }
    }
}
