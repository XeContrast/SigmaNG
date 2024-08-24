package info.sigmaclient.sigma.utils.render.anims.extended.impl;

import info.sigmaclient.sigma.utils.render.anims.extended.Animation;
import info.sigmaclient.sigma.utils.render.anims.extended.Direction;

public class SmoothStepAnimation extends Animation {

    public SmoothStepAnimation(int ms, double endPoint) {
        super(ms, endPoint, Direction.FORWARDS);
    }

    public SmoothStepAnimation(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x) {
        return -2 * Math.pow(x, 3) + (3 * Math.pow(x, 2));
    }

}
