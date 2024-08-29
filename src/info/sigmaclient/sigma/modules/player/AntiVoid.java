package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.player.PlayerUtil;
import net.minecraft.block.AirBlock;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AntiVoid extends Module {
    public ModeValue type = new ModeValue("Type", "Flag", new String[]{"Flag", "Vulcan", "KKCraft", "Polar","Blink"});
    public NumberValue fallDist = new NumberValue("fallDist",1,1,6, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !type.is("Blink");
        }
    };
    public AntiVoid() {
        super("AntiVoid", Category.Player, "Anti fall to void.");
     registerValue(type);
     registerValue(fallDist);
    }
    public List<IPacket> packets = new ArrayList<>();
    public double lastGroundY = 0;
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.isRevive())return;
        if(type.is("Blink")) {
            if (event.getPacket() instanceof CPlayerPacket) {
                if (!PlayerUtil.isBlockUnder()) {
                    if (mc.player.fallDistance < fallDist.getValue().doubleValue()) {
                        event.setCancelled();
                        packets.add(event.getPacket());
                    } else {
                        if (!packets.isEmpty()) {
                            for (IPacket packet : packets) {
                                CPlayerPacket c03 = (CPlayerPacket) packet;
                                c03.y = lastGroundY;
                                mc.getConnection().sendPacketNOEvent(packet);
                            }
                            packets.clear();
                        }
                    }
                } else {
                    lastGroundY = mc.player.getPosY();
                    if (!packets.isEmpty()) {
                        packets.forEach(mc.getConnection()::sendPacketNOEvent);
                        packets.clear();
                    }
                }
            }
        }
    }
    public boolean isOnVoid(){
        double y = mc.player.getPosY();
        while(y > 0){
            y -= 1.0;
            if(!(mc.world.getBlockState(new BlockPos(mc.player.getPosX(), y, mc.player.getPosZ())).getBlock() instanceof AirBlock)) return false;
        }
        return true;
    }
    boolean flag = false;
    int vulcantick = 0;
    @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            if(mc.player.fallDistance >= 2 && isOnVoid()){
                if(!flag) {
                    switch (type.getValue()) {
                        case "KKCraft":
                            mc.player.getMotion().x = 0;
                            mc.player.getMotion().y = 0;
                            mc.player.getMotion().z = 0;
                            event.yaw += new Random().nextInt(100);
                            event.yaw = MathHelper.wrapAngleTo180_float(event.yaw);
                            event.pitch += new Random().nextInt(100);
                            event.pitch = Math.max(Math.min(event.pitch, 90), -90);
                            event.dontRotation = true;
                            if(mc.player.hurtTime > 0){
                                flag = true;
                            }
                            break;
                        case "Flag":
                            mc.player.getMotion().y *= 0.2;
                            flag = true;
                            break;
                        case "Polar":
                            event.cancelable = true;

                            break;
                        case "Vulcan":
                            double y = mc.player.getPosY();
                            y = y - (y % (0.015625));
                            event.y = y;
                            event.onGround = true;
                            vulcantick ++;
                            if(vulcantick > 100){
                                vulcantick = 0;
                                flag = true;
                            }
                            break;
                    }
                }
            }else{
                flag = false;
                vulcantick = 0;
            }
        }
       
    }

    @Override
    public void onEnable() {
        lastGroundY = mc.player.getPosY();
        if (!packets.isEmpty()) {
            packets.forEach(mc.getConnection()::sendPacketNOEvent);
            packets.clear();
        }
        super.onEnable();
    }
}
