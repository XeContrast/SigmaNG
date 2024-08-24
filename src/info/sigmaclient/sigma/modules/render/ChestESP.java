package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ColorValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.event.impl.render.RenderChestEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.sigma5.utils.ShadowESP;

import java.awt.*;


public class ChestESP extends Module {
    public static BooleanValue chest = new BooleanValue("Chest", true);
    public static BooleanValue enderChest = new BooleanValue("EnderChest", false);
    public static BooleanValue bed = new BooleanValue("Bed", false);
    public static ColorValue color = new ColorValue("Color", new Color(0xFF662E).getRGB());
    public ChestESP() {
        super("ChestESP", Category.Render, "ESP for all.");
     registerValue(chest);
     registerValue(enderChest);
     registerValue(bed);
     registerValue(color);
    }
    ShadowESP shadowESP = new ShadowESP();
    @EventTarget
    public void onRenderChestEvent(RenderChestEvent event) {
    }
     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        shadowESP.renderChest(()->{
        });
        
    }
}
