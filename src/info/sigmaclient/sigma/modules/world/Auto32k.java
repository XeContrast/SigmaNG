package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MouseClickEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class Auto32k extends Module {
    public Auto32k() {
        super("Auto32k", Category.World, "Auto place shulk and use 32k. (cant use)");
    }
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
       
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
        super.onDisable();
    }
}
