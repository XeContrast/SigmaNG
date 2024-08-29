package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.play.server.SChatPacket;


public class ChatCleaner extends Module {
    public ChatCleaner() {
        super("ChatCleaner", Category.Misc, "Dont blame me!");
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
       
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof SChatPacket){
            if(((SChatPacket) event.packet).isSystem()) return;
            String n = ((SChatPacket) event.packet).getChatComponent().toString();
            if(n.startsWith("<") || n.contains(": ")){
                event.cancelable = true;
            }
        }
        
    }
}
