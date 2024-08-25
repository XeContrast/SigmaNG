package info.sigmaclient.sigma.modules.combat;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.AttackEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.*;

import java.util.ArrayList;
import java.util.Objects;


public class AntiKnockBack extends Module {
    public ModeValue mode = new ModeValue("Mode", "Jump", new String[]{
            "Intave13",
            "Hype",
            "Motion",
            "Packet",
            "Vulcan",
            "Polar",
            "Hypixel",
            "Hypixel2",
            "Hypixel3",
            "Hypixel4",
            "Remiaft",
            "Delayed",
            "Matrix",
            "GrimAC",
            "Verus",
            "Jump"
    });
    public NumberValue motionX = new NumberValue("Horizontal", 0, 0, 1, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !(mode.is("Packet") || mode.is("Motion"));
        }
    };
    public NumberValue motionY = new NumberValue("Vertical", 0, 0, 1, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !(mode.is("Packet") || mode.is("Motion"));
        }
    };
    public NumberValue ticks = new NumberValue("DelayedTicks", 0, 0, 1, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !mode.is("Delayed");
        }
    };
    public BooleanValue hitBugs = new BooleanValue("HitBugs", false){
        @Override
        public boolean isHidden() {
            return true;
        }
    };
    int delayed = 0, grimPackets = 0;
    ArrayList<IPacket<?>> veloPacket = new ArrayList<>();
    public AntiKnockBack() {
        super("AntiKnockBack", Category.Combat, "Anti knockback");
     registerValue(mode);
     registerValue(motionX);
     registerValue(motionY);
     registerValue(ticks);
     registerValue(hitBugs);
    }

    @EventTarget
    public void onAttackEvent(AttackEvent event) {
        if (mode.is("Hype")) {
            if (mc.player.isSwingInProgress && mc.player.hurtTime > 6) {
                mc.player.setMotion(mc.player.getMotion().x *= 0.6, mc.player.getMotion().getY(), mc.player.getMotion().z *= 0.6);
            }
        }
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        suffix = mode.getValue();
        if(event.isPre()){
            switch (mode.getValue()){
                case "Polar":
                    if(mc.player.hurtTime >= 1 && mc.player.hurtTime < 6) {
                        double multi = 1.2224324, min = 0.1, max = 0.5;
                        if ((Math.abs(mc.player.getMotion().x) > min && Math.abs(mc.player.getMotion().z) > min) && (Math.abs(mc.player.getMotion().x) < max && Math.abs(mc.player.getMotion().z) < max)) {
                            mc.player.getMotion().x /= multi;
                            mc.player.getMotion().z /= multi;
                        }
                    }
                    break;
                case "Intave13":
                    if (mc.player.hurtTime > 7) {
                        mc.player.getMotion().x = 0;
                        mc.player.getMotion().y = 0;
                        mc.player.getMotion().z = 0;
                    }
                    break;
                case "Jump":
                    if(mc.currentScreen == null) {
                        if (mc.player.hurtTime >= 8) {
                            mc.gameSettings.keyBindJump.pressed = mc.player.isSprinting();
                            if(mc.player.hurtTime == 8){
                                mc.gameSettings.keyBindJump.pressed = false;
                            }
                        }
                    }
                    break;
                case "Motion":
                    if(mc.player.hurtTime == 8){
                        mc.player.getMotion().x *= motionX.getValue().floatValue();
                        mc.player.getMotion().y *= motionY.getValue().floatValue();
                        mc.player.getMotion().z *= motionX.getValue().floatValue();
                    }
                    break;
                case "Vulcan":
                    if(mc.player.hurtTime == 6){
                        mc.player.getMotion().x *= 0;
                        mc.player.getMotion().y *= 0;
                        mc.player.getMotion().z *= 0;
                    }
                    break;
                case "Remiaft":
                    // OMG
                    if(mc.player.hurtTime >= 1 && mc.player.hurtTime < 6) {
                        // OMG BEST CHECK
                        double multi = 1.3224324, min = 0.0000001, max = 0.2;
                        if ((Math.abs(mc.player.getMotion().x) > min && Math.abs(mc.player.getMotion().z) > min) && (Math.abs(mc.player.getMotion().x) < max && Math.abs(mc.player.getMotion().z) < max)) {
                            mc.player.getMotion().x /= multi;
                            mc.player.getMotion().z /= multi;
                        }
                    }
                    break;
                case "Delayed":
                    if(delayed != 0){
                        delayed --;
                        if(delayed == 0) {
                            veloPacket.forEach(p -> ((IPacket<INetHandler>) p).processPacket(Objects.requireNonNull(mc.getConnection()).getNetworkManager().getNetHandler()));
                            veloPacket.clear();
                        }
                    }
                    break;
            }
        }
       
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if (mode.getValue().equals("Intave13")) {
            if (mc.player.hurtTime > 7) {
                if (event.packet instanceof SPlayerPositionLookPacket packet) {
                    event.cancelable = true;
                    Objects.requireNonNull(mc.getConnection()).sendPacketNOEvent(new CPlayerPacket.RotationPacket(packet.yaw, packet.pitch, packet.getY() % 0.125 == 0));
                }
                if (event.packet instanceof CPlayerPacket.PositionPacket packet) {
                    event.cancelable = true;
                    Objects.requireNonNull(mc.getConnection()).sendPacketNOEvent(new CPlayerPacket(packet.onGround));
                }
            }
        }
        if(event.isRevive()){
            switch (mode.getValue()) {
                case "Packet":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            if(
                                    motionX.getValue().floatValue() == 0
                                && motionY.getValue().floatValue() == 0)
                            {
                                event.cancelable = true;
                            }
                            ((SEntityVelocityPacket) event.packet).motionX *= motionX.getValue().floatValue();
                            ((SEntityVelocityPacket) event.packet).motionY *= motionY.getValue().floatValue();
                            ((SEntityVelocityPacket) event.packet).motionZ *= motionX.getValue().floatValue();
                        }
                    }
                    if(event.packet instanceof SExplosionPacket){
                        if(
                                motionX.getValue().floatValue() == 0
                                        && motionY.getValue().floatValue() == 0)
                        {
                            event.cancelable = true;
                        }
                    }
                    break;
                case "Hype":
                    if (event.packet instanceof SPlayerPositionLookPacket) {
                        return;
                    }
                    break;
                case "Verus":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            event.cancelable = true;
                            mc.player.getMotion().y += 0.0001;
                        }
                    }
                    if(event.packet instanceof SExplosionPacket){
                        if(
                                motionX.getValue().floatValue() == 0
                                        && motionY.getValue().floatValue() == 0)
                        {
                            mc.player.getMotion().y += 0.00001;
                            event.cancelable = true;
                        }
                    }
                    break;
                case "Hypixel2":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            event.cancelable = true;
                            if(!mc.player.onGround)
                                mc.player.getMotion().y = ((SEntityVelocityPacket) event.packet).getMotionY() / 8000.0D;
                        }
                    }
                    break;
                case "Hypixel":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            event.cancelable = true;
                            if(mc.player.onGround)
                                mc.player.getMotion().y = ((SEntityVelocityPacket) event.packet).getMotionY() / 8000.0D;
                        }
                    }
                    break;
                case "Hypixel3":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            ((SEntityVelocityPacket) event.packet).motionX *= 0;
                            ((SEntityVelocityPacket) event.packet).motionZ *= 0;
                        }
                    }
                    break;
                case "Hypixel4":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            event.cancelable = true;
                            if(mc.player.onGround) {
                                mc.player.getMotion().y = 0.00000000000000000041;
                            }else {
                                mc.player.getMotion().y = ((SEntityVelocityPacket) event.packet).getMotionY() / 8000.0D;
                            }
                            mc.player.getMotion().x += ((SEntityVelocityPacket) event.packet).getMotionX() / 8000000.0D;
                            mc.player.getMotion().z += ((SEntityVelocityPacket) event.packet).getMotionZ() / 8000000.0D;
                        }
                    }
                    break;
                case "GrimAC":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            event.cancelable = true;
                            grimPackets = 6;
                        }
                    }
                    if (event.packet instanceof SPlayerAbilitiesPacket) {
                        event.cancelable = true;
                    }
                    if (event.packet instanceof SConfirmTransactionPacket && grimPackets > 0) {
                        event.cancelable = true;
                        grimPackets --;
                    }
                    if (event.packet instanceof SCustomPayloadPlayPacket) {
                        event.cancelable = true;
                    }
                    break;
                case "Matrix":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            ((SEntityVelocityPacket) event.packet).motionX *= -0.1;
                            ((SEntityVelocityPacket) event.packet).motionZ *= -0.1;
                        }
                    }
                    break;
                case "Delayed":
                    if (event.packet instanceof SEntityVelocityPacket) {
                        if (((SEntityVelocityPacket) event.packet).getEntityID() == mc.player.getEntityId()) {
                            delayed = (int) (ticks.getValue().floatValue() * 10);
                            veloPacket.add(event.packet);
                            event.cancelable = true;
                        }
                    }
                    if(event.packet instanceof SKeepAlivePacket || event.packet instanceof SConfirmTransactionPacket){
                        if(delayed > 0) {
                            veloPacket.add(event.packet);
                            event.cancelable = true;
                        }
                    }
                    break;
            }
        }
        
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
