package com.sushkomihail.math;

public class Vector {
    private float x;
    private float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public Vector normalize() {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        return this;
    }

    public static float getDistance(Vector a, Vector b) {
        float dx = a.getX() - b.getX();
        float dy = a.getY() - b.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static Vector subtract(Vector a, Vector b) {
        float dx = a.getX() - b.getX();
        float dy = a.getY() - b.getY();
        return new Vector(dx, dy);
    }
}
