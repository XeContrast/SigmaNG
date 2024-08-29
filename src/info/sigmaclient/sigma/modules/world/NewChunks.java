package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MouseClickEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;

public class NewChunks extends Module {
    int ticks = 0;
    public NewChunks() {
        super("NewChunks", Category.World, "Detect new chunks on not vanilla servers.");
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
       
    }
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
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
