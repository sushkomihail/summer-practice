package com.sushkomihail.unit;

import com.sushkomihail.graphics.RenderObject;
import com.sushkomihail.math.Random;
import com.sushkomihail.math.Vector;
import com.sushkomihail.window.Canvas;
import com.sushkomihail.window.UnitSettings;

import java.awt.*;

public class Unit implements RenderObject {
    private static final int RADIUS = 8;
    private static final int MAX_MOVE_DELTA = 20;

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

    public boolean isUsingDistancing() {
        return isUsingDistancing;
    }

    public UnitState getState() {
        return state;
    }

    public Vector getPosition() {
        return position;
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

    private Vector clampPosition(int x, int y) {
        if (x != movementCanvas.getWidth()) {
            x = (x + movementCanvas.getWidth()) % movementCanvas.getWidth();
        }

        if (y != movementCanvas.getHeight()) {
            y = (y + movementCanvas.getHeight()) % movementCanvas.getHeight();
        }

        return new Vector(x, y);
    }

    private Vector calculateNextPosition() {
        float angleInRadians = Random.getFloat(0, (float) (2 * Math.PI));
        int dx = (int) (Math.cos(angleInRadians) * MAX_MOVE_DELTA);
        int dy = (int) (Math.sin(angleInRadians) * MAX_MOVE_DELTA);
        int x = (int) (position.getX() + dx);
        int y = (int) (position.getY() + dy);
        return clampPosition(x, y);
    }

    public void applySettings(UnitSettings settings) {
        speed = settings.getSpeed();
    }

    public void initializeMovement() {
        position = movementCanvas.getRandomPositionInside(RADIUS);
        targetPosition = position;
    }

    public void move(float deltaTime) {
        if (isUsingDistancing) {
            return;
        }

        float distanceToTargetPosition = Vector.getDistance(position, targetPosition);

        if (distanceToTargetPosition <= 0.5f) {
            targetPosition = calculateNextPosition();
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
