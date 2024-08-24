package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import info.sigmaclient.sigma.utils.render.ShaderUtil;
import net.minecraft.client.shader.ShaderGroup;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;
import static info.sigmaclient.sigma.utils.render.RenderUtils.createFrameBuffer;

public class Sigma5BlurUtils {
    static String vanillaBlur = "{\n" +
            "    \"targets\": [\n" +
            "        \"swap\"\n" +
            "    ],\n" +
            "    \"passes\": [\n" +
            "        {\n" +
            "            \"name\": \"blur\",\n" +
            "            \"intarget\": \"minecraft:main\",\n" +
            "            \"outtarget\": \"swap\",\n" +
            "            \"uniforms\": [\n" +
            "                {\n" +
            "                    \"name\": \"BlurDir\",\n" +
            "                    \"values\": [ 1.0, 0.0 ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"name\": \"Radius\",\n" +
            "                    \"values\": [ 20.0 ]\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"blur\",\n" +
            "            \"intarget\": \"swap\",\n" +
            "            \"outtarget\": \"minecraft:main\",\n" +
            "            \"uniforms\": [\n" +
            "                {\n" +
            "                    \"name\": \"BlurDir\",\n" +
            "                    \"values\": [ 0.0, 1.0 ]\n" +
            "                },\n" +
            "                {\n" +
            "                    \"name\": \"Radius\",\n" +
            "                    \"values\": [ 20.0 ]\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";
    static ShaderGroup group;
    static int lastWidth, lastHeight;
    private static void init(){
        group.createBindFramebuffers(mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
        lastWidth = mc.getMainWindow().getFramebufferWidth();
        lastHeight = mc.getMainWindow().getFramebufferHeight();
    }
    public static void initAll() {
        try {
            group = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), vanillaBlur, "vanillablur");
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void setValues(float strength) {
        for (int i = 0; i < 2; i++) {
            group.listShaders.get(i).getShaderManager().func_216539_a("Radius").set(strength);
        }
    }

    public static void vblur(float v){
        if(v == 0) return;
        if(mc.getMainWindow().getFramebufferWidth() != 0 && mc.getMainWindow().getFramebufferHeight() != 0 && (lastWidth != mc.getMainWindow().getFramebufferWidth() || lastHeight != mc.getMainWindow().getFramebufferHeight())) {
            init();
        }
        setValues(v);
        GL11.glPushMatrix();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        group.render(mc.timer.renderPartialTicks);
        ShaderUtil.drawQuads();
        RenderSystem.enableTexture();
        GL11.glPopMatrix();
        mc.getFramebuffer().bindFramebuffer(true);
    }

    public static void blur(float v){
        BlurUtils.blur(v);
    }
}
