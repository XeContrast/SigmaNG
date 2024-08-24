package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;

public class StrafeEvent extends Event {

    public double strafe , forword , friction;
    public float yaw;

    public StrafeEvent(double strafe, double forword, double friction, float yaw) {
        this.strafe = strafe;
        this.forword = forword;
        this.friction = friction;
        this.yaw = yaw;
    }
}
