package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.impl.other.PrintChatMesEvent;
import info.sigmaclient.sigma.event.impl.other.WorldChangeEvent;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.process.impl.player.BlinkProcess;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.TimerUtil;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import info.sigmaclient.sigma.utils.player.PlayerUtil;
import net.minecraft.block.GlassBlock;
import net.minecraft.item.BowItem;
import net.minecraft.network.play.client.CPlayerPacket;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class Phase extends Module {
    public ModeValue type = new ModeValue("Type", "Vanilla", new String[]{"Vanilla", "Clip", "ONCP", "NClip","Blink"});
    public TimerUtil timerUtil = new TimerUtil();
    public Phase() {
        super("Phase", Category.Movement, "Move into a block");
     registerValue(type);
    }
    @Override
    public void onEnable() {
        timerUtil.reset();
        super.onEnable();
    }

//    public static Pair<Direction, Vector3d> 쿨뎫郝곻聛(final double n) {
//        final AxisAlignedBB 䢿鷏낛缰䩜꿩 = mc.player.getBoundingBox();
//        for (final Direction 훔睬蕃㠠쬷柿 : new Direction[] {
//                Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
//        }) {
//            if (mc.world.getCollisionShapes(
//                    mc.player,
//                    䢿鷏낛缰䩜꿩.쥡㮃붛竬鏟䬾(n * 훔睬蕃㠠쬷柿.getXOffset(), 0.0, n * 훔睬蕃㠠쬷柿.getZOffset())
//            ).iterator().hasNext()) {
//                return new Pair<>(
//                        훔睬蕃㠠쬷柿, mc.player.getPositionVec().add(훔睬蕃㠠쬷柿.getXOffset(), 0.0, 훔睬蕃㠠쬷柿.getZOffset())
//                );
//            }
//        }
//        return null;
//    }


    @EventTarget
    public void onWorldChange(WorldChangeEvent event){
        switch (type.getValue()) {
            case "Blink":
                BlinkProcess.stopBlink();
                setEnabled(false);
                break;
        }
    }
    @EventTarget
    public void onMoveEvent(MoveEvent event){
        switch (type.getValue()) {
            case "Blink":
                if(PlayerUtil.blockRelativeToPlayer(0,-1,0) instanceof GlassBlock && mc.player.inventory.getStackInSlot(0).getItem() instanceof BowItem){
                    if(!BlinkProcess.isBlinking()) {
                        BlinkProcess.startBlink();
                    }
                    mc.player.noClip = true;
                }

                if(!(PlayerUtil.block(BlinkProcess.lastPos.x, BlinkProcess.lastPos.y - 1,BlinkProcess.lastPos.z) instanceof GlassBlock) && !(PlayerUtil.blockRelativeToPlayer(0,-1,0) instanceof GlassBlock )){
                    timerUtil.reset();
                }

                if(BlinkProcess.isBlinking() && !(PlayerUtil.block(BlinkProcess.lastPos.x, BlinkProcess.lastPos.y - 1,BlinkProcess.lastPos.z) instanceof GlassBlock)){
                    BlinkProcess.stopBlink();
                    timerUtil.reset();
                }
                if(timerUtil.hasTimeElapsed(10000)){
                    BlinkProcess.release();
                    timerUtil.reset();
                }

                break;
            case "NClip":
                mc.player.noClip = true;
                break;
            case "ONCP":
                if(mc.player.collidedHorizontally) {
                    if(MovementUtils.isMoving()){
                        final double yaw = Math.toRadians(mc.player.rotationYaw);
                        final double x = -Math.sin(yaw) * 1.56250014364675;
                        final double z = Math.cos(yaw) * 1.56250014364675;
                        mc.player.setPosition(mc.player.getPosX() + x, mc.player.getPosY(), mc.player.getPosZ() + z);
                        mc.getConnection().sendPacketNOEvent(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), true));
                    }
//                    final Pair<Direction, Vector3d> 쿨뎫郝곻聛 = 쿨뎫郝곻聛(1.0E-4);
//                    if(쿨뎫郝곻聛 == null) return;
//                    boolean hyp = false;
//                    final double n = hyp ? 1.0E-6 : 0.0625;
//                    if (쿨뎫郝곻聛.getKey().getAxis() != Direction.Axis.X) {
//                        event.setZ(Math.round((쿨뎫郝곻聛.getValue().z + 1.1921022E-8) * 10000.0) / 10000.0 + 쿨뎫郝곻聛.getKey().getZOffset() * n);
//                    } else {
//                        event.setX(Math.round((쿨뎫郝곻聛.getValue().x + 1.1921022E-8) * 10000.0) / 10000.0 + 쿨뎫郝곻聛.getKey().getXOffset() * n);
//                    }
                }
                break;
        }
        
    }


    @Override
    public void onDisable() {
        BlinkProcess.stopBlink();
        super.onDisable();
    }
    @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            switch (type.getValue()){
                case "Clip":
                    if(mc.player.collidedHorizontally) {
                        if(MovementUtils.isMoving()){
                            final double yaw = Math.toRadians(mc.player.rotationYaw);
                            final double x = -Math.sin(yaw) * 0.08;
                            final double z = Math.cos(yaw) * 0.08;
                            mc.player.setPosition(mc.player.getPosX() + x, mc.player.getPosY(), mc.player.getPosZ() + z);
//                            mc.getConnection().sendPacketNOEvent(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), true));
                        }
                    }
                    break;
                case "Vanilla":
                    if(mc.player.collidedHorizontally) {
                        if(MovementUtils.isMoving()){
                            final double yaw = Math.toRadians(mc.player.rotationYaw);
                            final double x = -Math.sin(yaw) * 2;
                            final double z = Math.cos(yaw) * 2;
                            mc.player.setPosition(mc.player.getPosX() + x, mc.player.getPosY(), mc.player.getPosZ() + z);
                            mc.getConnection().sendPacketNOEvent(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), true));
                        }
                    }
                    break;
            }
        }
       
    }
}
