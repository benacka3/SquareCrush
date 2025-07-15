package main.SquareCrushLIBGDX.gameLogic.controlery;

public class GridPoint {
    public final int x;
    public final int y;
    public final int value;

    public GridPoint(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getValue() { return value; }
}
