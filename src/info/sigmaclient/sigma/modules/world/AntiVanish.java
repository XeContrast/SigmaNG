package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.gui.hud.notification.NotificationManager;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;


public class AntiVanish extends Module {

    BooleanValue error = new BooleanValue("Error", false);
    public AntiVanish() {
        super("AntiVanish", Category.World, "Detects if there are vanished players.");
     registerValue(error);
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof SDestroyEntitiesPacket){
            for(int i : ((SDestroyEntitiesPacket) event.packet).getEntityIDs()){
                assert mc.world != null;
                Entity e = mc.world.getEntityByID(i);
                if(e == null){
                    if(error.isEnable())
                        NotificationManager.notify("Vanish Error", "Something bad happened.");
                    continue;
                }
                if(e instanceof PlayerEntity){
                    NotificationManager.notify("Vanish Warning", "A player is vanished !!");
                }
            }
        }
        
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){

       
    }
}
