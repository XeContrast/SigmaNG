package info.sigmaclient.sigma.event.impl.player;

import info.sigmaclient.sigma.event.Event;
import lombok.Getter;

public class KeyEvent extends Event {
    @Getter
    public int key;
    public KeyEvent(int key){
        this.key = key;
    }
}
