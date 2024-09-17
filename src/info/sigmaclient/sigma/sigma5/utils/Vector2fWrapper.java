package info.sigmaclient.sigma.sigma5.utils;

public class Vector2fWrapper {
    private static String[] names;
    public static final Vector2fWrapper ZERO;
    public static final Vector2fWrapper ONE;
    public static final Vector2fWrapper UNIT_X;
    public static final Vector2fWrapper NEGATIVE_UNIT_X;
    public static final Vector2fWrapper UNIT_Y;
    public static final Vector2fWrapper NEGATIVE_UNIT_Y;
    public static final Vector2fWrapper MAX_VALUE;
    public static final Vector2fWrapper MIN_VALUE;
    public final float x;
    public final float y;

    public Vector2fWrapper(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(final Vector2fWrapper other) {
        return this.x == other.x && this.y == other.y;
    }

    static {
        ZERO = new Vector2fWrapper(0.0f, 0.0f);
        ONE = new Vector2fWrapper(1.0f, 1.0f);
        UNIT_X = new Vector2fWrapper(1.0f, 0.0f);
        NEGATIVE_UNIT_X = new Vector2fWrapper(-1.0f, 0.0f);
        UNIT_Y = new Vector2fWrapper(0.0f, 1.0f);
        NEGATIVE_UNIT_Y = new Vector2fWrapper(0.0f, -1.0f);
        MAX_VALUE = new Vector2fWrapper(Float.MAX_VALUE, Float.MAX_VALUE);
        MIN_VALUE = new Vector2fWrapper(Float.MIN_VALUE, Float.MIN_VALUE);
    }
}
