package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.network.play.client.CClientStatusPacket;


public class AutoRespawn extends Module {

    public AutoRespawn() {
        super("AutoRespawn", Category.Player, "Auto respawn");
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            if(mc.currentScreen instanceof DeathScreen){
                mc.getConnection().sendPacket(new CClientStatusPacket(CClientStatusPacket.State.PERFORM_RESPAWN));
                mc.displayGuiScreen(null);
            }
        }
       
    }
}
