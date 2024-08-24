package info.sigmaclient.sigma.utils.player;

import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;

import static info.sigmaclient.sigma.modules.Module.mc;

public class MovementUtils {
    public static boolean notJump = false;
    private static double speed;

    public static boolean isMoving() {
        if (mc.player == null) {
            return false;
        }
        return (mc.player.movementInput.moveForward != 0F || mc.player.movementInput.moveStrafe != 0F);
    }
    public static double getSpeed() {
        return MathHelper.sqrt(mc.player.getMotion().x * mc.player.getMotion().x + mc.player.getMotion().z * mc.player.getMotion().z);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player.isPotionActive(Effects.SPEED)) {
            baseSpeed *= 1.0 + 0.2 * (mc.player.getActivePotionEffect(Effects.SPEED).getAmplifier() + 1);
        }
        return baseSpeed;
    }




    public static void strafing(double moveSpeed) {
        strafing(moveSpeed,mc.player);
    }
    public static void strafing(MoveEvent event, double moveSpeed) {
        strafing(event, moveSpeed, mc.player);
    }
    public static void strafing() {
        if (!isMoving()) {
            return;
        }

        final double yaw = direction();
        mc.player.getMotion().x = -MathHelper.sin((float) yaw) * getSpeed();
        mc.player.getMotion().z = MathHelper.cos((float) yaw) * getSpeed();
    }


    public static double directionNoRadians(){
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.movementInput.moveForward < 0f) rotationYaw += 180f;
        float forward = 1f;
        if (mc.player.movementInput.moveForward < 0f)
            forward = -0.5f;
        else if (mc.player.movementInput.moveForward > 0f) forward = 0.5f;
        if (mc.player.movementInput.moveStrafe > 0f) rotationYaw -= 90f * forward;
        if (mc.player.movementInput.moveStrafe < 0f) rotationYaw += 90f * forward;
        return rotationYaw;
    }

    public static int getSpeedEffect() {
        return mc.player.isPotionActive(Effects.SPEED) ? mc.player.getActivePotionEffect(Effects.SPEED).getAmplifier() + 1 : 0;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        return getJumpBoostModifier(baseJumpHeight, true);
    }

    public static double getJumpBoostModifier(double baseJumpHeight, boolean potionJump) {
        if (mc.player.isPotionActive(Effects.JUMP_BOOST) && potionJump) {
            int amplifier = mc.player.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier();
            baseJumpHeight += ((float) (amplifier + 1) * 0.1f);
        }

        return baseJumpHeight;
    }

    public static double direction(){
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.movementInput.moveForward < 0f) rotationYaw += 180f;
        float forward = 1f;
        if (mc.player.movementInput.moveForward < 0f)
            forward = -0.5f;
        else if (mc.player.movementInput.moveForward > 0f) forward = 0.5f;
        if (mc.player.movementInput.moveStrafe > 0f) rotationYaw -= 90f * forward;
        if (mc.player.movementInput.moveStrafe < 0f) rotationYaw += 90f * forward;
        return Math.toRadians(rotationYaw);
    }
    public static double direction(float rotationYaw){
        if (mc.player.movementInput.moveForward < 0f) rotationYaw += 180f;
        float forward = 1f;
        if (mc.player.movementInput.moveForward < 0f)
            forward = -0.5f;
        else if (mc.player.movementInput.moveForward > 0f) forward = 0.5f;
        if (mc.player.movementInput.moveStrafe > 0f) rotationYaw -= 90f * forward;
        if (mc.player.movementInput.moveStrafe < 0f) rotationYaw += 90f * forward;
        return Math.toRadians(rotationYaw);
    }

    public static void strafing_yaw(final double yaw,final double speed) {
        if (!isMoving()) {
            return;
        }

        mc.player.getMotion().x = -MathHelper.sin((float) direction((float) yaw)) * speed;
        mc.player.getMotion().z = MathHelper.cos((float) direction((float) yaw)) * speed;
    }

    public static void strafing(final double speed, Entity entity) {
        if (!isMoving()) {
            return;
        }

        final double yaw = direction();
        entity.getMotion().x = -MathHelper.sin((float) yaw) * speed;
        entity.getMotion().z = MathHelper.cos((float) yaw) * speed;
    }

    public static void strafing(final MoveEvent moveEvent,final double speed, Entity entity) {
        if (!isMoving()) {
            return;
        }

        final double yaw = direction();
        entity.getMotion().x = -MathHelper.sin((float) yaw) * speed;
        entity.getMotion().z = MathHelper.cos((float) yaw) * speed;
        moveEvent.setX(-MathHelper.sin((float) yaw) * speed);
        moveEvent.setZ(MathHelper.cos((float) yaw) * speed);
    }

    public static void strafing2(double moveSpeed, float yaw, MoveEvent moveEvent) {
        float n3 = yaw + 90.0f;
        float n = 1;
        float n2 = 0;
        float[] 蓳岋㨳Ꮺꦱ = new float[] { n3, n, n2 };
        final float n22 = 蓳岋㨳Ꮺꦱ[1];
        final float n32 = 蓳岋㨳Ꮺꦱ[2];
        final float n42 = 蓳岋㨳Ꮺꦱ[0];
        if (n22 == 0.0f) {
            if (n32 == 0.0f) {
                moveEvent.setX(0);
                moveEvent.setZ(0);
            }
        }
        final double cos = Math.cos(Math.toRadians(n42));
        final double sin = Math.sin(Math.toRadians(n42));
        final double 頉ꁈၝ嘖藸竁 = (n22 * cos + n32 * sin) * moveSpeed;
        final double 嶗䈔Ⱋ玑Ꮀ牰 = (n22 * sin - n32 * cos) * moveSpeed;
        moveEvent.setX(頉ꁈၝ嘖藸竁);
        moveEvent.setZ(嶗䈔Ⱋ玑Ꮀ牰);
    }

    //        if (forward != 0.0D) {
//            if (strafe > 0.0D) {
//                yaw += ((forward >= 0.0D) ? -45 : 45);
//            } else if (strafe < 0.0D) {
//                yaw += ((forward >= 0.0D) ? 45 : -45);
//            }
//            strafe = 0.0D;
//            if (forward > 0.0D) {
//                forward = 1.0D;
//            } else if (forward < 0.0D) {
//                forward = -1.0D;
//            }
//        }
//        if (strafe > 0.0D) {
//            strafe = 1.0D;
//        } else if (strafe < 0.0D) {
//            strafe = -1.0D;
//        }
//        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
//        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
//        mc.player.getMotion().x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
//        mc.player.getMotion().z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    static boolean doMotion = false;
    static double nextX = 0;
    static double nextZ = 0;
    public static void clacMotion(MoveEvent moveEvent){
        if(doMotion) {
            moveEvent.setX(nextX);
            moveEvent.setZ(nextZ);
            doMotion = false;
        }
    }



    public static void setSpeed(double speed) {
        MovementUtils.speed = speed;
    }
}
