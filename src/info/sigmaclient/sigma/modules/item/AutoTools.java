package info.sigmaclient.sigma.modules.item;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;


public class AutoTools extends Module {
    public AutoTools() {
        super("AutoTools", Category.Item, "Auto select tools");
    }
    int oldSlot = -1;
    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        if(mc.gameSettings.keyBindAttack.pressed && mc.currentScreen == null){
            if(mc.objectMouseOver != null){
                if(mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK){
                    BlockState block = mc.world.getBlockState(((BlockRayTraceResult) mc.objectMouseOver).getPos());
                    int bestSlot = -1;
                    float digSpeed = 1;
                    for(int i = 0;i <= 8;i ++){
                        ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                        float d = itemStack.getDestroySpeed(block);
                        if(d > digSpeed){
                            bestSlot = i;
                            digSpeed = d;
                        }
                    }
                    if(bestSlot != -1){
                        if(oldSlot == -1)
                            oldSlot = mc.player.inventory.currentItem;
                        mc.player.inventory.currentItem = bestSlot;
                    }
                }
            }
        }else{
            if(oldSlot != -1){
                mc.player.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
        }
       
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
       
    }
}
