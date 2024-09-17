package info.sigmaclient.sigma.sigma5.utils;

public enum ColorUtils {
    BLACK(-16711423),
    DARK_BLUE(-16723258),
    DARK_GREEN(-15698006),
    DARK_AQUA(-9581017),
    DARK_RED(-11231458),
    WHITE(-65794),
    GRAY(-14163205),
    DARK_GRAY(-16548724),
    BLUE(-6710887),
    GREEN(-12303292),
    AQUA(-43691),
    RED(-7864320),
    LIGHT_PURPLE(-21931),
    YELLOW(-7846912),
    LIGHT_BLUE(-171),
    LIGHT_GREEN(-7829504),
    LIGHT_AQUA(-43521),
    LIGHT_RED(-7864184),
    LIGHT_GRAY(-16724271);

    public final int ColorCode;

    private ColorUtils(final int ColorCode) {
        this.ColorCode = ColorCode;
    }
}
    