package world.points.locateables.creatures;

public enum Condition {
    DISABLED("инвалид"),
    CONFUSED("в замешательстве"),
    HEALTHY("здоров");

    final String name;

    Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
