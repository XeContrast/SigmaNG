package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.util.ResourceLocation;


public class TestCape extends Module {
    public TestCape() {
        super("TestCape", Category.Misc, "TestCape.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        mc.player.setElytraOfCape(false);
//        mc.player.isWearing();
        mc.player.setLocationOfCape(new ResourceLocation("sigmang/images/jelloblur.png"));
       
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }
}
