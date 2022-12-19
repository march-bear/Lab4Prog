package world;

import world.points.Point;
import world.points.locateables.GroupOfCreatures;
import world.points.locateables.Sound;
import world.points.locateables.SoundType;
import world.points.locateables.creatures.*;
import world.points.locateables.creatures.active.DevilishCreature;
import world.points.locateables.creatures.active.Human;
import world.squares.Area;
import world.squares.Label;
import world.squares.MainlandAntarctica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class World {
    private static World instance;
    public final MainlandAntarctica mainland;
    private final GroupOfCreatures<Human> people;
    private final GroupOfCreatures<Penguin> penguins;
    private final ArrayList<DevilishCreature> devilishCreatures;
    private int maxNumberOfMonsters = 5;
    public final Label finish;
    private final Commentator comm = Commentator.getInstance();

    private World() {
        mainland = MainlandAntarctica.getInstance();
        finish = new Label("Пещерка пингвинов");
        people = new GroupOfCreatures<>("Люди", mainland);
        penguins = new GroupOfCreatures<>("Пингвины", finish);
        devilishCreatures = new ArrayList<>();
    }

    public static synchronized World getInstance() {
        if (instance == null)
            instance = new World();
        return instance;
    }

    public boolean setGroupOfPeople(Human firstHuman, Human... people) {
        if (this.people.getNumberOfCreatures() != 0)
            return false;

        this.people.addCreature(firstHuman);
        for (Human human: people) {
            this.people.addCreature(human);
        }
        return true;
    }

    public Point getGroupOfPeople() {
        return new Point(people.getX(), people.getY(), people.getName());
    }

    public boolean setGroupOfPenguins(Penguin firstPenguin, Penguin... penguins) {
        if (this.penguins.getNumberOfCreatures() != 0)
            return false;

        this.penguins.addCreature(firstPenguin);
        for (Penguin penguin: penguins) {
            this.penguins.addCreature(penguin);
        }
        return true;
    }

    public Point getGroupOfPenguins() {
        return new Point(penguins.getX(), penguins.getY(), penguins.getName());
    }

    private boolean addMonster(String name, double bloodlust, int senseOfSmell) {
        if (getNumberOfMonster() < maxNumberOfMonsters) {
            devilishCreatures.add(new DevilishCreature(name,
                    mainland.getArea((int) (Math.random() * (mainland.sizeOfAreas() + 1))), bloodlust, senseOfSmell));
            return true;
        }
        return false;
    }

    public int getNumberOfMonster() {
        return devilishCreatures.size();
    }

    Point getMonster(int index) {
        Point monster = devilishCreatures.get(index);
        return new Point(monster.getX(), monster.getY(), monster.getName());
    }

    private boolean startBattle(Human human, DevilishCreature monster) {
        while (human.getHp() != 0 && monster.getHp() != 0 && monster.getCondition() != Condition.CONFUSED) {
            human.attackTarget(monster);
            if (monster.getHp() == 0) {
                monster.setCondition(Condition.DISABLED);
                return true;
            }
            monster.attackTarget(human);
            if (human.getHp() == 0) {
                human.setCondition(Condition.DISABLED);
                return false;
            }
        }

        return human.getHp() != 0;
    }

    private boolean startBattle(DevilishCreature monster, GroupOfCreatures<Human> people) {
        System.out.println("ДА БУДЕТ БАААААААААТТЛ");
        for (int i = 0; i < people.getNumberOfCreatures(); ++i) {
            Human curr_human = people.getCreature(i);
            if (startBattle(curr_human, monster)) {
                System.out.println("Победа людей\n");
                break;
            }
        }
        return monster.getCondition() != Condition.CONFUSED && monster.getHp() != 0;
    }

    public void setMaxNumberOfMonsters(int number) {
        maxNumberOfMonsters = Correctors.correctInt(number, Point.maxXY / 5, Point.maxXY / 5 * 2);
    }

    public boolean run() throws PeopleWereKilledException, GroupNotMarkedException {
        final class WorldMap {
            public final static HashMap<String, Character> symbols;
            public final int height;
            public final int width;
            private final World world;
            private final MainlandAntarctica mainland;
            private final ArrayList<ArrayList<Character>> field;

            public WorldMap(World world, int height, int width) {
                this.height = height;
                this.width = width;
                this.world = world;
                this.mainland = MainlandAntarctica.getInstance();
                field = new ArrayList<>();

                for (int i = 0; i < height; ++i) {
                    field.add(new ArrayList<>());
                    for (int j = 0; j < width; ++j) {
                        field.get(i).add(symbols.get("empty"));
                    }
                }

                update();
            }

            public void update() {
                clear();
                for (int i = 0; i < mainland.sizeOfAreas(); ++i) {
                    Area area = mainland.getArea(i);
                    double audibility = area.getAudibility();
                    double visibility = area.getVisibility();
                    for (int y = area.getBottomLeftPoint().getY(); y <= area.getTopRightPoint().getY(); ++y) {
                        for (int x = area.getBottomLeftPoint().getX(); x <= area.getTopRightPoint().getX(); ++x) {
                            if (field.get(y).get(x) != symbols.get("hardZone")) {
                                if (audibility - 0.4 < 0.00001 || visibility - 0.4 < 0.00001)
                                    field.get(y).set(x, symbols.get("hardZone"));
                                else if (audibility - 0.8 < 0.00001 || visibility - 0.75 < 0.00001)
                                    field.get(y).set(x, symbols.get("middleZone"));
                                else if (field.get(y).get(x) != symbols.get("middleZone"))
                                    field.get(y).set(x, symbols.get("normalZone"));
                            }
                        }
                    }
                }

                Label label = world.finish;
                for (int y = label.getBottomLeftPoint().getY(); y <= label.getTopRightPoint().getY(); ++y) {
                    for (int x = label.getBottomLeftPoint().getX(); x <= label.getTopRightPoint().getX(); ++x) {
                        field.get(y).set(x, symbols.get("label"));
                    }
                }
                field.get(label.getCenterPoint().getY()).set(label.getCenterPoint().getX(), symbols.get("labelCenter"));

                for (int i = 0; i < world.getNumberOfMonster(); ++i) {
                    Point monster = world.getMonster(i);
                    field.get(monster.getY()).set(monster.getX(), symbols.get("monster"));
                }

                Point people = world.getGroupOfPeople();
                if (field.get(people.getY()).get(people.getX()) == 'M')
                    field.get(people.getY()).set(people.getX(), symbols.get("battle"));
                else
                    field.get(people.getY()).set(people.getX(), symbols.get("people"));

                Point penguins = world.getGroupOfPenguins();
                field.get(penguins.getY()).set(penguins.getX(), symbols.get("penguins"));
            }

            private void clear() {
                for (int i = 0; i < height; ++i) {
                    for (int j = 0; j < width; ++j) {
                        field.get(i).set(j, symbols.get("empty"));
                    }
                }
            }

            public void print() {
                for (ArrayList<Character> strings : field) {
                    for (Character ch : strings) {
                        System.out.print(ch + " ");
                    }
                    System.out.println();
                }
            }

            static {
                symbols = new HashMap<>();
                symbols.put("empty", '`');
                symbols.put("penguin", 'p');
                symbols.put("penguins", 'P');
                symbols.put("human", 'h');
                symbols.put("people", 'H');
                symbols.put("monster", 'M');
                symbols.put("battle", 'B');
                symbols.put("normalZone", '+');
                symbols.put("middleZone", '^');
                symbols.put("hardZone", '%');
                symbols.put("label", '~');
                symbols.put("labelCenter", '?');
            }
        }

        if (this.people.getNumberOfCreatures() == 0) {
            throw new GroupNotMarkedException(people.getName());
        } else if (this.penguins.getNumberOfCreatures() == 0) {
            throw new GroupNotMarkedException(penguins.getName());
        }
        System.out.println("Подготовка к запуску симуляции...");

        while (mainland.addArea(new Area("Земля" + (mainland.sizeOfAreas() + 1), new Point(), new Point(),
                ((int) (Math.random() * 101)) / 100.0, ((int) (Math.random() * 101)) / 10.0))) {
            System.out.println("Определена " + mainland.getArea(mainland.sizeOfAreas() - 1) + "\n");
        }

        while (addMonster("Монстр" + (getNumberOfMonster() + 1),
                ((int) (Math.random() * 101)) / 100.0, (int) (Math.random() * 3 + 3))) {
            System.out.println("Добавлена " + devilishCreatures.get(getNumberOfMonster() - 1) + "\n");
        }

        IMakeSound penguinScream = (int x, int y) ->
                new Sound(x, y, "визг пингвина", SoundType.PENGUIN_SCREAM, (int) ((Math.random() * 2) + 2));
        IMakeSound devilishScream = (int x, int y) ->
                new Sound(x, y, "крик страшной твари", SoundType.MONSTER_SCREAM, (int) ((Math.random() * 3) + 4));

        WorldMap map = new WorldMap(
                this,
                mainland.getTopRightPoint().getY() - mainland.getBottomLeftPoint().getY() + 1,
                mainland.getTopRightPoint().getX() - mainland.getBottomLeftPoint().getX() + 1
        );
        map.print();

        boolean peopleReachedTheTarget = false;
        mainLoop: while (true) {
            for (int i = 0; i < getNumberOfMonster(); ++i) {
                DevilishCreature curr_monster = devilishCreatures.get(i);
                if (curr_monster.getCondition() == Condition.CONFUSED && Math.random() > 0.8)
                    curr_monster.setCondition(Condition.HEALTHY);
                if (curr_monster.getCondition() == Condition.HEALTHY) {
                    int deltaX = Math.abs(people.getX() - curr_monster.getX());
                    int deltaY = Math.abs(people.getY() - curr_monster.getY());
                    if (deltaX == 0 && deltaY == 0) {
                        if (startBattle(curr_monster, people)) {
                            System.out.println("Победа твари\n");
                            throw new PeopleWereKilledException(curr_monster.getName());
                        }
                        devilishCreatures.set(i, null);
                    }
                    else if ((deltaX <= 1 && deltaY <= 1) || deltaX * deltaX + deltaY * deltaY <=
                            curr_monster.senseOfSmell * curr_monster.senseOfSmell) {
                        curr_monster.goTo(people);
                    } else {
                        curr_monster.move();
                    }
                }
                if (Math.random() > 0.9)
                    mainland.addSound(devilishScream.makeSound(curr_monster.getX(), curr_monster.getY()));
            }

            penguins.move();
            if (Math.random() > 0.3)
                mainland.addSound(penguinScream.makeSound(penguins.getX(), penguins.getY()));

            if (people.getCreature(0).getMainTarget() != null &&
                    people.goTo(people.getCreature(0).getMainTarget()) || finish.isIncludedPoint(people)) {
                peopleReachedTheTarget = true;
                break;
            } else {
                for (int i = 0; i < mainland.getNumberOfSounds(); ++i) {
                    Sound curr_sound = mainland.getSound(i);
                    if (curr_sound.heard(people)) {
                        if (curr_sound.getType() == SoundType.PENGUIN_SCREAM) {
                            people.getCreature(0).setMainTarget(finish.getCenterPoint());
                            System.out.println("До членов группы " + people.getName() +
                                    " доносятся дребезжащие крики какой-то ужасной твари. Походу, это пингвины. " +
                                    "Определен источник звука");
                            break;
                        }
                        else if (curr_sound.getType() == SoundType.MONSTER_SCREAM)
                            System.out.println("До членов группы " + people.getName() + " доносятся страшные звуки");
                    }
                }
                if (people.getCreature(0).getMainTarget() == null)
                    people.move();
            }

            int i = 0;
            while (i < devilishCreatures.size()) {
                if (devilishCreatures.get(i) == null)
                    devilishCreatures.remove(i);
                else
                    ++i;
            }

            map.update();
            map.print();
            System.out.println();
            mainland.clearSounds();
        }

        return peopleReachedTheTarget;
    }

    static class Commentator {
        private static Commentator instance;
        private Commentator() {}
        Commentator printMessage(String message) {
            System.out.println(message);
            return Commentator.this;
        }

        Commentator printNewObject(Object obj) {
            if (Area.class.equals(obj.getClass()))
                System.out.println("Определена " + obj + "\n");
            else if (DevilishCreature.class.equals(obj.getClass()))
                System.out.println("Добавлена " + obj + "\n");
            else
                System.out.println("Определен объект " + obj);
            return instance;
        }
        protected static Commentator getInstance() {
            if (instance == null)
                instance = new Commentator();
            return instance;
        }
    }

    @Override
    public String toString() {
        return "Мир\n" +
                "Материк: " + mainland.getName() + "\n" +
                "Количество монстров: " + getNumberOfMonster() + "\n" +
                "Группа людей: " + ((people.getNumberOfCreatures() != 0) ? "определена" : "не определена") + "\n" +
                "Группа пингвинов: " + ((penguins.getNumberOfCreatures() != 0) ? "определена" : "не определена");
    }

    @Override
    public boolean equals(Object obj) {
        return getInstance() == obj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstance());
    }
}
