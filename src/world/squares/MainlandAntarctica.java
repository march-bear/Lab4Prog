package world.squares;

import world.Correctors;
import world.points.locateables.Sound;
import world.points.Point;

import java.util.ArrayList;
import java.util.Objects;

public class MainlandAntarctica extends Area {
    private static MainlandAntarctica instance;
    private final ArrayList<Area> areas;
    private final ArrayList<Sound> sounds;
    private int numberOfAreas = 5;

    private MainlandAntarctica() {
        super("Антарктида", new Point(0, 0), new Point(Point.maxXY, Point.maxXY));
        areas = new ArrayList<>();
        sounds = new ArrayList<>();
    }

    public static MainlandAntarctica getInstance() {
        if (instance == null)
            instance = new MainlandAntarctica();
        return instance;
    }

    public Area getArea(int index) {
        if (index >= areas.size() || index < 0)
            return instance;
        return areas.get(index);
    }

    public boolean addArea(Area area) {
        if (sizeOfAreas() < numberOfAreas) {
            areas.add(area);
            return true;
        }
        return false;
    }

    public int sizeOfAreas() {
        return areas.size();
    }

    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    public Sound getSound(int index) {
        return sounds.get(index);
    }

    public int getNumberOfSounds() {
        return sounds.size();
    }

    public void clearSounds() {
        sounds.clear();
    }

    public Area whatArea(Point point) {
        for (Area area : areas) {
            if (area.isIncludedPoint(point)) {
                return area;
            }
        }
        return instance;
    }

    public void setNumberOfAreas(int number) {
        numberOfAreas = Correctors.correctInt(number, Point.maxXY / 10, Point.maxXY / 5);
    }

    @Override
    public String toString() {
        return "материк " + name + "\n" +
                "Количество областей: " + numberOfAreas;
    }

    @Override
    public boolean equals(Object obj) {
        return getInstance() == obj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance);
    }
}
