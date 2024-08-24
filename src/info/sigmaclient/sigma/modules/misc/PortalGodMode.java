package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;

public final class PortalGodMode extends Module {
    public PortalGodMode() {
        super("PortalGodMode", Category.Misc, "Makes you invulnerable when you in portal");
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(mc.player.inPortal){
            event.cancelable = true;
        }
       
    }
}
