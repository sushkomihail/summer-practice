package com.sushkomihail.simulation;

import com.sushkomihail.unit.InfectedState;
import com.sushkomihail.unit.UninfectedState;
import com.sushkomihail.unit.Unit;

import java.util.List;

public class Statistics {
    private int uninfectedCount;
    private int infectedCount;
    private int recoveredCount;

    public int getUninfectedCount() {
        return uninfectedCount;
    }

    public int getInfectedCount() {
        return infectedCount;
    }

    public int getRecoveredCount() {
        return recoveredCount;
    }

    public void update(List<Unit> units) {
        clear();

        for (Unit unit : units) {
            if (unit.getState() instanceof UninfectedState) {
                uninfectedCount += 1;
            } else if (unit.getState() instanceof InfectedState) {
                infectedCount += 1;
            } else {
                recoveredCount += 1;
            }
        }
    }

    public void clear() {
        uninfectedCount = 0;
        infectedCount = 0;
        recoveredCount = 0;
    }
}
