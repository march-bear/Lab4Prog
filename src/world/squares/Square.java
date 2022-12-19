package world.squares;

import world.points.Point;
import world.points.locateables.Locateable;

import java.util.Objects;

public abstract class Square {
    protected final Point bottomLeftPoint;
    protected final Point topRightPoint;
    protected final String name;

    Square(String name) {
        this(name, new Point(), new Point());
    }

    Square(String name, Point bottomLeftPoint, Point topRightPoint) {
        int bottomLeftPointX = bottomLeftPoint.getX();
        int bottomLeftPointY = bottomLeftPoint.getY();
        int topRightPointX = topRightPoint.getX();
        int topRightPointY = topRightPoint.getY();

        if (bottomLeftPoint.equals(topRightPoint)) {
            if (topRightPointX != Point.maxXY) topRightPointY++;
            else bottomLeftPointX--;

            if (topRightPointY != Point.maxXY) topRightPointY++;
            else bottomLeftPointY--;
        } else {
            if (bottomLeftPointX > topRightPointX) {
                int tmp = bottomLeftPointX;
                bottomLeftPointX = topRightPointX;
                topRightPointX = tmp;
            }

            if (bottomLeftPointY > topRightPointY) {
                int tmp = bottomLeftPointY;
                bottomLeftPointY = topRightPointY;
                topRightPointY = tmp;
            }
        }
        this.bottomLeftPoint = new Point(bottomLeftPointX, bottomLeftPointY);
        this.topRightPoint = new Point(topRightPointX, topRightPointY);
        this.name = name;
    }

    public Point getBottomLeftPoint() {
        return bottomLeftPoint;
    }

    public Point getTopRightPoint() {
        return topRightPoint;
    }

    public String getName() {
        return name;
    }

    public boolean isIncludedPoint(Point point) {
        return (point.getX() >= bottomLeftPoint.getX() && point.getY() >= bottomLeftPoint.getY() &&
                point.getX() <= topRightPoint.getX() && point.getY() <= topRightPoint.getY());
    }

    public boolean isIntersecting(Square other) {
        return (isIncludedPoint(other.bottomLeftPoint) || isIncludedPoint(other.topRightPoint) ||
                isIncludedPoint(new Point(other.bottomLeftPoint.getX(), other.topRightPoint.getY())) ||
                isIncludedPoint(new Point(other.topRightPoint.getX(), other.bottomLeftPoint.getY())));
    }

    @Override
    public String toString() {
        return "Квадрат " + name + "\n" +
                "Координаты нижней левой вершины: (" + bottomLeftPoint.getX() + ", " + bottomLeftPoint.getY() + ")\n" +
                "Координаты верхней правой вершины: (" + topRightPoint.getX() + ", " + topRightPoint.getY() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != getClass())
            return false;
        Square square = (Square) obj;
        return square.bottomLeftPoint == this.bottomLeftPoint && square.topRightPoint == this.topRightPoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bottomLeftPoint, topRightPoint);
    }
}
