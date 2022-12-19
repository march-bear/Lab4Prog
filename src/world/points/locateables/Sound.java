package world.points.locateables;

import world.Correctors;
import world.squares.MainlandAntarctica;

public final class Sound extends Locateable {
    final private SoundType type;
    final private int volume;

    public Sound(String name, SoundType type, int volume) {
        this(randomXY(), randomXY(), name, type, volume);
    }

    public Sound(int x, int y, String name, SoundType type, int volume) {
        super(x, y, name, MainlandAntarctica.getInstance());
        this.type = type;
        this.volume = Correctors.correctInt(volume, 1, maxXY / 5 + 1);
    }

    public SoundType getType() {
        return type;
    }

    public boolean heard(Locateable l) {
        return Math.pow(l.getX() - this.x, 2) + Math.pow(l.getY() - this.y, 2) <=
                Math.pow(volume * l.square.getAudibility(), 2);
    }
}
