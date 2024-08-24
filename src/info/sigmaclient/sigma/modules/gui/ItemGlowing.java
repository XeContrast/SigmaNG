package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderShaderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.blurs.Bloom;
import net.minecraft.client.shader.Framebuffer;


public class ItemGlowing extends Module {
    public ItemGlowing() {
        super("ItemGlowing", Category.Gui, "FPS reducer", true);
    }
    public static void drawHand(Runnable draw){
        if(framebuffer == null){
            return;
        }
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        draw.run();
        framebuffer.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(false);
    }

    static Framebuffer framebuffer;
    @EventTarget
    public void onRenderShaderEvent(RenderShaderEvent event) {
        framebuffer = RenderUtils.createFrameBuffer(framebuffer, false);
        if(framebuffer == null) return;
        Bloom.renderBlur(framebuffer.framebufferTexture, 4, 2);
        framebuffer.framebufferClear();
        mc.getFramebuffer().bindFramebuffer(false);
    }
}
