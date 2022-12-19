package world.points.locateables.creatures.active;

import world.points.Point;
import world.points.locateables.SoundType;
import world.points.locateables.Sound;
import world.points.locateables.creatures.Condition;
import world.points.locateables.creatures.Creature;
import world.squares.Label;
import world.squares.MainlandAntarctica;

public final class Human extends ActiveCreature {
    private Point mainTarget = null;
    public Human(String name) {
        this(name, 0, 0);
    }
    public Human(String name, int x, int y) {
        super(100, name, MainlandAntarctica.getInstance(), 10, x, y, 3);
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
        target.setCondition(Condition.CONFUSED);
    }

    public Point getMainTarget() {
        if (mainTarget != null)
            return new Point(mainTarget.getX(), mainTarget.getY());
        return null;
    }

    public void setMainTarget(Point mainTarget) {
        this.mainTarget = mainTarget;
    }

    @Override
    public String toString() {
        return "человек " + name + "\n" +
                "Координаты: (" + x + ", " + y + ")\n" +
                "Локация: " + square.getName() + "\n" +
                "Скорость передвижения: " + speed + "\n" +
                "HP: " + hp + "\n" +
                "Состояние: " + condition.getName();
    }
}
