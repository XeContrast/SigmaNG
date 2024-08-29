package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;


public class LegitSpeed extends SpeedModule {
    public LegitSpeed(Speed speed) {
        super("Legit", "Legit speed", speed);
    }

     @Override
    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = false;
        super.onDisable();
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPost()) return;
        mc.gameSettings.keyBindJump.pressed = MovementUtils.isMoving();
       
    }
}
