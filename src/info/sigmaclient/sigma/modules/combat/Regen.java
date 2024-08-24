package info.sigmaclient.sigma.modules.combat;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.play.client.CPlayerPacket;


public class Regen extends Module {
    public NumberValue amount = new NumberValue("Amount", 5, 0, 20, NumberValue.NUMBER_TYPE.FLOAT);
    public Regen() {
        super("Regen", Category.Combat, "Fast health (1.8 servers only)");
     registerValue(amount);
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()) {
            for (int i = 0; i < amount.getValue().longValue(); i++) {
                mc.getConnection().sendPacketNOEvent(new CPlayerPacket(mc.player.onGround));
            }
        }
       
    }
}
