package world;

public class PeopleWereKilledException extends RuntimeException {
    public PeopleWereKilledException(String monsterName) {
        super("Люди убиты тварью " + monsterName);
    }
}
