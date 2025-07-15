package main.SquareCrushLIBGDX.gameLogic.controlery;

import java.util.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicGridFlat {
    private final int width;
    private final int height;
    private final AtomicIntegerArray data;

    public AtomicGridFlat(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new AtomicIntegerArray(width * height);
    }

    private int index(int x, int y) {
        return y * width + x;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void setScore(int x, int y, int value) {
        if (isInBounds(x, y)) {
            data.set(index(x, y), value);
        }
    }

    public void addScore(int x, int y, int delta) {
        if (isInBounds(x, y)) {
            data.addAndGet(index(x, y), delta);
        }
    }

    public int getScore(int x, int y) {
        return isInBounds(x, y) ? data.get(index(x, y)) : 0;
    }

    public List<GridPoint> getTop10() {
        PriorityQueue<GridPoint> minHeap = new PriorityQueue<>(Comparator.comparingInt(g -> g.value));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = getScore(x, y);
                if (value > 0) { // ignoruj nulové pozície (voliteľné)
                    GridPoint gp = new GridPoint(x, y, value);
                    if (minHeap.size() < 10) {
                        minHeap.offer(gp);
                    } else if (value > minHeap.peek().value) {
                        minHeap.poll();
                        minHeap.offer(gp);
                    }
                }
            }
        }

        for (GridPoint gp : minHeap) {
            System.out.println(gp.getClass().getName());
        }

        List<GridPoint> result = new ArrayList<>(minHeap);
        result.sort(Comparator.comparingInt(GridPoint::getValue).reversed());
        return result;
    }
}
