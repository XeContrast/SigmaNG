package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import info.sigmaclient.sigma.modules.render.ChestESP;
import info.sigmaclient.sigma.modules.render.ESP;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;
import static info.sigmaclient.sigma.utils.render.RenderUtils.preGlHints;
import static org.lwjgl.opengl.GL11.*;

// Sigma client (c)
// 2014 - 2019
public class ShadowESP {

    public IRenderTypeBuffer.Impl renderBuffer;
    public static RenderMode currentRenderMode;
    {
        this.renderBuffer = IRenderTypeBuffer.getImpl(mc.getRenderTypeBuffers().fixedBuffers, new BufferBuilder(256));
    }
    public void renderEvent() {
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glEnable(GL_POINT_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
            this.setupRender();
        GlStateManager.disableLighting();
            StencilUtil.initStencilToWrite();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.applyRenderMode(RenderMode.renderEntity);
            StencilUtil.readStencilBuffer(0);
            GL11.glLineWidth(1.0f);
            this.drawShadow();
            this.applyRenderMode(RenderMode.setupRender);
            GL11.glColor4f(1.0f, 0.0f, 1.0f, 0.1f);
            GL11.glEnable(GL_BLEND);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            StencilUtil.uninitStencilBuffer();
            this.resetRender();
            this.renderBuffer.finish();
        GL11.glDisable(GL_LINE_SMOOTH);
        GL11.glDisable(GL_POINT_SMOOTH);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(true);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.enableDepth();
    }

    public static Vector3d getBlockPosVector(final BlockPos initializeSettings) {
        return new Vector3d(initializeSettings.getX() -
                Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().x,
                initializeSettings.getY() - Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().y,
                initializeSettings.getZ() - Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().z);
    }
    public void renderChest(Runnable runnable) {
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glEnable(GL_POINT_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        this.setupRender();
        GlStateManager.disableLighting();
        StencilUtil.initStencilToWrite();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.updatePosition(RenderMode.renderEntity, runnable);
        StencilUtil.readStencilBuffer(0);
        GL11.glLineWidth(1.0f);
//        this.drawShadow();
        this.updatePosition(RenderMode.setupRender, runnable);
        GL11.glColor4f(1.0f, 0.0f, 1.0f, 0.1f);
        GL11.glEnable(GL_BLEND);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        StencilUtil.uninitStencilBuffer();
        this.resetRender();
        this.renderBuffer.finish();
        GL11.glDisable(GL_LINE_SMOOTH);
        GL11.glDisable(GL_POINT_SMOOTH);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(true);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    public void renderEvent2() {
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glEnable(GL_POINT_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        GL11.glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        this.setupRender();
        GlStateManager.disableLighting();
        StencilUtil.initStencilToWrite();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.updateVelocity(RenderMode.renderEntity);
        StencilUtil.readStencilBuffer(0);
        GL11.glLineWidth(1.0f);
//        this.drawShadow();
        this.updateVelocity(RenderMode.setupRender);
        GL11.glColor4f(1.0f, 0.0f, 1.0f, 0.1f);
        GL11.glEnable(GL_BLEND);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        StencilUtil.uninitStencilBuffer();
        this.resetRender();
        this.renderBuffer.finish();
        GL11.glDisable(GL_LINE_SMOOTH);
        GL11.glDisable(GL_POINT_SMOOTH);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(true);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.enableDepth();
    }

    public static Vector3d getEntityVector(final Entity entity) {
        return new Vector3d(
                entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * mc.timer.renderPartialTicks,
                entity.lastTickPosY + (entity.getPosY() - entity.lastTickPosY) * mc.timer.renderPartialTicks,
                entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * mc.timer.renderPartialTicks
        );
    }

    public static Vector3d getEntityRenderVector(final Entity entity) {
        final Vector3d entityVector = getEntityVector(entity);
        return new Vector3d(entityVector.x - Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().x,
                entityVector.y - Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().y,
                entityVector.z - Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView().z);
    }
    private void drawShadow() {
        preGlHints();
        mc.world.getLoadedEntityList().forEach((entity) -> {
//            堧鏟ᔎ㕠釒.霥瀳놣㠠釒(핇댠䂷呓贞.white.哺卫콗鱀ಽ, 0.8f);
            if (!(!ESP.isTargetEnable(entity))) {
                final double xCoordinate = getEntityRenderVector(entity).x;
                final double yCoordinate = getEntityRenderVector(entity).y;
                final double zCoordinate = getEntityRenderVector(entity).z;
                GL11.glPushMatrix();
                GL11.glColor4f(1,1,1,0.8f);
                GL11.glAlphaFunc(519, 0.0f);
                GL11.glTranslated(xCoordinate, yCoordinate, zCoordinate);
                GL11.glTranslatef(0.0f, entity.getHeight(), 0.0f);
                GL11.glTranslatef(0.0f, 0.1f, 0.0f);
                GL11.glRotatef(mc.gameRenderer.getActiveRenderInfo().getYaw(), 0.0f, -1.0f, 0.0f);
                GL11.glScalef(-0.11f, -0.11f, -0.11f);
                RenderUtils.drawTextureLocation(-entity.getWidth() * 22.0f,
                        -entity.getHeight() * 5.5f,
                        entity.getWidth() * 44.0f,
                        entity.getHeight() * 21.0f,
                        "alt/shadow", Color.WHITE);
//                뚔弻缰硙柿.釒蕃ၝ姮딨.睬醧㕠ꈍ롤驋();
                GL11.glPopMatrix();
            }
        });
    }

    private void applyRenderMode(final RenderMode renderMode) {
        GL11.glDepthFunc(GL_ALWAYS);
        this.currentRenderMode = renderMode;
//        renderMode = renderMode;
        final int colorInt = ESP.color.getColorInt();
        final float n = (colorInt >> 24 & 0xFF) / 255.0f;
        final float n2 = (colorInt >> 16 & 0xFF) / 255.0f;
        final float n3 = (colorInt >> 8 & 0xFF) / 255.0f;
        final float n4 = (colorInt & 0xFF) / 255.0f;
//        GL11.glEnable(GL_LIGHTING);
        GL11.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { n2, n3, n4, n });
        GlStateManager.disableLighting();

        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glEnable(GL_POLYGON_OFFSET_LINE);
            GL11.glLineWidth(2.0f);
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glEnable(GL_ALPHA_TEST);
//            GL11.glEnable(GL_LIGHTING);
        }
        for (final Entity entity : mc.world.getLoadedEntityList()) {
            if (!ESP.isTargetEnable(entity)) {
                continue;
            }
            GL11.glPushMatrix();
            final Vector3d projectedView = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double viewX = projectedView.x;
            final double viewY = projectedView.y;
            final double viewZ = projectedView.z;
            final MatrixStack matrixStack = new MatrixStack();
            final boolean entityShadows = mc.gameSettings.entityShadows;
            final boolean isVisible = entity.getFlag(5);
            final int oldHu =  entity.hurtResistantTime;
             int oldHurt = 0;
            if(entity instanceof LivingEntity e) {
                oldHurt = e.hurtTime;
            }
            GlStateManager.disableLighting();
            GlStateManager.color4f(n, n2, n3, 0.5f);
            RenderUtils.startBlend();
            mc.gameSettings.entityShadows = false;
            final int fireTimer = entity.getFireTimer();
            final boolean flag0 = entity.getFlag(0);
            entity.forceFireTicks(0);
            entity.setFlag(0, false);
            this.renderEntity(entity, viewX, viewY, viewZ, mc.timer.renderPartialTicks,matrixStack, this.renderBuffer);
            entity.forceFireTicks(fireTimer);
            entity.setFlag(0, flag0);
            entity.setFlag(5, isVisible);
            if(entity instanceof LivingEntity e) {
                e.hurtTime = oldHurt;
            }
            mc.gameSettings.entityShadows = entityShadows;
            GL11.glPopMatrix();
        }
        this.renderBuffer.finish(RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntityCutoutNoCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntitySmoothCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getLines());
        this.renderBuffer.finish();
        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        }
        this.currentRenderMode = RenderMode.render;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }
    private void updatePosition(final RenderMode renderMode, Runnable runnable) {
        GL11.glDepthFunc(GL_ALWAYS);
        this.currentRenderMode = renderMode;
//        renderMode = renderMode;
        final int colorInt = ESP.color.getColorInt();
        final float alpha = (colorInt >> 24 & 0xFF) / 255.0f;
        final float red = (colorInt >> 16 & 0xFF) / 255.0f;
        final float green = (colorInt >> 8 & 0xFF) / 255.0f;
        final float blue = (colorInt & 0xFF) / 255.0f;
//        GL11.glEnable(GL_LIGHTING);
        GL11.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { red, green, blue, alpha});
        GlStateManager.disableLighting();

        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glEnable(GL_POLYGON_OFFSET_LINE);
            GL11.glLineWidth(2.0f);
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glEnable(GL_ALPHA_TEST);
//            GL11.glEnable(GL_LIGHTING);
        }

