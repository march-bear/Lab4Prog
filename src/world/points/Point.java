package world.points;

import world.Correctors;
import world.squares.Square;

import java.util.Objects;

public class Point {
    protected int x;
    protected int y;
    protected String name;
    public final static int maxXY = 20;
    public Point() {
        this(randomXY(), randomXY());
    }

    public Point(int x, int y) {
        this(x, y, "");
    }
    public Point(int x, int y, String name) {
        this.x = Correctors.correctInt(x, 0, maxXY);
        this.y = Correctors.correctInt(y, 0, maxXY);
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void printCoordinates() {
        System.out.println(x + " " + y);
    }

    public static int randomXY() {
        return (int) (Math.random() * (maxXY + 1));
    }

    public static int randomX(Square square) {
        return (int) (Math.random() * (square.getTopRightPoint().getX() -
                square.getBottomLeftPoint().getX() + 1) + square.getBottomLeftPoint().getX());
    }

    public static int randomY(Square square) {
        return (int) (Math.random() * (square.getTopRightPoint().getY() -
                square.getBottomLeftPoint().getY() + 1) + square.getBottomLeftPoint().getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != getClass())
            return false;
        Point point = (Point) obj;
        return point.x == this.x && point.y == this.y;
    }

    @Override
    public String toString() {
        return "Точка " + name + ": (" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
