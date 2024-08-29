package info.sigmaclient.sigma.process.impl.player;

import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.event.impl.player.StrafeEvent;
import info.sigmaclient.sigma.process.BProcess;
import info.sigmaclient.sigma.utils.RandomUtil;
import info.sigmaclient.sigma.utils.player.RotationUtils;

public class RotationManager extends BProcess {
    private static float RotYaw = 0;
    private static float RotPitch = 0;
    private static float prevRotYaw = 0;
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

            if(this.SMOOTH_BACK_TICK > 0){
                this.SMOOTH_BACK_TICK --;
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


    public static float getPrevRotYaw() {
        return prevRotYaw;
    }

    public static float getPrevRotPitch() {
        return prevRotPitch;
    }

    public static float getRotYaw() {
        return RotYaw;
    }

    public static float getRotPitch() {
        return RotPitch;
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
