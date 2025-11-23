package src.main.java.ga.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }
}
