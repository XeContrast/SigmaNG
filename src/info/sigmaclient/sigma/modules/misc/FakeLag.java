package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.combat.Killaura;
import info.sigmaclient.sigma.utils.TimerUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.math.RayTraceResult;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class FakeLag extends Module {
    private final CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList<>();
    private final TimerUtil timer = new TimerUtil();

    private final NumberValue delay = new NumberValue("Delay",10,10,100, NumberValue.NUMBER_TYPE.INT);
    private final BooleanValue move = new BooleanValue("onlyMove",true);
    private final BooleanValue combat = new BooleanValue("NoCombat",true);
    public FakeLag() {
        super("FakeLag", Category.Misc,"FakeLag");
        registerValue(delay);
        registerValue(move);
        registerValue(combat);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        packets.clear();
        timer.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        release();
    }

    @EventTarget
    public void onPacket(PacketEvent event){
        if (!event.isSend() || event.cancelable)return;
        if(Objects.requireNonNull(mc.objectMouseOver).getType() != RayTraceResult.Type.ENTITY && Killaura.attackTarget == null){
            release();
            timer.reset();
            return;
        }
        if(timer.hasTimeElapsed(delay.getValue().longValue(),true)){
            release();
        }
        IPacket packet = event.packet;
        if(packet instanceof CUseEntityPacket){
            if(combat.isEnable() && (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY || Killaura.attackTarget != null)) {
                release();
                return;
            }
        }
        if(packet instanceof CPlayerPacket){
            if(!packets.contains(packet)) {
                packets.add(packet);
                event.setCancelled();
            }
        }else if(!move.isEnable()){
            if(!packets.contains(packet)) {
                packets.add(packet);
                event.setCancelled();
            }
        }
    }

    public void release(){
        if(!packets.isEmpty()) {
            packets.forEach(Objects.requireNonNull(mc.getConnection())::sendPacketNOEvent);
            packets.clear();
        }
    }
}
