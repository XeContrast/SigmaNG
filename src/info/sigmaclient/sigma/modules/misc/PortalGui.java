package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;

public final class PortalGui extends Module {
    public PortalGui() {
        super("PortalGui", Category.Misc, "Allows GUIs while in nether portal");
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(mc.player.inPortal){
            event.cancelable = true;
        }
       
    }
}
