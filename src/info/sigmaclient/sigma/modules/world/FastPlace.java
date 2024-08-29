package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MouseClickEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class FastPlace extends Module {
    NumberValue timer = new NumberValue("Time", 1.0, 0, 20.0, NumberValue.NUMBER_TYPE.INT);
    public FastPlace() {
        super("FastPlace", Category.World, "Place no delay.");
     registerValue(timer);
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
       
    }

    @EventTarget
    public void onMouseClickEvent(MouseClickEvent event) {
        if(!event.isAttack){
            event.rightClickDelay = timer.getValue().intValue();
        }
        
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        super.onDisable();
    }
}
