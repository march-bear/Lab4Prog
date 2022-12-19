package world.squares;

import world.Correctors;
import world.points.Point;
import world.squares.Square;

import java.util.Objects;

public final class Label extends Area {
    private final Point centerPoint;
    private final int radius;

    public Label(String name) {
        this(name, new Point(Point.randomXY(), Point.randomXY()));
    }
    private Label(String name, Point centerPoint) {
        this(name, new Point(Correctors.correctInt(centerPoint.getX(), 1, Point.maxXY - 1),
                Correctors.correctInt(centerPoint.getY(), 1, Point.maxXY - 1)), 1);
    }

    public Label(String name, Point centerPoint, int radius) {
        super(name, new Point(centerPoint.getX() - radius, centerPoint.getY() - radius),
                new Point(centerPoint.getX() + radius, centerPoint.getY() + radius));
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    @Override
    public String toString() {
        return "метка " + name + "\n" +
                "Координаты центра: (" + centerPoint.getX() + ", " + centerPoint.getY() + ")\n" +
                "Радиус: (" + radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return ((Label) obj).centerPoint == this.centerPoint && ((Label) obj).radius == this.radius;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bottomLeftPoint, topRightPoint, audibility, visibility, centerPoint, radius);
    }
}
