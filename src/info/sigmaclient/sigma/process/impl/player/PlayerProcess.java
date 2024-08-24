package info.sigmaclient.sigma.process.impl.player;

import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.process.BProcess;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public class PlayerProcess extends BProcess {
    @EventPriority(1)
    @EventTarget
    public void onMotion(UpdateEvent event){
        if(event.isPre()){
            if(event.onGround){
                mc.player.onGroundTicks++;
                mc.player.offGroundTicks = 0;
            }else {
                mc.player.offGroundTicks++;
                mc.player.onGroundTicks = 0;
            }
        }
    }
}
