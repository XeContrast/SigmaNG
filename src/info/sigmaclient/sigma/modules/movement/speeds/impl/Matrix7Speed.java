package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.client.settings.KeyBinding;

public class Matrix7Speed extends SpeedModule {
    public Matrix7Speed(Speed speed) {
        super("Matrix7", "Speed for Matrix7", speed);
    }

    /**
     * @author Xebook1
     */

    @EventTarget
    public void onUpdateEvent(UpdateEvent event) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.keyCode,false);
        mc.player.setMotion(mc.player.getMotion().getX(), mc.player.getMotion().y -= 0.00348, mc.player.getMotion().getZ());
        mc.player.jumpMovementFactor = 0.026f;
        if (mc.player.onGround) {
            if (MovementUtils.isMoving()) {
                mc.player.jump();
                mc.timer.setTimerSpeed(1.35f);
            }
            MovementUtils.strafing();
        } else {
            if (mc.timer.getTimerSpeed() != 1f) {
                mc.timer.setTimerSpeed(1f);
            }
            if (MovementUtils.getSpeed() < 0.19f) {
                MovementUtils.strafing();
            }
        }
    }
}
