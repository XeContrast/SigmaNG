package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.config.values.ModeValue;
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
//    public ModeValue type = new ModeValue("Styles", "Next", new String[]{"Next","classic"});

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        super.onDisable();
        mc.player.setLocationOfCape(null);
    }

    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        mc.player.setElytraOfCape(false);
        //switch (type.getValue()) {
            //case "Next":
                //        mc.player.isWearing();
                mc.player.setLocationOfCape(new ResourceLocation("sigmang/images/capes/Cape2.png"));
            //case "classic":
              //  mc.player.setLocationOfCape(new ResourceLocation("sigmang/images/capes/MojangN.png"));
       // }
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }
}
