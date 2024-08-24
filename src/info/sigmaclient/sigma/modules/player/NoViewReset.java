package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;


public class NoViewReset extends Module {

    public NoViewReset() {
        super("NoViewReset", Category.Player, "Dont set my rotation.");
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof SPlayerPositionLookPacket){
            lastRotation = new float[]{0, 0};
            lastRotation[0] = mc.player.rotationYaw;
            lastRotation[1] = mc.player.rotationPitch;
//            ((SPlayerPositionLookPacket) event.packet).yaw = mc.player.rotationYaw;
//            ((SPlayerPositionLookPacket) event.packet).pitch = mc.player.rotationPitch;
        }
        
    }
    float[] lastRotation = null;
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            if(lastRotation != null){
                mc.player.rotationYaw = lastRotation[0];
                mc.player.rotationPitch = lastRotation[1];
                lastRotation = null;
            }
        }
       
    }
}
