package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;
import lombok.Getter;

public class MouseClickEvent extends Event {
    @Getter
    public boolean isAttack = false;
    public int rightClickDelay = -1;
    public MouseClickEvent(boolean isAttack){
        this.isAttack = isAttack;
    }
}
