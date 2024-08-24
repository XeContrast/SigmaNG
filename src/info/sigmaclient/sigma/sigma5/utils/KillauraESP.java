package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;


public class KillauraESP {
    public static void drawESP(final Entity key, float anim, Color cd) {
        GlStateManager.disableLighting();
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(32925);
        GL11.glEnable(2929);
        GL11.glLineWidth(1.4f);
        double n = Minecraft.getInstance().timer.renderPartialTicks;
//        if (!key.낛揩牰㻣퉧랾()) {
//            n = 0.0;
//        }
        GL11.glTranslated(
                key.lastTickPosX + (key.getPosX() - key.lastTickPosX) * n,
                key.lastTickPosY + (key.getPosY() - key.lastTickPosY) * n,
                key.lastTickPosZ + (key.getPosZ() - key.lastTickPosZ) * n);
        double camX = RenderUtils.getRenderPos().renderPosX;
        double camY = RenderUtils.getRenderPos().renderPosY;
        double camZ = RenderUtils.getRenderPos().renderPosZ;
        GL11.glTranslated(
                -camX,
                -camY,
                -camZ
        );
        GL11.glEnable(32823);
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        GL11.glAlphaFunc(519, 0.0f);
        final int n2 = 1800;
        final float n3 = System.currentTimeMillis() % n2 / (float)n2;
        final boolean b = n3 > 0.5f;
        final float n4 = b ? (1.0f - n3 * 2.0f % 1.0f) : (n3 * 2.0f);
        GL11.glTranslatef(0.0f, (key.getHeight() + 0.4f) * n4, 0.0f);
        final float n5 = (float)Math.sin(n4 * 3.141592653589793);
        샱훔騜좯卒(b, 0.45f * n5, 0.6f, 0.35f * n5, anim, cd);
        GL11.glPushMatrix();
        GL11.glTranslated(
                camX,
                camY,
                camZ
        );
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(32925);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void 샱훔騜좯卒(final boolean b, final float n, final float n2, final float n3, final float n4, Color color2) {
        GL11.glShadeModel(GL_SMOOTH);
        GL11.glDisable(32823);
        GL11.glDisable(2929);
        GL11.glBegin(5);
        final int n5 = (int)(360.0f / (40.0f * n2));
        final float n6 = color2.getRed() / 255.0f;
        final float n7 = color2.getGreen() / 255.0f;
        final float n8 = color2.getBlue() / 255.0f;
        for (int i = 0; i <= 360 + n5; i += n5) {
            int n9 = i;
            if (n9 > 360) {
                n9 = 0;
            }
            final double n10 = Math.sin(n9 * 3.141592653589793 / 180.0) * n2;
            final double n11 = Math.cos(n9 * 3.141592653589793 / 180.0) * n2;
            GL11.glColor4f(n6, n7, n8, b ? (n3 * n4) : 0.0f);
            GL11.glVertex3d(n10, 0.0, n11);
            GL11.glColor4f(n6, n7, n8, b ? 0.0f : (n3 * n4));
            GL11.glVertex3d(n10, n, n11);
        }
        GL11.glEnd();
        GL11.glLineWidth(2.2f);
        GL11.glBegin(3);
        for (int j = 0; j <= 360 + n5; j += n5) {
            int n12 = j;
            if (n12 > 360) {
                n12 = 0;
            }
            final double n13 = Math.sin(n12 * 3.141592653589793 / 180.0) * n2;
            final double n14 = Math.cos(n12 * 3.141592653589793 / 180.0) * n2;
            GL11.glColor4f(n6, n7, n8, (0.5f + 0.5f * n3) * n4);
            GL11.glVertex3d(n13, b ? 0.0 : ((double)n), n14);
        }
        GL11.glEnd();
        GL11.glEnable(2929);
        GL11.glShadeModel(GL_FLAT);
        GlStateManager.disableLighting();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

}
