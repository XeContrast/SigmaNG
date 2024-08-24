package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;
import lombok.Getter;
import lombok.Setter;

public class MoveEvent extends Event {
    @Getter
    @Setter
    private double x;
    @Getter
    @Setter
    private double y;
    @Getter
    @Setter
    private double z;

    public MoveEvent(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setMoveSpeed(double v) {

    }
}
