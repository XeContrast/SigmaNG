package info.sigmaclient.sigma.utils.render.anims.extended.impl;


import info.sigmaclient.sigma.utils.render.anims.extended.Animation;
import info.sigmaclient.sigma.utils.render.anims.extended.Direction;

public class EaseBackIn extends Animation {
    private final float easeAmount;

    public EaseBackIn(int ms, double endPoint, float easeAmount) {
        super(ms, endPoint);
        this.easeAmount = easeAmount;
    }

    public EaseBackIn(int ms, double endPoint, float easeAmount, Direction direction) {
        super(ms, endPoint, direction);
        this.easeAmount = easeAmount;
    }

    @Override
    protected double getEquation(double x) {
        float shrink = easeAmount + 1;
        return Math.max(0, 1 + shrink * Math.pow(x - 1, 3) + easeAmount * Math.pow(x - 1, 2));
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

}
