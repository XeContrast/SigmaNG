package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.premium.PremiumManager;


public class GrimSpeed extends SpeedModule {
    public static Runnable premium = null;
    public GrimSpeed(Speed speed) {
        super("Grim", "Grim ac speed", speed);
    }

    @Override
    public void onDisable() {
//        mc.gameSettings.keyBindJump.pressed = false;
        super.onDisable();
    }

    @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPost()) return;
//        mc.gameSettings.keyBindJump.pressed = true;
        if(premium != null && PremiumManager.isPremium)
            premium.run();
       
    }
}
