package info.sigmaclient.sigma.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static float nextFloat(double origin, double bound) {
        if (origin == bound) {
            return (float)origin;
        }
        return (float)ThreadLocalRandom.current().nextDouble((float)origin, (float)bound);
    }

    public static float nextFloat(float origin, float bound) {
        if (origin == bound) {
            return origin;
        }
        return (float) ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public static double getRandom(double min, double max) {
        Random random = new Random();
        double randomValue = min + (max - min) * random.nextDouble();

        double bigDecimalValue = randomValue;

        return bigDecimalValue;
    }

    public static double nextDouble(double origin, double bound) {
        if (origin == bound) {
            return origin;
        }
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }
}
