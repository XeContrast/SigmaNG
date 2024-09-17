package info.sigmaclient.sigma.modules.combat;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.item.BowItem;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.Hand;

import java.util.Objects;


public class FastBow extends Module {
    public ModeValue type = new ModeValue("Type", "Vanilla", new String[]{"Vanilla", "Timer"});
    public NumberValue timer = new NumberValue("Timer", 1.2, 1, 10, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !type.is("Timer");
        }
    };
    public FastBow() {
        super("FastBow", Category.Combat, "Fast shot bow.");
     registerValue(type);
     registerValue(timer);
    }
    int T = 0;
     @Override
    public void onDisable() {
        mc.timer.setTimerSpeed(1f);
        super.onDisable();
    }
    boolean use = false;
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }
    public boolean shouldTime(){
        return mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem;
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            if(!mc.player.isHandActive() || !shouldTime()) {
                if(use){
                    use = false;
                    mc.timer.setTimerSpeed(1f);
                }
                return;
            }
            if(T > 0){
                T --;
                if(T == 0){
                    mc.playerController.onStoppedUsingItem(mc.player);
                }
            }
            switch (type.getValue()){
                case "Vanilla":
                    for(int i = 0;i < 40;i++){
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerPacket(mc.player.onGround));
                    }
                    mc.playerController.onStoppedUsingItem(mc.player);
                    break;
                case "Timer":
                    mc.timer.setTimerSpeed(timer.getValue().floatValue());
                    use = true;
                    if(mc.player.getItemInUseCount() >= 72000){
                        mc.playerController.onStoppedUsingItem(mc.player);
                    }
                    break;
            }
        }

    }
}