        for(TileEntity tileEntity : mc.world.loadedTileEntityList){
            if(
                    (ChestESP.chest.isEnable() && tileEntity instanceof ChestTileEntity) ||
                            (ChestESP.enderChest.isEnable() && tileEntity instanceof EnderChestTileEntity) ||
                            (ChestESP.bed.isEnable() && tileEntity instanceof BedTileEntity)
            ){
                final double xCoordinate = getBlockPosVector(tileEntity.getPos()).x;
                final double yCoordinate = getBlockPosVector(tileEntity.getPos()).y;
                final double zCoordinate = getBlockPosVector(tileEntity.getPos()).z;
//                System.out.println(tileEntity.getPos());
                BoxOutlineESP.drawOutlinedBox(tileEntity.getBlockState().getShape(mc.world, tileEntity.getPos()).getBoundingBox().offset(
                        xCoordinate, yCoordinate, zCoordinate
                ), -1);
            }
        }
        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        }
        this.currentRenderMode = RenderMode.render;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }

    private void updateVelocity(final RenderMode renderMode) {
        GL11.glDepthFunc(GL_ALWAYS);
        this.currentRenderMode = renderMode;
//        renderMode = renderMode;
        final int colorInt = Color.WHITE.getRGB();
        final float alpha = (colorInt >> 24 & 0xFF) / 255.0f;
        final float red = (colorInt >> 16 & 0xFF) / 255.0f;
        final float green = (colorInt >> 8 & 0xFF) / 255.0f;
        final float blue = (colorInt & 0xFF) / 255.0f;
//        GL11.glEnable(GL_LIGHTING);
        GL11.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { red, green, blue, alpha});
        GlStateManager.disableLighting();

        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glEnable(GL_POLYGON_OFFSET_LINE);
            GL11.glLineWidth(6.0f);
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glEnable(GL_ALPHA_TEST);
//            GL11.glEnable(GL_LIGHTING);
        }
        for (final Entity entity : mc.world.getLoadedEntityList()) {
            if (!(mc.isEntityGlowing2(entity))) {
                continue;
            }
            if(mc.gameSettings.getPointOfView().func_243192_a()){
                if(entity == mc.player){
                    continue;
                }
            }
            GL11.glPushMatrix();
            final Vector3d projectedView = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double projectedX = projectedView.x;
            final double projectedY = projectedView.y;
            final double projectedZ = projectedView.z;
            final MatrixStack matrixStack = new MatrixStack();
            final boolean entityShadows = mc.gameSettings.entityShadows;
            GlStateManager.disableLighting();
            GlStateManager.color4f(alpha, red, green, 0.5f);
            RenderUtils.startBlend();
            mc.gameSettings.entityShadows = false;
            final int fireTimer = entity.getFireTimer();
            final boolean isOnFire = entity.getFlag(0);
            entity.forceFireTicks(0);
            entity.setFlag(0, false);
            this.renderEntity(entity, projectedX, projectedY, projectedZ, mc.timer.renderPartialTicks,matrixStack, this.renderBuffer);
            entity.forceFireTicks(fireTimer);
            entity.setFlag(0, isOnFire);
            mc.gameSettings.entityShadows = entityShadows;
            GL11.glPopMatrix();
        }
        this.renderBuffer.finish(RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntityCutoutNoCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getEntitySmoothCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        this.renderBuffer.finish(RenderType.getLines());
        this.renderBuffer.finish();
        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        }
        this.currentRenderMode = RenderMode.render;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }

    public void renderEntity(final Entity entity, final double x, final double y, final double z, final float partialTicks,
                             final MatrixStack matrixStack, final IRenderTypeBuffer renderBuffer) {
        mc.worldRenderer.renderManager.renderEntityStatic(entity,
                interpolate(partialTicks, entity.lastTickPosX, entity.getPosX()) - x,
                interpolate(partialTicks, entity.lastTickPosY, entity.getPosY()) - y,
                interpolate(partialTicks, entity.lastTickPosZ, entity.getPosZ()) - z,
                interpolate(partialTicks, entity.prevRotationYaw, entity.rotationYaw),
                partialTicks,
                matrixStack,
                renderBuffer,
                238);
    }

    public static double interpolate(final double n, final double n2, final double n3) {
        return n2 + n * (n3 - n2);
    }
    public static float interpolate(final float n, final float n2, final float n3) {
        return n2 + n * (n3 - n2);
    }
