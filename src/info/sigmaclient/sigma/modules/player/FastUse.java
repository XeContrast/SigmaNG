package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.Hand;


public class FastUse extends Module {
    public ModeValue type = new ModeValue("Type", "Vanilla", new String[]{"Vanilla", "Timer"});
    public NumberValue timer = new NumberValue("Timer", 1.2, 1, 2, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !type.is("Timer");
        }
    };
    public FastUse() {
        super("FastUse", Category.Player, "Fast eat food.");
     registerValue(type);
     registerValue(timer);
    }

     @Override
    public void onDisable() {
        mc.timer.setTimerSpeed(1f);
        super.onDisable();
    }
    boolean use = false;
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            if(!mc.player.isHandActive() || !(mc.player.getHeldItem(Hand.MAIN_HAND).isFood())) {
                if(use){
                    use = false;
                    mc.timer.setTimerSpeed(1f);
                }
                return;
            }
            switch (type.getValue()){
                case "Vanilla":
                    for(int i = 0;i < 40;i++){
                        mc.getConnection().sendPacket(new CPlayerPacket(mc.player.onGround));
                    }
                    mc.playerController.onStoppedUsingItem(mc.player);
                    break;
                case "timer":
                    mc.timer.setTimerSpeed(timer.getValue().floatValue());
                    use = true;
                    break;
            }
        }
       
    }
}
