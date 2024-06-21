package com.sushkomihail.graphics;

import com.sushkomihail.window.Canvas;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;

public class Chart implements RenderObject {
    private static final float UPDATE_INTERVAL = 1f;

    private final HashMap<Color, ChartData> dataSet = new HashMap<>();
    private final Canvas drawingCanvas;
    private float elapsedUpdateInterval;

    public Chart(Canvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
        drawingCanvas.getRenderer().addRenderObject(this);
    }

    private int getDataSetMaxParameter(UnaryOperator<Integer> function) {
        List<Color> keys = new ArrayList<>(dataSet.keySet());

        int max = function.apply(0);

        for (int i = 1; i < keys.size(); i++) {
            int current = function.apply(i);

            if (current > max) {
                max = current;
            }
        }

        return max;
    }

//    private int getMaxDataSize() {
//        List<Color> keys = new ArrayList<>(dataSet.keySet());
//
//        int maxSize = dataSet.get(keys.get(0)).getSize();
//
//        for (int i = 1; i < keys.size(); i++) {
//            int size = dataSet.get(keys.get(i)).getSize();
//
//            if (size > maxSize) {
//                maxSize = size;
//            }
//        }
//
//        return maxSize;
//    }
//
//    private int getMaxDataValue() {
//        List<Color> keys = new ArrayList<>(dataSet.keySet());
//
//        int maxValue = dataSet.get(keys.get(0)).getValue(0);
//
//        for (int i = 1; i < keys.size(); i++) {
//            int value = dataSet.get(keys.get(i)).getMaxValue();
//
//            if (value > maxValue) {
//                maxValue = value;
//            }
//        }
//
//        return maxValue;
//    }

    public void addDataValue(Color key, int value) {
        if (!dataSet.containsKey(key)) {
            dataSet.put(key, new ChartData());
        }

        dataSet.get(key).add(value);
    }

    @Override
    public void render(Graphics2D graphics) {
        List<Color> keys = new ArrayList<>(dataSet.keySet());

        if (keys.size() == 0) {
            return;
        }

        //graphics.setStroke(new BasicStroke(2));

        UnaryOperator<Integer> getParameterFunction = x -> dataSet.get(keys.get(x)).getMaxValue();
        float yMultiplier = (float) drawingCanvas.getHeight() / getDataSetMaxParameter(getParameterFunction);
        getParameterFunction = x -> dataSet.get(keys.get(x)).getSize();
        int step = drawingCanvas.getWidth() / getDataSetMaxParameter(getParameterFunction);

        HashMap<Color, ChartData> dataSetCopy = new HashMap<>(dataSet);

        for (Color key : keys) {
            int x = 0;

            for (int i = 1; i < dataSetCopy.get(key).getSize(); i++) {
                int y1 = (int) (dataSet.get(key).getValue(i - 1) * yMultiplier);
                int y2 = (int) (dataSet.get(key).getValue(i) * yMultiplier);
                graphics.setColor(key);
                graphics.drawLine(x, y1, x + step, y2);
                x += step;
            }
        }
    }
}
