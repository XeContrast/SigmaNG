package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;

public class JumpEvent extends Event {
    public boolean boost;
    public float yaw;
    public JumpEvent(float yaw,boolean boost) {
        this.yaw = yaw;
        this.boost = boost;
    }
}
