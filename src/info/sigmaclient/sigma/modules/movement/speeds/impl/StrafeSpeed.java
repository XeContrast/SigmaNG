package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class StrafeSpeed extends SpeedModule {
    public StrafeSpeed(Speed speed) {
        super("Strafe", "Strafe speed", speed);
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        MovementUtils.strafing(event, MovementUtils.getSpeed());
        
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPost()) return;
        if(MovementUtils.isMoving()) {
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
       
    }
}
