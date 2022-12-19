package world.points.locateables.creatures;

import world.squares.Area;

public class Penguin extends Creature {

    public Penguin(String name, Area square) {
        super(name, square);
    }

    public Penguin(String name, Area square, int speed) {
        super(name, square, speed);
    }

    public Penguin(int x, int y, String name, Area square, int speed) {
        super(x, y, name, square, speed);
    }
}
