package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.modules.render.ESP;
import info.sigmaclient.sigma.utils.render.ColorUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;
import static org.lwjgl.opengl.GL11.*;


// Sigma client (c)
// 2014 - 2019
public class BoxOutlineESP {

    public static void resetFramebufferDepth() {
        final Framebuffer framebuffer = net.minecraft.client.Minecraft.getInstance().getFramebuffer();
        if (framebuffer != null) {
            if (framebuffer.depthBuffer > -1) {
                deleteAndRecreateDepthBuffer(framebuffer);
                framebuffer.depthBuffer = -1;
            }
        }
    }

    public static void deleteAndRecreateDepthBuffer(final Framebuffer depthFramebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(depthFramebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight());
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    public static boolean stencilEnabled;
    public static void initStencil() {
        GL11.glPushMatrix();
        resetFramebufferDepth();
        GL11.glEnable(2960);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
        GL11.glStencilFunc(512, 1, 1);
        GL11.glStencilOp(7681, 7680, 7680);
        GL11.glStencilMask(1);
        GL11.glClear(1024);
        stencilEnabled = true;
    }

    public static void startStencil(final StencilOperation StencilOperation) {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0);
        GL11.glStencilFunc((StencilOperation != StencilOperation.REPLACE) ? 517 : 514, 1, 1);
    }

    public static void endStencil() {
        GL11.glStencilMask(-1);
        GL11.glDisable(2960);
        GL11.glPopMatrix();
        stencilEnabled = false;
    }

    public enum StencilOperation {

        REPLACE,
        INVERT;
    }
    public void draw() {
        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glEnable(GL_POINT_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

        GlStateManager.disableLighting();
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
//        if (Box.浣韤쬷햖㼜.釒쇽璧敤쥦쥡 != null && Box.浣韤쬷햖㼜.姮䢶牰㝛硙䩜 != null) {
//            this.startDraw();
//            initStencil();
        StencilUtil.initStencilToWrite();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawBoxes(false);
//            startStencil(StencilOperation.ᔎ渺훔䣓䄟);
        StencilUtil.readStencilBuffer(0);
            GL11.glLineWidth(3.0f);
//            RenderSystem.alphaFunc(518, 0.0f);
//            GlStateManager.enableAlphaTest();
            this.drawShadows();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.1f);
//            GL11.glEnable(3042);
//            GL11.glDisable(2896);
            this.drawBoxes(true);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//            endStencil();
        StencilUtil.uninitStencilBuffer();
        
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(true);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableBlend();

        GL11.glDisable(GL_LINE_SMOOTH);
        GL11.glDisable(GL_POINT_SMOOTH);
//            this.endDraw();
//        }
    }

