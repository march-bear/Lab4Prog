import world.GroupNotMarkedException;
import world.PeopleWereKilledException;
import world.World;
import world.points.locateables.creatures.Penguin;
import world.points.locateables.creatures.active.Human;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        World world = World.getInstance();

        world.setGroupOfPeople(
                new Human("Лейк"),
                new Human("Рассказчик"),
                new Human("Карл")
        );

        world.setGroupOfPenguins(
                new Penguin("Круглан", world.finish),
                new Penguin("Рыбак", world.finish),
                new Penguin("Коко", world.finish),
                new Penguin("Тулупчик", world.finish)
        );

        world.setMaxNumberOfMonsters(10);
        world.mainland.setNumberOfAreas(4);
        try {
            world.run();
        } catch (PeopleWereKilledException ex) {
            System.out.println("Люди не достигли своей цели и были убиты фантомными монстрами.");
        } catch (GroupNotMarkedException ex) {
            System.out.println(ex);
        }
        System.out.println("Симуляция остановлена...");

    }
}