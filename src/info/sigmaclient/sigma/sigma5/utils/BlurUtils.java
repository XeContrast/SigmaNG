//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\1\Desktop\Minecraft-Deobfuscator3000-1.2.3\config"!

/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 * 
 * This code belongs to WYSI-Foundation. Please give credits when using this in your repository.
 */
package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.ShaderUtil;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import static info.sigmaclient.sigma.utils.render.RenderUtils.createFrameBuffer;
import static org.lwjgl.opengl.GL11.*;

public class BlurUtils {
    
    private static Minecraft mc = Minecraft.getInstance();

    private static ResourceLocation blurShader = new ResourceLocation("shaders/post/blur.json");
    public static ShaderGroup shaderGroup;
    private static float width, height;
    public static Framebuffer 捉䢶欫哺酭;
    public static Framebuffer 햖鶊Ꮤ䈔藸;

    private static void setValues(float strength) {
        for (int i = 0; i < 2; i++) {
            shaderGroup.listShaders.get(i).getShaderManager().func_216539_a("Radius").set(strength);
        }
    }
    public static void stopShader(){
        shaderGroup.close();
        shaderGroup=null;
    }
    public static void reInit(){
        try {
            shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), JelloSwapBlur.jello, "jelloblur");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateFrame(){
        if(!SigmaNG.getSigmaNG().inGameGuiBlur) return;
        if(mc.getMainWindow().getFramebufferWidth() != 0 && mc.getMainWindow().getFramebufferHeight() != 0 && (width != mc.getMainWindow().getFramebufferWidth() || height != mc.getMainWindow().getFramebufferHeight())) {
            shaderGroup.createBindFramebuffers(mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());

            width = mc.getMainWindow().getFramebufferWidth();
            height = mc.getMainWindow().getFramebufferHeight();
            setValues(35.0f);
            捉䢶欫哺酭 = shaderGroup.getFramebufferRaw("jello");
            햖鶊Ꮤ䈔藸 = shaderGroup.getFramebufferRaw("jelloswap");
        }
        捉䢶欫哺酭.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        햖鶊Ꮤ䈔藸.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);

        捉䢶欫哺酭.bindFramebuffer(true);
        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glEnable(GL_LIGHTING);
        GL11.glDisable(GL_LINE_SMOOTH);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GlStateManager.disableLighting();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        RenderSystem.disableBlend();
        GL11.glPushMatrix();
//        GL11.glDisable((int)2929);
//        GL11.glDisable((int)3008);
//        RenderSystem.enableLighting();
        RenderSystem.disableLighting();
        shaderGroup.render(mc.timer.renderPartialTicks);
//        mc.gameRenderer.shaderGroup = shaderGroup;
//        mc.gameRenderer.stopUseShader();
        GL11.glPopMatrix();
        RenderUtils.startBlend();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderUtils.resetColor();
        RenderSystem.disableLighting();
        RenderSystem.enableTexture();
        mc.getTextureManager().bindTexture(TextureManager.RESOURCE_LOCATION_EMPTY);
//        GL11.glEnable((int)3008);
        GL11.glColor4f(1,1,1,1);
        RenderSystem.depthFunc(519);
        mc.getFramebuffer().bindFramebuffer(true);
    }
    public static void blur(float blurStrength) {
        if (!RenderSystem.isOnRenderThread()) return;
        if(blurStrength == 0) {
            return;
        }

        GL11.glPushMatrix();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        捉䢶欫哺酭.bindFramebufferTexture();
        ShaderUtil.drawQuads();
        RenderSystem.enableTexture();
        GL11.glPopMatrix();
        mc.getFramebuffer().bindFramebuffer(true);
    }
}


