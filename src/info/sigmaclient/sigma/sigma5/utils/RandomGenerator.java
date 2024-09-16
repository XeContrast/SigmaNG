package info.sigmaclient.sigma.sigma5.utils;

import java.util.Random;

public class RandomGenerator extends Random {
    private static String[] randomStrings;
    private static final long uniqueID = 1L;

    public int nextInt(final int n, final int n2) {
        return this.nextInt(n2 - n) + n;
    }
}
