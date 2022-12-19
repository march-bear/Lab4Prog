package world;

public class GroupNotMarkedException extends Exception {
    public GroupNotMarkedException(String nameOfGroup) {
        super("Симуляция не может быть запущена: группа " + nameOfGroup + " не определена.");
    }
}
