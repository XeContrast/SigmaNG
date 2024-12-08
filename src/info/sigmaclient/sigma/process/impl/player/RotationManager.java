package info.sigmaclient.sigma.process.impl.player;

import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.process.BProcess;
import info.sigmaclient.sigma.utils.RandomUtil;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import lombok.Getter;

public class RotationManager extends BProcess {
    @Getter
    private static float RotYaw = 0;
    @Getter
    private static float RotPitch = 0;
    @Getter
    private static float prevRotYaw = 0;
    @Getter
    private static float prevRotPitch = 0;
    public static int SMOOTH_BACK_TICK = 0;

    @EventPriority(20)
    @EventTarget
    public void onMotion(MotionEvent event){
        if(!event.isPre())return;
        prevRotYaw = 0;
        prevRotPitch = 0;
        if(RotPitch == 0 && RotYaw == 0){
            RotYaw = 0;
            RotPitch = 0;

            if(SMOOTH_BACK_TICK > 0){
                SMOOTH_BACK_TICK --;
                event.yaw = RotationUtils.rotateToYaw(20 + RandomUtil.nextFloat(0, 15), mc.player.lastReportedYaw, event.yaw);
                event.pitch = mc.player.lastReportedPitch;
            }
            return;
        }


        event.yaw = RotYaw;
        event.pitch = RotPitch;

        prevRotYaw = RotYaw;
        prevRotPitch = RotPitch;

        RotYaw = 0;
        RotPitch = 0;

    }


    public static void setRotYaw(float rotYaw) {
        RotYaw = rotYaw;
        prevRotYaw = RotYaw;
    }

    public static void setRotPitch(float rotPitch) {
        RotPitch = rotPitch;
        prevRotPitch = RotPitch;
    }


}
