package world;

public final class Correctors {
    private Correctors() {}

    public static int correctInt(int num, int min, int max) {
        if (num > max)
            num = max;
        else if (num < min)
            num = min;
        return num;
    }

    public static double correctDouble(double num, double min, double max) {
        if (num - max > 0.00001)
            num = max;
        else if (num - min < 0.00001)
            num = min;
        return num;
    }
}
