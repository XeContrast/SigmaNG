package info.sigmaclient.sigma.process.impl.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.JumpEvent;
import info.sigmaclient.sigma.event.impl.player.MoveInputEvent;
import info.sigmaclient.sigma.event.impl.player.StrafeEvent;
import info.sigmaclient.sigma.process.BProcess;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.util.math.MathHelper;

import static info.sigmaclient.sigma.modules.Module.mc;

public class StrafeFixManager extends BProcess {
    public static boolean StrafeFix = false;
    public static boolean silent = false;

    @EventTarget
    public void onStrafe(StrafeEvent event){
        if(!StrafeFix)return;

        if(RotationManager.getPrevRotPitch() != 0 || RotationManager.getPrevRotYaw() != 0){
            event.yaw = RotationManager.getPrevRotYaw();
        }
    }

    @EventTarget
    public void onJump(JumpEvent event){
        if(!StrafeFix){
            return;
        }
        if(RotationManager.getPrevRotPitch() != 0 || RotationManager.getPrevRotYaw() != 0){
            event.yaw = RotationManager.getPrevRotYaw();
        }
    }

    @EventTarget
    public void onInput(MoveInputEvent event){

        if(!StrafeFix)return;
        if(!silent)return;
        if(RotationManager.getPrevRotPitch() == 0 && RotationManager.getPrevRotYaw() == 0)return;

        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MovementUtils.direction(mc.player.rotationYaw, forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MovementUtils.direction(RotationManager.getPrevRotYaw(), predictedForward, predictedStrafe)));
                final double difference = MathHelper.wrappedDifference(angle, predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }

        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }



}
