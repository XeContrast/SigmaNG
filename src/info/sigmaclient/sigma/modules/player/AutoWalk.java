package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.util.InputMappings;

public final class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", Category.Player, "Automatically walks forward");
    }

     @Override
    public void onDisable() {
        mc.gameSettings.keyBindForward.pressed = InputMappings.isKeyDown(mc.gameSettings.keyBindForward);
        super.onDisable();
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()) {
            mc.gameSettings.keyBindForward.pressed = true;
        }
       
    }
}
