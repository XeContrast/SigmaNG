package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.StrafeEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;


public class HypixelSpeed extends SpeedModule {
    boolean wasSlow = false;
    double stair;
    float speed;
    public double fakeMotionY, fakeY;
    private int offGroundTick;
    public HypixelSpeed(Speed speed) {
        super("Hypixel", "Speed for Hypixel", speed);
    }

    @Override
    public void onEnable() {
        wasSlow = false;
        offGroundTick = 0;

        stair = MovementUtils.getBaseMoveSpeed();
        fakeMotionY = 0;
        fakeY = mc.player.getPositionVector().y;
        super.onEnable();
    }

    @EventTarget
    public void onStrafeEvent(StrafeEvent event) {

        if(this.parent.hypixelMode.is("Bhop")){
            if (MovementUtils.isMoving() && mc.player.onGround) {
                mc.player.getMotion().y = MovementUtils.getJumpBoostModifier(0.41999998688698F);
                MovementUtils.strafing((float) Math.max(MovementUtils.getBaseMoveSpeed(), 0.475f + 0.04F * MovementUtils.getSpeedEffect()));
            }

            if (mc.player.onGround) {
                speed = 1F;

            }

            final int[] allowedAirTicks = new int[]{10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25, 26, 28, 29};

            if (!(mc.world.getBlockState(mc.player.getPosition().add(0, -0.25, 0)).getBlock() instanceof AirBlock)) {
                for (final int allowedAirTick : allowedAirTicks) {
                    if (offGroundTick == allowedAirTick && allowedAirTick <= 9 + this.parent.gliding.getValue().intValue() && mc.player.hurtTime == 0) {
                        mc.player.getMotion().y = 0;
                        MovementUtils.strafing(MovementUtils.getBaseMoveSpeed() * speed);
                        speed *= 0.98F;
                    }
                }
            }

        }

    }

