package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.gui.font.JelloFontRenderer;
import info.sigmaclient.sigma.gui.font.JelloFontUtil;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;


public class Cords extends Module {
    public Cords() {
        super("Cords", Category.Gui, "Show a cords in your GUI.");
    }
    public static int offsetY = 0;
    @Override
    public void onEnable() {
        super.onEnable();
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        if (Minecraft.getInstance().isF3Enabled()) {
            return;
        }
        String t = (int)Math.floor(mc.player.getPosX()) + " "+(int)Math.floor(mc.player.getPosY()) +" "+(int)Math.floor(mc.player.getPosZ());
        GL11.glPushMatrix();
        JelloFontRenderer font = (t.length() > 15 + 4) ? (t.length() > 21 + 4 ? JelloFontUtil.jelloFont12 : JelloFontUtil.jelloFont14) : JelloFontUtil.jelloFont16;
        double x = 42.5F - 1 - font.getStringWidth(t) / 2f;
        double y = offsetY;
        font.drawNoBSStringWithBloom(
               t,
                x,
                y,
                -1, 0.1f);
        GL11.glPopMatrix();
        
    }
}
