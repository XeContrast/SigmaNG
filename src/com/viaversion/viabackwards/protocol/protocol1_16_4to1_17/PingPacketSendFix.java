package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import info.sigmaclient.sigma.utils.ChatUtils;

import java.util.ArrayList;

public class PingPacketSendFix {
    public static ArrayList<Runnable> lastPong = new ArrayList<>();
    public static void pushAll(){
        lastPong.forEach(Runnable::run);
        lastPong.clear();
    }
    public static void handlePingPacket(PacketWrapper wrapper){
        try {
            wrapper.cancel();
            int id = wrapper.read(Type.INT);
            short shortId = (short)id;
            final PacketWrapper[] pongPacket = new PacketWrapper[1];
            if (id == shortId && ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                wrapper.user().get(PingRequests.class).addId(shortId);
                pongPacket[0] = wrapper.create(ClientboundPackets1_16_2.WINDOW_CONFIRMATION);
                pongPacket[0].write(Type.UNSIGNED_BYTE, (short) 0);
                pongPacket[0].write(Type.SHORT, shortId);
                pongPacket[0].write(Type.BOOLEAN, false);
                pongPacket[0].send(Protocol1_16_4To1_17.class);
            } else {
                pushAll();
                lastPong.add(() -> {
                    pongPacket[0] = wrapper.create(ServerboundPackets1_17.PONG);
                    pongPacket[0].write(Type.INT, id);
                    try {
                        pongPacket[0].sendToServer(Protocol1_16_4To1_17.class);
                    } catch (Exception e) {
                        ChatUtils.sendMessageWithPrefix("Something bad happend!");
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
