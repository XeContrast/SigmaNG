package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MouseClickEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.block.AirBlock;
import net.minecraft.util.math.BlockPos;


public class Eagle extends Module {
    public Eagle() {
        super("Eagle", Category.World, "Sneak un sat block.");
    }
    int sneakAfterTick = 0;
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()) return;
        boolean block = mc.world.getBlockState(
                new BlockPos(mc.player.getPosX(), mc.player.getPosY() - 0.5, mc.player.getPosZ())
        ).getBlock() instanceof AirBlock;
        if(!mc.player.onGround) {
            mc.gameSettings.keyBindSneak.pressed = false;
            return;
        }
        if(block){
            mc.gameSettings.keyBindSneak.pressed = true;
            sneakAfterTick = 2;
        }else{
            if(sneakAfterTick > 0){
                sneakAfterTick --;
                if(sneakAfterTick == 0){
                    mc.gameSettings.keyBindSneak.pressed = false;
                }
            }
        }
       
    }

    @EventTarget
    public void onMouseClickEvent(MouseClickEvent event) {
        
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }
}
