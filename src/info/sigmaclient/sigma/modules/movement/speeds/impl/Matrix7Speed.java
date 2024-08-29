package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;

public class Matrix7Speed extends SpeedModule {
    public Matrix7Speed(Speed speed) {
        super("Matrix7", "Speed for Matrix7", speed);
    }

    /**
     * @author Xebook1
     */

    @EventTarget
    public void onUpdateEvent(MotionEvent event) {
        if (event.isPre()) {
            mc.player.getMotion().y -= 0.00348;
            mc.player.jumpMovementFactor = 0.026f;
            if (mc.player.onGround) {
                if (MovementUtils.isMoving()) {
                    mc.player.jump();
                    MovementUtils.strafing();
                }
            } else {
                if (MovementUtils.getSpeed() < 0.19f) {
                    MovementUtils.strafing();
                }
            }
        }
    }
}