    @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if (event.isPost()) return;
        if(mc.player.onGround){
            offGroundTick = 0;
        }else {
            offGroundTick++;
        }
        if (this.parent.low.getValue() && mc.player.hurtTime == 0) {
            ArrayList<Double> values_9tick = new ArrayList<>(Arrays.asList(
                    0.33310120140062277, 0.24796918219826297, 0.14960980209333172,
                    0.05321760771444281, -0.02624674495067964, -0.3191218156544406,
                    -0.3161693874618279, -0.3882460072689227, -0.4588810960546281));
            ArrayList<Double> values_7tick = new ArrayList<>(Arrays.asList(
                    0.4200000059604645, 0.3380001194477087, 0.25302801701974894,
                    0.18916746036107118, -0.079601588676394617, -0.31289557200443413,
                    -0.3862369221364473, -0.45299219251018796));

            switch (this.parent.lowMode.getValue()) {
                case "MoreTick":
                    ChatUtils.sendMessageWithPrefix("more");
                    if (offGroundTick < values_9tick.size() - 1 && offGroundTick > 1) {
                        mc.player.getMotion().y = values_9tick.get(offGroundTick - 1);
                    }
                    break;
                case "LessTick":
                    ChatUtils.sendMessageWithPrefix("less");
                    if(mc.player.onGround || mc.player.fallDistance <= 1) {
                        if (MovementUtils.isMoving() || offGroundTick > 0) {
                            mc.player.getMotion().y = values_7tick.get(offGroundTick);
                        }
                    }
                    break;
            }

        }
        switch (this.parent.hypixelMode.getValue().toLowerCase()) {
            case "semistrafe":
                if (mc.player.collidedVertically && MovementUtils.isMoving()) {
                    BlockPos blockPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
                    if (mc.player.onGround && MovementUtils.isMoving() && !(mc.world.getBlockState(blockPos).getBlock() instanceof StairsBlock)) {
                        mc.player.getMotion().y = MovementUtils.getJumpBoostModifier(0.41999998688698F);
                        MovementUtils.strafing((float) Math.max(MovementUtils.getBaseMoveSpeed(), 0.475f + 0.04F * MovementUtils.getSpeedEffect()));
                    }
                }
                if (!mc.player.onGround) {
                    if (MovementUtils.getSpeed() < 0.11f) {
                        MovementUtils.strafing(0.11);
                    }
                }
                if (!MovementUtils.isMoving()) {
                    mc.player.getMotion().x = mc.player.getMotion().z = 0;
                }
            case "ground":
                if (mc.player.collidedVertically && MovementUtils.isMoving()) {
                    BlockPos blockPos = new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
                    if (mc.player.onGround && MovementUtils.isMoving() && !(mc.world.getBlockState(blockPos).getBlock() instanceof StairsBlock)) {
                        mc.player.getMotion().y = MovementUtils.getJumpBoostModifier(0.41999998688698F);
                        MovementUtils.strafing((float) Math.max(MovementUtils.getBaseMoveSpeed(), 0.475f + 0.04F * MovementUtils.getSpeedEffect()));
                    }
                }
                if (!MovementUtils.isMoving()) {
                    mc.player.getMotion().x = mc.player.getMotion().z = 0;
                }
                break;
            case "fakestrafe":
                if (!mc.player.isInWater()) {
                    fakeY += fakeMotionY;
                    mc.player.getPositionVec().y = fakeY;

                    if (fakeMotionY > 0) {
                        fakeMotionY *= 0.8;
                        fakeMotionY -= 0.05;
                    } else {
                        fakeMotionY -= 0.07;
                    }

                    if (fakeY < mc.player.getBoundingBox().minY) {
                        fakeY = mc.player.getBoundingBox().minY;
                        mc.player.getPositionVec().y = fakeY;
                    }
                    if (fakeY <= mc.player.getBoundingBox().minY) {
                        if (MovementUtils.isMoving())
                            fakeMotionY = 0.42;
                        else fakeMotionY = 0;
                    }
                }
                break;
            case "real":
                if (!mc.player.isInWater()) {
                    if (mc.player.onGround && MovementUtils.isMoving()) {
                        mc.player.getMotion().y = 0.41999998688697815;
                        stair = MovementUtils.getBaseMoveSpeed() * 1.4D;
                        wasSlow = true;
                    }
                }
                break;
            case "eternityf":
                if (MovementUtils.isMoving()) {
                    if (mc.player.isOnGround()) {
                        mc.player.jump();
                        MovementUtils.strafing(0.485);
                    }
                    if (mc.player.getMotion().y < 0.1 && mc.player.getMotion().y > 0.01) {
                        mc.player.setMotion(mc.player.getMotion().mul(1.005, 1, 1.005));
                    }
                    if (mc.player.getMotion().y < 0.005 && mc.player.getMotion().y > 0) {
                        mc.player.setMotion(mc.player.getMotion().mul(1.005, 1, 1.005));
                    }
                    if (mc.player.getMotion().y < 0.01 && mc.player.getMotion().y > -0.03) {
                        mc.player.setMotion(mc.player.getMotion().mul(1.002, 1, 1.002));
                    }
                }
                if (InputMappings.isKeyDown(32) && InputMappings.isKeyDown(30)) {
                    if (mc.player.getMotion().y < -0.05 && mc.player.getMotion().y > -0.08 && mc.player.hurtTime <= 1) {
                        MovementUtils.strafing(0.15);
                    } else if (mc.player.getMotion().y < -0.05 && mc.player.getMotion().y > -0.08) {
                        MovementUtils.strafing(0.15);
                    }
                }
                if (mc.player.hurtTime > 6) {
                    mc.player.setMotion(mc.player.getMotion().mul(1.007, 1, 1.007));
                }
                break;
        }
    }



    @EventTarget
    public void onMoveEvent(MoveEvent event){
        switch (this.parent.hypixelMode.getValue().toLowerCase()) {
            case "fakestrafe":
                if (!mc.player.isInWater()) {
                    if (mc.player.onGround && MovementUtils.isMoving()) {
                        MovementUtils.strafing(event, MovementUtils.getBaseMoveSpeed() * 1.0f);
                    }
                }
                break;
            case "real":
                if (!mc.player.isInWater()) {
                    if (wasSlow && MovementUtils.isMoving()) {
                        stair *= 0.8;
                        stair = Math.max(MovementUtils.getBaseMoveSpeed(), stair);
                        wasSlow = false;
                        MovementUtils.strafing(event, stair);
                    }
                    if (mc.player.onGround && MovementUtils.isMoving()) {
                        MovementUtils.strafing(event, stair);
                    }
                    if(mc.player.getMotion().y < -0.05 && mc.player.getMotion().y > -0.08 && mc.player.movementInput.moveStrafe != 0){
                        MovementUtils.strafing(event, stair * 0.5);
                    }
                    if (stair > MovementUtils.getBaseMoveSpeed() && !wasSlow)
                        stair -= stair / 156;

                    stair = Math.max(MovementUtils.getBaseMoveSpeed() * 1.2f, stair);

                    if (!MovementUtils.isMoving()) {
                        MovementUtils.strafing(event, 0);
                        wasSlow = false;
                    }
                }
                break;
        }
    }
}
