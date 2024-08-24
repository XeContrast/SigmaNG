package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.potion.Effect;

public class AntiBlind extends Module {
    public AntiBlind() {
        super("AntiBlind", Category.Render, "Now i can see you.");
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPost()){
            if(mc.player.isPotionActive(Effect.get(15))){
                mc.player.removePotionEffect(Effect.get(15));
            }
        }
       
    }
}
