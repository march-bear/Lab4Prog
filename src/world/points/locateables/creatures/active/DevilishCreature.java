package world.points.locateables.creatures.active;

import world.Correctors;
import world.points.Point;
import world.points.locateables.SoundType;
import world.points.locateables.Sound;
import world.points.locateables.creatures.Creature;
import world.squares.Area;
import world.squares.Label;
import world.squares.Square;

import java.util.Objects;

public final class DevilishCreature extends ActiveCreature {
    public final double bloodlust;
    public final int senseOfSmell;

    public DevilishCreature(String name, Area square, int x, int y, double bloodlust, int senseOfSmell) {
        super(45, name, square, (int) (Math.random() * 8 + 20), x, y, 3);
        this.bloodlust = Correctors.correctDouble(bloodlust, 0.0, 1.0);
        this.senseOfSmell = Correctors.correctInt(senseOfSmell, 0, maxXY / 5);
    }

    public DevilishCreature(String name, Area square, double bloodlust, int senseOfSmell) {
        this(name, square, randomX(square), randomY(square), bloodlust, senseOfSmell);
    }

    @Override
    public void attackTarget(ActiveCreature target) {
        if (Math.random() > 0.9)
            useSpecialAttack(target);
        else
            target.loseHP(this.attackDamage);
    }

    @Override
    public void useSpecialAttack(ActiveCreature target) {
        target.loseHP(this.getAttackDamage() * 2);
    }

    @Override
    public String toString() {
        return "тварь " + name + "\n" +
                "Координаты: (" + x + ", " + y + ")\n" +
                "Локация: " + square.getName() + "\n" +
                "Скорость передвижения: " + speed + "\n" +
                "Кровожадность: " + (int) (bloodlust * 100) + "%\n" +
                "Нюх: " + senseOfSmell + "\n" +
                "HP: " + hp + "\n" +
                "Состояние: " + condition.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return ((DevilishCreature) obj).bloodlust == this.bloodlust &&
                    ((DevilishCreature) obj).senseOfSmell == this.senseOfSmell;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name, square, speed, attackDamage, bloodlust, senseOfSmell);
    }
}
