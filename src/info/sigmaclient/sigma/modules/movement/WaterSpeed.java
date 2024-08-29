package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.BlockColEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.player.MovementUtils;


public class WaterSpeed extends Module {
    public ModeValue type = new ModeValue("Type", "Vanilla", new String[]{"Vanilla", "Matrix"});
    public WaterSpeed() {
        super("WaterSpeed", Category.Movement, "Faster on the liquid");
     registerValue(type);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        super.onDisable();
    }
    @EventTarget
    public void onBlockColEvent(BlockColEvent event) {
        
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            if(mc.player.isInWater()){
                switch (type.getValue()){
                    case "Vanilla"-> MovementUtils.strafing(MovementUtils.getBaseMoveSpeed());
                    case "Matrix"->{
                        if(mc.player.isSwimming()) {
                            mc.player.setSprinting(true);
                            MovementUtils.strafing(MovementUtils.getSpeed() * 0.8f + MovementUtils.getBaseMoveSpeed() * 0.3f);
                        }
                    }
                }
            }
        }
       
    }
}
