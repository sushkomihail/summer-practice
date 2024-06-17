package com.sushkomihail.virus;

import com.sushkomihail.window.VirusSettings;

public class Virus {
    private int infectionRadius = 30;
    private float infectionInterval = 0.5f;
    private float infectionProbability = 0.5f;
    private float detectionTime = 1;
    private float recoveryTime = 4;

    public int getInfectionRadius() {
        return infectionRadius;
    }

    public float getInfectionInterval() {
        return infectionInterval;
    }

    public float getInfectionProbability() {
        return infectionProbability;
    }

    public float getDetectionTime() {
        return detectionTime;
    }

    public float getRecoveryTime() {
        return recoveryTime;
    }

    public void applySettings(VirusSettings settings) {
        infectionRadius = settings.getInfectionRadius();
        infectionInterval = settings.getInfectionInterval();
        infectionProbability = settings.getInfectionProbability();
        detectionTime = settings.getDetectionTime();
        recoveryTime = settings.getRecoveryTime();
    }
}
