package info.sigmaclient.sigma.process.impl.packet;


import info.sigmaclient.sigma.process.BProcess;
import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import net.minecraft.item.AirItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.*;
import net.minecraft.util.Hand;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public final class BadPacketsProcess extends BProcess {

    private static boolean slot, attack, swing, mainBlock, offBlock, unblock, inventory;

    public static boolean bad() {
        return bad(true, true, true, true,true, true,true);
    }

    public static boolean bad(final boolean slot, final boolean attack, final boolean swing, final boolean main_block,  final boolean off_block,final boolean unblock, final boolean inventory) {
        return (BadPacketsProcess.slot && slot) ||
                (BadPacketsProcess.attack && attack) ||
                (BadPacketsProcess.swing && swing) ||
                (BadPacketsProcess.mainBlock && main_block) ||
                (BadPacketsProcess.offBlock && off_block) ||
                (BadPacketsProcess.inventory && inventory) ||
                (BadPacketsProcess.unblock && unblock);
    }

    @EventPriority(5)
    @EventTarget
    public  void onPacketSend(PacketEvent event){
        if(!event.isSend())return;
        if(event.cancelable)return;

        final IPacket<?> packet = event.getPacket();

        if(packet instanceof CPlayerDiggingPacket){
            unblock = true;
        }else if (packet instanceof CHeldItemChangePacket) {
            slot = true;
        } else if (packet instanceof CAnimateHandPacket) {
            swing = true;
        } else if (packet instanceof CUseEntityPacket) {
            attack = true;
        } else if (packet instanceof CPlayerTryUseItemPacket) {
            if(((CPlayerTryUseItemPacket) packet).getHand() == Hand.MAIN_HAND) {
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof AirItem)) {
                    mainBlock = true;
                }
            }else if(((CPlayerTryUseItemPacket) packet).getHand() == Hand.OFF_HAND) {
                if (!(mc.player.getHeldItemOffhand().getItem() instanceof AirItem)){
                    offBlock = true;
                }
            }
        } else if (packet instanceof CClickWindowPacket ||
                (packet instanceof CEntityActionPacket && ((CEntityActionPacket) packet).getAction() == CEntityActionPacket.Action.OPEN_INVENTORY) ||
                packet instanceof CCloseWindowPacket) {
            inventory = true;
        } else if (packet instanceof CPlayerPacket) {
            reset();
        }
    };

    public static void reset() {
        slot = false;
        swing = false;
        attack = false;
        mainBlock = false;
        offBlock = false;
        inventory = false;
        unblock = false;
    }
}
