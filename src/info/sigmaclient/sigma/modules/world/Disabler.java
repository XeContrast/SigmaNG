package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.other.WorldChangeEvent;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.movement.Fly;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.TimerUtil;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Hand;

import java.util.Random;


public class Disabler extends Module {
    public static ModeValue mode = new ModeValue("Type","Verus",
            new String[]{
                    "Verus", // verus sprint...
                    "Karhu",
                    "ColdPVP",
                    "MineMalia",
                    "WatchDog"
            });
    public Disabler() {
        super("Disabler", Category.World, "disable some anti-cheats");
     registerValue(mode);
    }
    public TimerUtil timer = new TimerUtil();
    public int testticks = 0;
    private boolean disabled = false, jumps = false;
    public int offGroundTicks = 0;
    @Override
    public void onEnable() {
        super.onEnable();
        disabled = false;
        jumps = false;
        offGroundTicks = 0;
        testticks = 0;
        timer.reset();
    }

    @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(mc.player == null || mc.world == null)return;
        this.suffix = mode.getValue();
        switch (mode.getValue()) {
            case "Karhu":
                if (event.isPre() && mc.timer.getTimerSpeed() > 1.0F) {
                    mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(
                            Hand.MAIN_HAND
                    ));
                }
                break;
            case "MineMalia":
//                if (event.isPre() && mc.player.ticksExisted % 5 == 0 && mc.player.onGround && (SigmaNG.getSigmaNG().moduleManager.getModule(Speed.class).enabled || SigmaNG.getSigmaNG().moduleManager.getModule(Fly.class).enabled)) {
//                    double doubleValue;
//                    doubleValue = -0.5;
//                    final double posX = mc.player.getPosX();
//                    final double posY = mc.player.getPosY();
//                    final CPlayerPacket.PositionPacket position = new CPlayerPacket.PositionPacket(posX, posY + doubleValue, mc.player.getPosZ(), mc.player.onGround);
////                    packetPlayerList.add((CPlayerPacket)position);
//                    mc.getConnection().sendPacket((IPacket<?>)position);
//                }
                break;
            case "ColdPVP":
                if (event.isPre() && mc.player.ticksExisted % 10 == 0 && (SigmaNG.getSigmaNG().moduleManager.getModule(Speed.class).enabled || SigmaNG.getSigmaNG().moduleManager.getModule(Fly.class).enabled)) {
                    double doubleValue;
                    doubleValue = -new Random().nextInt(10)-1;
                    final double posX = mc.player.getPosX();
                    final double posY = mc.player.getPosY();
                    final CPlayerPacket.PositionPacket position = new CPlayerPacket.PositionPacket(posX, posY + doubleValue, mc.player.getPosZ(), mc.player.onGround);
//                    packetPlayerList.add((CPlayerPacket)position);
                    mc.getConnection().sendPacket(position);
                }
                break;
            case "Intave":
                break;
            case "WatchDog":
                if(!event.isPost()){
                    if (mc.player.onGround) {
                        this.offGroundTicks = 0;
                    }
                    else {
                        this.offGroundTicks++;
                    }
                    if (mc.player.onGround && jumps){
                        ChatUtils.sendMessageWithPrefix("Start Disable Motion Checker");
                        this.jumps = false;
                        mc.player.jump();
                        this.disabled = true;
                    }else if(disabled && offGroundTicks >= 10){
                        if (this.offGroundTicks % 2 == 0) {
                            mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX() + 0.095, mc.player.getPosY(), mc.player.getPosZ(), mc.player.onGround));
                        }else {
                            mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.onGround));
                        }
                    }
                }

                break;
        }
       
    }
    public float fixedFacing(float face){
        return face > 0 ? 0.5f : (face < 0 ? -0.5f : 0);
    }
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(mc.player == null || mc.world == null)return;
        switch (mode.getValue()) {
            case "MineMalia":
                if (event.packet instanceof SPlayerPositionLookPacket packetIn && mc.currentScreen == null && (SigmaNG.getSigmaNG().moduleManager.getModule(Speed.class).enabled || SigmaNG.getSigmaNG().moduleManager.getModule(Fly.class).enabled)) {
                    ClientPlayerEntity playerentity = mc.player;
                    mc.getConnection().sendPacket(new CConfirmTeleportPacket(packetIn.getTeleportId()));
                    mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), playerentity.rotationYaw, playerentity.rotationPitch, false));

                    event.cancelable = true;
                }
                break;
            case "Karhu" :
            case "Verus":
                if (event.isSend()) {
                    if (event.packet instanceof CEntityActionPacket) {
                        if (((CEntityActionPacket) event.packet).getAction() == CEntityActionPacket.Action.START_SPRINTING) {
                            event.cancelable = true;
                        }
                        if (((CEntityActionPacket) event.packet).getAction() == CEntityActionPacket.Action.STOP_SPRINTING) {
                            event.cancelable = true;
                        }
                    }
                }
                break;
            case "WatchDog":
                if(disabled && event.packet instanceof SPlayerPositionLookPacket){
                    this.testticks = this.testticks + 1;
                    if (this.testticks == 25) {
                        this.disabled = false;
                        this.testticks = 0;
                        ChatUtils.sendMessageWithPrefix("Successful!");
                    }

                }

                break;
        }
        
    }
    @EventTarget
    public void onMove(MoveEvent event){
        if(mc.player == null || mc.world == null)return;
        if (mode.getValue().equals("WatchDog")) {
            if (disabled && offGroundTicks > 10) {
                event.setX(0);
                event.setY(0);
                event.setZ(0);
            }
        }
    }
    @EventTarget
    public void onWorld(WorldChangeEvent event){
        ChatUtils.sendMessageWithPrefix("World Change Disabler");
        this.disabled = false;
        this.jumps = true;

        timer.reset();
    }
}
