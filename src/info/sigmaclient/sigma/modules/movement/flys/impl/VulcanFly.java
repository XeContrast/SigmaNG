package info.sigmaclient.sigma.modules.movement.flys.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.Fly;
import info.sigmaclient.sigma.modules.movement.flys.FlyModule;
import info.sigmaclient.sigma.premium.PremiumManager;

import top.fl0wowp4rty.phantomshield.annotations.Native;

@Native
public class VulcanFly extends FlyModule {
    public static PremiumManager.PremiumRun premium1 = null;
    public static PremiumManager.PremiumRun premium2 = null;
    public static PremiumManager.PremiumRun premium3 = null;
    public VulcanFly(Fly fly) {
        super("Vulcan", "Fly for Vulcan", fly);
    }
    int flyTicks = 0;
    double y = 0;
    boolean phase = false;
    @Override
    public void onEnable() {
        flyTicks = 0;
        y = 0;
        phase = false;
        super.onEnable();
    }

     @Override
    public void onDisable() {
        mc.timer.setTimerSpeed(1f);
        super.onDisable();
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPost()) return;
        if(premium1 != null && PremiumManager.isPremium)
            premium1.run(event, parent);
       
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(premium2 != null && PremiumManager.isPremium)
            premium2.run(event, parent);
        
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        if(premium3 != null && PremiumManager.isPremium)
            premium3.run(event, parent);
        
    }
}
