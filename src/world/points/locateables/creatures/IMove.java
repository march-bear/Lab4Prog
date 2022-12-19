package world.points.locateables.creatures;

public interface IMove {
    void move();
    boolean move(int deltaX, int deltaY);
}
