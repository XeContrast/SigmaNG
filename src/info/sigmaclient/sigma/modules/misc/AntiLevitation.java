package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class AntiLevitation extends Module {
    public AntiLevitation() {
        super("AntiLevitation", Category.Misc, "Anti levitation effect.");
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPost()){

        }
       
    }
}
