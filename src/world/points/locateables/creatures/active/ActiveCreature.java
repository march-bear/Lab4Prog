package world.points.locateables.creatures.active;

import world.Correctors;
import world.points.Point;
import world.points.locateables.creatures.Condition;
import world.points.locateables.creatures.Creature;
import world.points.locateables.creatures.IMove;
import world.squares.Area;

import java.util.Objects;

public abstract class ActiveCreature extends Creature implements IMove {
    protected int hp;
    protected final int attackDamage;
    protected Condition condition = Condition.HEALTHY;

    ActiveCreature(int hp, String name, Area square, int attackDamage, int x, int y, int speed) {
        super(x, y, name, square, speed);
        this.hp = hp;
        this.attackDamage = attackDamage;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean goTo(Point target) {
        int x = target.getX();
        int y = target.getY();
        int deltaX = (Math.min(Math.abs(x - this.x), speed)) * ((x > this.x) ? 1 : -1);
        this.x = Correctors.correctInt(this.x + deltaX, square.getBottomLeftPoint().getX(),
                square.getTopRightPoint().getX());
        int deltaY = (Math.min(Math.abs(y - this.y), speed - Math.abs(deltaX))) * ((y > this.y) ? 1 : -1);
        this.y = Correctors.correctInt(this.y + deltaY, square.getBottomLeftPoint().getY(),
                square.getTopRightPoint().getY());
        return this.x == x && this.y == y;
    }

    public abstract void attackTarget(ActiveCreature target);

    protected abstract void useSpecialAttack(ActiveCreature target);

    public void loseHP(int damage) {
        if (damage > 0) {
            if (damage >= this.hp) {
                System.out.println(name + " теряет все здоровье!");
                this.hp = 0;
            }
            else {
                System.out.println(name + " теряет " + damage + " очков здоровья.");
                this.hp -= damage;
            }
        }
    }

    @Override
    public String toString() {
        return "активное существо " + name + "\n" +
                "Координаты: (" + x + ", " + y + ")\n" +
                "Локация: " + square.getName() + "\n" +
                "Скорость передвижения: " + speed + "\n" +
                "HP: " + hp + "\n" +
                "Состояние: " + condition.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return ((ActiveCreature) obj).getHp() == this.hp &&
                    ((ActiveCreature) obj).getAttackDamage() == this.attackDamage;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name, square, speed, attackDamage);
    }
}
