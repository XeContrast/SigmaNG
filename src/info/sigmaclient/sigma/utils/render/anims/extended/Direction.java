package info.sigmaclient.sigma.utils.render.anims.extended;

public enum Direction {
    FORWARDS,
    BACKWARDS,
    STOPS;


    public boolean forwards() {
        return this == Direction.FORWARDS;
    }

    public boolean backwards() {
        return this == Direction.BACKWARDS;
    }

    public boolean stops() {
        return this == Direction.STOPS;
    }
}
