
package info.sigmaclient.sigma.modules.item;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.WorldEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SDestroyEntitiesPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;

import java.util.HashMap;


public class TotemPoper extends Module {
    HashMap<Entity, Integer> totemMap = new HashMap<>();
    public TotemPoper() {
        super("TotemPoper", Category.Item, "Show enmy's totem count");
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof SEntityStatusPacket){
            if(((SEntityStatusPacket) event.packet).getOpCode() == (byte)35){
                Entity e = ((SEntityStatusPacket) event.packet).getEntity(mc.world);
                if(!(e instanceof PlayerEntity)) return;
                if(!totemMap.containsKey(e))
                    totemMap.put(e, 1);
                    else
                    totemMap.put(e, totemMap.get(e) + 1);
                ChatUtils.sendMessage("§f[§6Sigma§f] §7" + e.getName().getUnformattedComponentText() + " popped " + totemMap.get(e) + " totem(s)!");
            }
        }else if(event.packet instanceof SDestroyEntitiesPacket){
            for(int i : ((SDestroyEntitiesPacket) event.packet).getEntityIDs()){
                Entity e = mc.world.getEntityByID(i);
                if(e instanceof PlayerEntity){
                    if(totemMap.containsKey(e)) {
                        ChatUtils.sendMessage("§f[§6Sigma§f] §7" + e.getName().getUnformattedComponentText() + " has " + totemMap.get(e) + " totem(s), §cDIED!!!");
                        totemMap.remove(e);
                    }
                }
            }
        }
        
    }

    @EventTarget
    public void onWorldEvent(WorldEvent event) {
        totemMap.clear();
    }
}
