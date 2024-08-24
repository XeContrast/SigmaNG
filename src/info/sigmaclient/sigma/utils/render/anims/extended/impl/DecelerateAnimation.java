package info.sigmaclient.sigma.utils.render.anims.extended.impl;


import info.sigmaclient.sigma.utils.render.anims.extended.Animation;

public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    protected double getEquation(double x) {
        return 1 - ((x - 1) * (x - 1));
    }

}
