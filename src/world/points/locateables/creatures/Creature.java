package world.points.locateables.creatures;

import world.Correctors;
import world.points.locateables.Locateable;
import world.squares.Area;

import java.util.Objects;

public abstract class Creature extends Locateable implements IMove {
    protected int speed;

    public Creature(String name, Area square) {
        this(name, square, 1);
    }

    public Creature(String name, Area square, int speed) {
        this(randomX(square), randomY(square), name, square, speed);
    }

    public Creature(int x, int y, String name, Area square, int speed) {
        super(x, y, name, square);
        this.speed = Correctors.correctInt(speed, 1, maxXY / 10 + 1);
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void move() {
        int deltaX = (int) (Math.random() * (speed + 1)) * (((int) (Math.random() * 2) == 0) ? -1 : 1);
        int deltaY = (speed - Math.abs(deltaX)) * (((int) (Math.random() * 2) == 0) ? -1 : 1);
        if (this.x + deltaX != Correctors.correctInt(this.x + deltaX,
                square.getBottomLeftPoint().getX(), square.getTopRightPoint().getX()))
            deltaX = -deltaX;

        if (this.y + deltaY != Correctors.correctInt(this.y + deltaY,
                square.getBottomLeftPoint().getY(), square.getTopRightPoint().getY()))
            deltaY = -deltaY;

        this.setPosition(this.x + deltaX, this.y + deltaY);
    }

    @Override
    public boolean move(int deltaX, int deltaY) {
        boolean flag = this.x + deltaX == Correctors.correctInt(this.x + deltaX,
                square.getBottomLeftPoint().getX(), square.getTopRightPoint().getX()) ||
                this.y + deltaY == Correctors.correctInt(this.y + deltaY,
                        square.getBottomLeftPoint().getY(), square.getTopRightPoint().getY());

        setPosition(this.x + deltaX, this.y + deltaY);
        return flag;
    }

    @Override
    public String toString() {
        return "Существо " + name + "\n" +
                "Координаты: (" + x + ", " + y + ")\n" +
                "Локация: " + square.getName() + "\n" +
                "Скорость передвижения: " + speed;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return ((Creature) obj).speed == this.speed;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name, square, speed);
    }
}
