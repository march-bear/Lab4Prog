package world.points.locateables;

import world.Correctors;
import world.squares.Area;
import world.squares.Square;
import world.points.Point;

import java.util.Objects;

public abstract class Locateable extends Point {
    protected final Area square;

    protected Locateable(int x, int y, String name, Area square) {
        super(x, y, name);
        this.square = square;
    }

    protected final void setPosition(int x, int y) {
        this.x = Correctors.correctInt(x, square.getBottomLeftPoint().getX(), square.getTopRightPoint().getX());
        this.y = Correctors.correctInt(y, square.getBottomLeftPoint().getY(), square.getTopRightPoint().getY());
    }

    public final int distanceTo(Locateable other) {
        return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
    }

    @Override
    public String toString() {
        return "Объект с координатами " + name + "\n" +
                "Координаты: (" + x + ", " + y + ")\n" +
                "Локация: " + square.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return ((Locateable) obj).square == this.square;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name, square);
    }
}
