package info.sigmaclient.sigma.utils.render.blurs;

import info.sigmaclient.sigma.utils.render.ShaderUtil;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;

import java.awt.*;

import static info.sigmaclient.sigma.modules.Module.mc;
import static info.sigmaclient.sigma.utils.render.RenderUtils.startBlend;
import static info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.resetColor;

public class RoundedRectShader {
    private static final ShaderUtil shader = new ShaderUtil("roundedRect");

    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        resetColor();
        startBlend();

        shader.init();

        ScaledResolution sr = new ScaledResolution(mc);

        shader.setUniformf("location", x * sr.getScaleFactor(), (y * sr.getScaleFactor()));
        shader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        shader.setUniformi("tex", 0);
        shader.setUniformf("radius", radius);
        shader.setUniformf("blur",0);
        // Bottom Left
        shader.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,color.getAlpha() / 255f);

        ShaderUtil.drawQuads(x * sr.getScaleFactor(), (y * sr.getScaleFactor()),width * sr.getScaleFactor(), height * sr.getScaleFactor());
        shader.unload();
    }
}
