package info.sigmaclient.sigma.modules.combat;


import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.Hand;



public class NoInteract extends Module {
    public NoInteract() {
        super("NoInteract", Category.Combat, "No interact special blocks");
    }

   @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if (event.packet instanceof CPlayerTryUseItemOnBlockPacket || event.packet instanceof CUseEntityPacket) {
            if (event.packet instanceof CUseEntityPacket && (((CUseEntityPacket) event.packet).getAction().equals(CUseEntityPacket.Action.INTERACT) || ((CUseEntityPacket) event.packet).getAction().equals(CUseEntityPacket.Action.INTERACT_AT))) {
                mc.getConnection().sendPacketNOEvent(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                event.cancelable = true;
            }
            if (event.packet instanceof CPlayerTryUseItemOnBlockPacket) {
                mc.getConnection().sendPacketNOEvent(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                event.cancelable = true;
            }
        }
        
    }
}
