package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.RandomUtil;
import net.minecraft.util.math.MathHelper;


public class Derp extends Module {

    public Derp() {
        super("Derp", Category.Player, "Spin bot");
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            event.yaw = MathHelper.wrapAngleTo180_float(mc.player.lastReportedYaw + 80 + RandomUtil.nextFloat(
                    0, 2));
            event.pitch = 87 + RandomUtil.nextFloat(0, 2);
        }
       
    }
}