//    @EventTarget
//    public void 驋펊䩉웨䢿(final RenderEvent RenderEvent) {
////        if (this.isModuleEnable()) {
//            if (currentRenderMode != RenderMode.render) {
//                RenderEvent.setCanceled(false);
//            }
////        }
//    }
    public static boolean cannotRenderName(){
        return currentRenderMode != RenderMode.render;
    };
//    @EventTarget
//    public void renderNotifications(final EntityRenderEvent EntityRenderEvent) {
////        if (this.isModuleEnable()) {
//            if (콗뎫鷏Ꮺ놣 != RenderMode.render) {
//                if (EntityRenderEvent.getEntity() instanceof SomeEntityClass) {
//                    EntityRenderEvent.setCanceled(true);
//                }
//            }
////        }
//    }

    private void setupRender() {
//        startBlend();
        RenderUtils.startBlend();

        GL11.glLineWidth(3.0f);
//        GL11.glEnable(GL_LUMINANCE8);
        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_LIGHTING);
//        GL11.glEnable(GL_ALPHA_TEST);
        GL11.glDisable(GL_TEXTURE_2D);
//        GL11.glDisable(GL_COLOR_MATERIAL);
        GL11.glDisable(GL_DEPTH_TEST);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(GL_LIGHTING);
//        GL11.glEnable(GL_TEXTURE_2D);
        mc.gameRenderer.lightmapTexture.disableLightmap();
    }

    private void resetRender() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(GL_LIGHTING);
        GL11.glEnable(GL_TEXTURE_2D);
//        GL11.glEnable(GL_COLOR_MATERIAL);
//        GlStateManager.multiTexCoord2f(33986, 240.0f, 240.0f);
//        刃ꦱ筕睬뎫欫.㼜圭좯玑㨳ꦱ();
        final TextureManager textureManager = mc.getTextureManager();
//        mc.textureManager();
//        textureManager.bindTexture(RESOURCE_LOCATION_EMPTY);
        mc.gameRenderer.lightmapTexture.enableLightmap();
        GL11.glLightModelfv(2899, new float[] { 0.4f, 0.4f, 0.4f, 1.0f });
        currentRenderMode = RenderMode.render;
        GlStateManager.disableLighting();
    }

}
