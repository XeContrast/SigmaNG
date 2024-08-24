package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.combat.Killaura;
import info.sigmaclient.sigma.utils.TimerUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CKeepAlivePacket;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoof extends Module {
    private CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList<>();
    private TimerUtil timer = new TimerUtil();
    private NumberValue delay = new NumberValue("Delay",50,10,2000, NumberValue.NUMBER_TYPE.INT);


    public PingSpoof() {
        super("PingSpoof", Category.Misc, "Fake Ping Spoof Server");
        registerValue(delay);
    }

    @EventTarget
    public void onPacket(PacketEvent event){
        if(event.isRevive())return;
        IPacket packet = event.packet;
        if(packet instanceof CConfirmTransactionPacket || packet instanceof CKeepAlivePacket){
            packets.add(packet);
            event.setCancelable(true);
        }
        if(timer.hasTimeElapsed(delay.getValue().longValue()) || Killaura.attackTarget != null || mc.currentScreen != null){
            timer.reset();
            if(!packets.isEmpty()) {
                packets.forEach(Objects.requireNonNull(mc.getConnection())::sendPacketNOEvent);
            }
            packets.clear();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(!packets.isEmpty()) {
            packets.forEach(Objects.requireNonNull(mc.getConnection())::sendPacketNOEvent);
        }
        packets.clear();
        timer.reset();
    }
}
