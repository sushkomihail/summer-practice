package com.sushkomihail.math;

public class Random {
    public static float getFloat(float min, float max) {
        return (float) (Math.random() * (max - min)) + min;
    }

    public static int getInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public static boolean isEventHappened(float probability) {
        return getFloat(0, 1) <= probability;
    }
}