    private void drawShadows() {
            mc.world.getLoadedEntityList().forEach((key) -> {
                if(!ESP.isTargetEnable(key)) return;
                            if (key != mc.player) {
                                GL11.glPushMatrix();
//                                GL11.glAlphaFunc(519, 0.0f);

                                double n = Minecraft.getInstance().timer.renderPartialTicks;
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

                                GL11.glTranslatef(0.0f, key.getHeight(), 0.0f);
                                GL11.glTranslatef(0.0f, 0.1f, 0.0f);
                                GL11.glRotatef(mc.getRenderManager().info.getYaw(), 0.0f, -1.0f, 0.0f);
                                GL11.glScalef(-0.11f, -0.11f, -0.11f);
                                RenderUtils.drawTextureLocation(
                                        -key.getWidth() * 22.0f,
                                        -key.getHeight() * 5.5f,
                                        key.getWidth() * 44.0f,
                                        key.getHeight() * 21.0f,
                                        "esp/shadow", new Color(1f,1f,1f,1f));
                                GL11.glPopMatrix();
                            }
            });
//        }
    }
    private void drawBoxes(final boolean b) {
        for (final Entity key : mc.world.getAllEntities()) {
            if(!ESP.isTargetEnable(key)) continue;
                if (key == mc.player) {
                    continue;
                }
                GL11.glPushMatrix();
//                GL11.glDisable(2929);
//                GL11.glEnable(3042);
                final int colorRGB = ESP.color.getColorInt();
            double camX = RenderUtils.getRenderPos().renderPosX;
            double camY = RenderUtils.getRenderPos().renderPosY;
            double camZ = RenderUtils.getRenderPos().renderPosZ;
            info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.translate(-camX, -camY, -camZ);
//                final AxisAlignedBB boundingBox = key.getBoundingBox().boundingBox(0.10000000149011612);
            final AxisAlignedBB boundingBox = key.getBoundingBox().offset(
                    (key.getPosX() - key.lastTickPosX) * mc.timer.renderPartialTicks - (key.getPosX() - key.lastTickPosX),
                    (key.getPosY() - key.lastTickPosY) * mc.timer.renderPartialTicks - (key.getPosY() - key.lastTickPosY),
                    (key.getPosZ() - key.lastTickPosZ) * mc.timer.renderPartialTicks - (key.getPosZ() - key.lastTickPosZ))
                    .boundingBox(0.10000000149011612);
                if (b) {
                    drawFilledBox(boundingBox, 3.0f, ColorUtils.reAlpha(new Color(colorRGB),0.35f).getRGB());
                }
                else {
                    drawOutlinedBox(boundingBox, new Color(0xFFFFFF).getRGB());
                }
//                GL11.glDisable(3042);
                GL11.glPopMatrix();
//            }
        }
    }
    public static void drawOutlinedBox(final AxisAlignedBB boundingBox, final int n) {
        if (boundingBox != null) {
            GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
            GL11.glEnable(3042);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glDisable(2896);
            GL11.glLineWidth(1.8f * SigmaNG.lineWidth);
            GL11.glBlendFunc(770, 771);
//            GL11.glEnable(2848);

            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glEnable(GL_TEXTURE_2D);
//            GL11.glEnable(2896);
//            GL11.glDisable(2848);
            GL11.glDisable(3042);
        }
    }
    public static void drawFilledBox(final AxisAlignedBB boundingBox, final float n, final int n2) {
        if (boundingBox != null) {
            GL11.glColor4f((n2 >> 16 & 0xFF) / 255.0f, (n2 >> 8 & 0xFF) / 255.0f, (n2 & 0xFF) / 255.0f, (n2 >> 24 & 0xFF) / 255.0f);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glDisable(2896);
            GL11.glLineWidth(n);
//            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBegin(3);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            GL11.glEnd();
            GL11.glEnable(GL_TEXTURE_2D);
//            GL11.glEnable(2896);
//            GL11.glDisable(2848);
            GL11.glDisable(3042);
        }
    }

    private void startDraw() {
        GL11.glLineWidth(3.0f);
        GL11.glPointSize(3.0f);
        GL11.glEnable(2832);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glDisable(2903);
        GL11.glDisable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        Minecraft.getInstance().gameRenderer.getLightTexture().disableLightmap();
    }

    private void endDraw() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glEnable(2903);
//        GlStateManager.multiTexCoord2f(33986, 240.0f, 240.0f);
//        刃ꦱ筕睬뎫欫.㼜圭좯玑㨳ꦱ();
//        GL11.glGetTexImage(GL_TEXTURE_2D, 0, 6407, 0, 6407, 5121);
        final TextureManager textureManager = mc.getTextureManager();
//        Box.浣韤쬷햖㼜.textureManager();
        textureManager.bindTexture(TextureManager.RESOURCE_LOCATION_EMPTY);
        Minecraft.getInstance().gameRenderer.getLightTexture().enableLightmap();
        GlStateManager.disableLighting();
//        Box.浣韤쬷햖㼜.佉낛鱀ꪕ轐揩.曞랾醧杭Ꮀ玑.挐敤㮃䁞붛嶗();
    }
}
