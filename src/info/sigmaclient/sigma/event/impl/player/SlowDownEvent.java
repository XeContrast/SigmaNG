package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;

public class SlowDownEvent extends Event {
    public float strafe , forward;
    public boolean sprint;
    public SlowDownEvent(float strafe,float forward,boolean sprint) {
        this.strafe = strafe;
        this.forward = forward;
        this.sprint = sprint;
    }
}
