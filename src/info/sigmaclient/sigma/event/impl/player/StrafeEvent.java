package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrafeEvent extends Event {

    public double strafe , forward, friction;
    public float yaw;

    public StrafeEvent(double strafe, double forward, double friction, float yaw) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
    }
}
