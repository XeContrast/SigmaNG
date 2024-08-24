package info.sigmaclient.sigma.utils.render.anims.extended.impl;


import info.sigmaclient.sigma.utils.render.anims.extended.Animation;

public class EaseOutSine extends Animation {

    public EaseOutSine(int ms, double endPoint) {
        super(ms, endPoint);
    }

    @Override
    protected double getEquation(double x) {
        return Math.sin(x * (Math.PI / 2));
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

}
