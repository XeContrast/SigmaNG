package info.sigmaclient.sigma.process.impl.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.other.WorldChangeEvent;
import info.sigmaclient.sigma.process.BProcess;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CKeepAlivePacket;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.CopyOnWriteArrayList;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public class BlinkProcess extends BProcess {
    private static boolean blink;
    public static Vector3d lastPos = new Vector3d(0,0,0);
    private static boolean KeepAlive;
    private static boolean CConfirmTransaction;
    private static CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList<>();

    @Override
    public void onInit() {
        super.onInit();
        blink = false;
        packets.clear();
    }

    @EventTarget
    public void onPacket(PacketEvent event){
        if(!event.isSend() || event.cancelable)return;
        if(blink){
            if(!KeepAlive && event.packet instanceof CKeepAlivePacket){
                return;
            }
            if(!CConfirmTransaction && event.packet instanceof CConfirmTransactionPacket){
                return;
            }
            if(!packets.contains(event.packet)){
                packets.add(event.packet);
                event.cancelable = true;
            }
        }
    }

    public static void startBlink(){
        blink = true;
        KeepAlive = true;
        CConfirmTransaction = true;
        lastPos = new Vector3d(mc.player.getPosX(),mc.player.getPosY(),mc.player.getPosZ());
    }
    public static void startBlink(boolean keepAlive,boolean cConfirmTransaction){
        blink = true;
        KeepAlive = keepAlive;
        CConfirmTransaction = cConfirmTransaction;
        lastPos = new Vector3d(mc.player.getPosX(),mc.player.getPosY(),mc.player.getPosZ());
    }

    public static void stopBlink(){
        blink = false;
        release();
        lastPos = new Vector3d(mc.player.getPosX(),mc.player.getPosY(),mc.player.getPosZ());
    }

    public static void release(){
        if(packets.isEmpty())return;
        packets.forEach(mc.getConnection()::sendPacketNOEvent);
        packets.clear();
    }

    public static boolean isBlinking() {
        return blink;
    }

    public static int getSize(){
        return packets.size();
    }

    @EventTarget
    public void onWorldChange(WorldChangeEvent event){
        blink = false;
        release();
    }
}
