package world.points.locateables.creatures;

import world.points.locateables.Sound;

@FunctionalInterface
public interface IMakeSound {
    Sound makeSound(int x, int y);
}
