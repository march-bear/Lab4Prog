package world.squares;

import world.World;
import world.points.Point;
import world.squares.Square;

import java.util.Objects;

public class Area extends Square {
    protected final double audibility;
    protected final double visibility;

    public Area(String name, Point bottomLeftPoint, Point topRightPoint) {
        this(name, bottomLeftPoint, topRightPoint, 1.0, 1.0);
    }

    public Area(String name, Point bottomLeftPoint, Point topRightPoint, double audibility, double visibility) {
        super(name, bottomLeftPoint, topRightPoint);
        if (audibility > 1.0)
            audibility = 1.0;
        else if (audibility < 0.0)
            audibility = 0.0;

        if (visibility > 1.0)
            visibility = 1.0;
        else if (visibility < 0.0)
            visibility = 0.0;

        this.audibility = audibility;
        this.visibility = visibility;
    }

    public double getAudibility() {
        return audibility;
    }

    public double getVisibility() {
        return visibility;
    }

    @Override
    public String toString() {
        return "область " + name + "\n" +
                "Координаты нижней левой вершины: (" + bottomLeftPoint.getX() + ", " + bottomLeftPoint.getY() + ")\n" +
                "Координаты верхней правой вершины: (" + topRightPoint.getX() + ", " + topRightPoint.getY() + ")\n" +
                "Видимость: " + (int) (visibility * 100) + "%\n" +
                "Слышимость: " + (int) (audibility * 100) + "%";
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            Area area = (Area) obj;
            return area.audibility == this.audibility && area.visibility == this.visibility;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bottomLeftPoint, topRightPoint, audibility, visibility);
    }
}
