package world.points.locateables;

import world.Correctors;
import world.points.Point;
import world.points.locateables.creatures.Creature;
import world.points.locateables.creatures.IMove;
import world.points.locateables.creatures.active.Human;
import world.squares.Area;

import java.util.ArrayList;

public final class GroupOfCreatures<T> extends Locateable implements IMove {
    final private ArrayList<T> creatures;

    public GroupOfCreatures(String name, Area square) {
        this(randomX(square), randomY(square), name, square);
    }

    public GroupOfCreatures(int x, int y, String name, Area square) {
        super(x, y, name, square);
        creatures = new ArrayList<T>();
    }

    public void addCreature(T creature) {
        ((Creature) creature).setPosition(this.x, this.y);
        creatures.add(creature);
    }

    public int getNumberOfCreatures() {
        return creatures.size();
    }

    public T getCreature(int index) {
        return creatures.get(index);
    }

    public boolean goTo(Point target) {
        if (creatures.size() != 0 && creatures.get(0).getClass() == Human.class) {
            boolean flag = false;
            for (T creature : this.creatures) {
                flag = ((Human) creature).goTo(target);
            }

            this.x = ((Locateable) creatures.get(0)).getX();
            this.y = ((Locateable) creatures.get(0)).getY();
            return flag;
        }
        return false;
    }

    @Override
    public void move() {
        int speed = ((Creature) creatures.get(0)).getSpeed();
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
}
