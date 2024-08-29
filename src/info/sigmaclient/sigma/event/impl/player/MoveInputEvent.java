package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;

public class MoveInputEvent extends Event {
    private float forward,strafe;

    public MoveInputEvent(float forward, float strafe) {
        this.forward = forward;
        this.strafe = strafe;
    }

    public float getForward() {
        return forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getStrafe() {
        return strafe;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }
}
