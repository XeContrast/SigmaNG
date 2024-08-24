package info.sigmaclient.sigma.sigma5.jelloportal.viafix.V_1_17;


import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.vialoadingbase.ViaLoadingBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CConfirmTransactionPacket;

public class V1_17_PlaceFix {
    public void fixPingPong(ByteBuf buf){
//        ClientboundPackets1_17.PING;
//        ServerboundPackets1_17.PONG;
        if (buf.isReadable()) {
            int id = Type.VAR_INT.readPrimitive(buf);
            if (id == 1000) {
            } else {
//                PacketWrapper wrapper = new PacketWrapperImpl(id, buf, null);
                if(id == ServerboundPackets1_17.PONG.getId()){
                    Minecraft.getInstance().getConnection().sendPacket(new CConfirmTransactionPacket(0, (short) Type.VAR_INT.readPrimitive(buf),true));
                }
            }
        }
    }
    public boolean isEnable(){
        return ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_17);
    }
    public void fix(){
//        Minecraft.getMinecraft().getConnection().sendPacket(new CPlayerPacket(Minecraft.getMinecraft().player.onGround));
    }
}
