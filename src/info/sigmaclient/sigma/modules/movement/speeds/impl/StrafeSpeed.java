package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;


public class StrafeSpeed extends SpeedModule {
    public StrafeSpeed(Speed speed) {
        super("Strafe", "Strafe speed", speed);
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        MovementUtils.strafing(event, MovementUtils.getSpeed());
        
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPost()) return;
        if(MovementUtils.isMoving()) {
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
       
    }
}
