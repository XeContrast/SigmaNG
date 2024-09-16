package info.sigmaclient.sigma.sigma5.utils;

import java.util.Random;

public class RandomGenerator extends Random {
    private static String[] 괠㝛湗骰鶊;
    private static final long 䈔롤竬붛ࡅ = 1L;

    public int nextInt(final int n, final int n2) {
        return this.nextInt(n2 - n) + n;
    }
}
    