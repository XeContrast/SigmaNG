package info.sigmaclient.sigma.utils.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GLUtil {
    public static void render(int mode, Runnable render){
        glBegin(mode);
        render.run();
        glEnd();
    }

    public static void setup2DRendering(Runnable f) {
        GlStateManager.enableBlend();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        f.run();
        glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public static void rotate(float x, float y, float rotate, Runnable f) {
        RenderSystem.pushMatrix();
        RenderSystem.translated(x, y, 0);
        RenderSystem.rotatef(rotate, 0, 0, -1);
        RenderSystem.translated(-x, -y, 0);
        f.run();
        RenderSystem.popMatrix();
    }
    public static void rotateTexture(Runnable texture, float degrees) {
        float radians = (float) Math.toRadians(degrees);

        GlStateManager.pushMatrix();
        GlStateManager.translated(0, 0, 0.0f);
        GlStateManager.rotatef(radians, 0.0f, 0.0f, 1.0f);
        GlStateManager.translated(-0, -0, 0.0f);

        texture.run();

        GlStateManager.popMatrix();

    }

    public static void rotateTexture(Runnable texture, float T, int textureWidth, int textureHeight) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5f, 0.5f, 0.0f);
        GL11.glRotatef(T * 360.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
        texture.run();
        GL11.glPopMatrix();
    }

    public static void startBlend() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void endBlend() {
        GlStateManager.disableBlend();
    }


}
