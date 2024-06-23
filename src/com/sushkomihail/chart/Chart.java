package com.sushkomihail.chart;

import com.sushkomihail.graphics.RenderObject;
import com.sushkomihail.window.Canvas;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;

public class Chart implements RenderObject {
    public static final float UPDATE_INTERVAL = 0.5f;

    private final HashMap<Color, ChartData> dataMap = new HashMap<>();
    private final Canvas canvas;

    public Chart(Canvas canvas) {
        this.canvas = canvas;
        this.canvas.getRenderer().addRenderObject(this);
    }

    public ChartData getData(Color key) {
        if (!dataMap.containsKey(key)) {
            dataMap.put(key, new ChartData());
        }

        return dataMap.get(key);
    }

    private int getDataMapMaxParameter(UnaryOperator<Integer> function) {
        List<Color> keys = new ArrayList<>(dataMap.keySet());

        int max = function.apply(0);

        for (int i = 1; i < keys.size(); i++) {
            int current = function.apply(i);

            if (current > max) {
                max = current;
            }
        }

        return max;
    }

    public void clearDataMap() {
        dataMap.clear();
    }

    @Override
    public void render(Graphics2D graphics) {
        List<Color> keys = new ArrayList<>(dataMap.keySet());

        if (keys.size() == 0) {
            return;
        }

        UnaryOperator<Integer> getParameterFunction = x -> dataMap.get(keys.get(x)).getMaxValue();
        float yMultiplier = (float) canvas.getHeight() / getDataMapMaxParameter(getParameterFunction);
        getParameterFunction = x -> dataMap.get(keys.get(x)).getSize();
        int step = canvas.getWidth() / getDataMapMaxParameter(getParameterFunction);

        HashMap<Color, ChartData> dataMapCopy = new HashMap<>(dataMap);

        for (Color key : keys) {
            int x = 0;

            for (int i = 1; i < dataMapCopy.get(key).getSize(); i++) {
                int y1 = (int) (canvas.getHeight() - dataMap.get(key).getValue(i - 1) * yMultiplier);
                int y2 = (int) (canvas.getHeight() - dataMap.get(key).getValue(i) * yMultiplier);
                graphics.setColor(key);
                graphics.drawLine(x, y1, x + step, y2);
                x += step;
            }
        }
    }
}
