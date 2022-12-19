package world.points.locateables;

public enum SoundType {
    WHISTLING("свист"),
    PENGUIN_SCREAM("крик пингвина"),
    MONSTER_SCREAM("крик монстера"),
    SPEECH("речь");

    final String name;

    SoundType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
