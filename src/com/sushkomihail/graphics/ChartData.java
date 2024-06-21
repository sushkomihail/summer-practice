package com.sushkomihail.graphics;

import java.util.ArrayList;
import java.util.List;

public class ChartData {
    private final List<Integer> data = new ArrayList<>();

    public int getSize() {
        return data.size();
    }

    public int getValue(int index) {
        return data.get(index);
    }

    public int getMaxValue() {
        int max = data.get(0);

        for (int value : data) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public void add(int value) {
        data.add(value);
    }
}
