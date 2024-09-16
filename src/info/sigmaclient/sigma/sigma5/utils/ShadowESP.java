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
                final double 欫缰곻睬괠 = getEntityRenderVector(entity).x;
                final double ኞ甐㞈錌ಽ = getEntityRenderVector(entity).y;
                final double 酋ꮀ聛眓쬫 = getEntityRenderVector(entity).z;
                GL11.glPushMatrix();
                GL11.glColor4f(1,1,1,0.8f);
                GL11.glAlphaFunc(519, 0.0f);
                GL11.glTranslated(欫缰곻睬괠, ኞ甐㞈錌ಽ, 酋ꮀ聛眓쬫);
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
            final Vector3d 㕠츚鶲霥ಽ뼢 = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double 鶲웎頉붃䢶蚳 = 㕠츚鶲霥ಽ뼢.x;
            final double 鞞躚뗴ᔎ娍ꪕ = 㕠츚鶲霥ಽ뼢.y;
            final double 붛嶗啖ኞ츚弻 = 㕠츚鶲霥ಽ뼢.z;
            final MatrixStack 햖䩉待뼢鱀 = new MatrixStack();
            final boolean 펊褕室뗴嶗疂 = mc.gameSettings.entityShadows;
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
            final int 筕ꦱ붃眓ꁈ웎 = entity.getFireTimer();
            final boolean 콗햖竁ꈍ酋釒 = entity.getFlag(0);
            entity.forceFireTicks(0);
            entity.setFlag(0, false);
            this.renderEntity(entity, 鶲웎頉붃䢶蚳, 鞞躚뗴ᔎ娍ꪕ, 붛嶗啖ኞ츚弻, mc.timer.renderPartialTicks,햖䩉待뼢鱀, this.renderBuffer);
            entity.forceFireTicks(筕ꦱ붃眓ꁈ웎);
            entity.setFlag(0, 콗햖竁ꈍ酋釒);
            entity.setFlag(5, isVisible);
            if(entity instanceof LivingEntity e) {
                e.hurtTime = oldHurt;
            }
            mc.gameSettings.entityShadows = 펊褕室뗴嶗疂;
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
        this.currentRenderMode = RenderMode.泹唟쥦㞈쿨;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }
    private void updatePosition(final RenderMode 콗뎫鷏Ꮺ놣, Runnable runnable) {
        GL11.glDepthFunc(GL_ALWAYS);
        this.currentRenderMode = 콗뎫鷏Ꮺ놣;
//        콗뎫鷏Ꮺ놣 = 콗뎫鷏Ꮺ놣;
        final int 侃姮쇽待敤 = ESP.color.getColorInt();
        final float n = (侃姮쇽待敤 >> 24 & 0xFF) / 255.0f;
        final float n2 = (侃姮쇽待敤 >> 16 & 0xFF) / 255.0f;
        final float n3 = (侃姮쇽待敤 >> 8 & 0xFF) / 255.0f;
        final float n4 = (侃姮쇽待敤 & 0xFF) / 255.0f;
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

        for(TileEntity tileEntity : mc.world.loadedTileEntityList){
            if(
                    (ChestESP.chest.isEnable() && tileEntity instanceof ChestTileEntity) ||
                            (ChestESP.enderChest.isEnable() && tileEntity instanceof EnderChestTileEntity) ||
                            (ChestESP.bed.isEnable() && tileEntity instanceof BedTileEntity)
            ){
                final double 欫缰곻睬괠 = getBlockPosVector(tileEntity.getPos()).x;
                final double ኞ甐㞈錌ಽ = getBlockPosVector(tileEntity.getPos()).y;
                final double 酋ꮀ聛眓쬫 = getBlockPosVector(tileEntity.getPos()).z;
//                System.out.println(tileEntity.getPos());
                BoxOutlineESP.drawOutlinedBox(tileEntity.getBlockState().getShape(mc.world, tileEntity.getPos()).getBoundingBox().offset(
                        欫缰곻睬괠, ኞ甐㞈錌ಽ, 酋ꮀ聛眓쬫
                ), -1);
            }
        }
        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            GL11.glDisable(GL_POLYGON_OFFSET_LINE);
        }
        this.currentRenderMode = RenderMode.泹唟쥦㞈쿨;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }

    private void updateVelocity(final RenderMode 콗뎫鷏Ꮺ놣) {
        GL11.glDepthFunc(GL_ALWAYS);
        this.currentRenderMode = 콗뎫鷏Ꮺ놣;
//        콗뎫鷏Ꮺ놣 = 콗뎫鷏Ꮺ놣;
        final int 侃姮쇽待敤 = Color.WHITE.getRGB();
        final float n = (侃姮쇽待敤 >> 24 & 0xFF) / 255.0f;
        final float n2 = (侃姮쇽待敤 >> 16 & 0xFF) / 255.0f;
        final float n3 = (侃姮쇽待敤 >> 8 & 0xFF) / 255.0f;
        final float n4 = (侃姮쇽待敤 & 0xFF) / 255.0f;
//        GL11.glEnable(GL_LIGHTING);
        GL11.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, new float[] { n2, n3, n4, n });
        GlStateManager.disableLighting();

        if (this.currentRenderMode == RenderMode.setupRender) {
            GL11.glEnable(GL_POLYGON_OFFSET_LINE);
            GL11.glLineWidth(6.0f);
            GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            GL11.glDisable(GL_TEXTURE_2D);
//            GL11.glEnable(GL_ALPHA_TEST);
//            GL11.glEnable(GL_LIGHTING);
        }
        for (final Entity 璧室䖼頉啖 : mc.world.getLoadedEntityList()) {
            if (!(mc.isEntityGlowing2(璧室䖼頉啖))) {
                continue;
            }
            if(mc.gameSettings.getPointOfView().func_243192_a()){
                if(璧室䖼頉啖 == mc.player){
                    continue;
                }
            }
            GL11.glPushMatrix();
            final Vector3d 㕠츚鶲霥ಽ뼢 = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
            final double 鶲웎頉붃䢶蚳 = 㕠츚鶲霥ಽ뼢.x;
            final double 鞞躚뗴ᔎ娍ꪕ = 㕠츚鶲霥ಽ뼢.y;
            final double 붛嶗啖ኞ츚弻 = 㕠츚鶲霥ಽ뼢.z;
            final MatrixStack 햖䩉待뼢鱀 = new MatrixStack();
            final boolean 펊褕室뗴嶗疂 = mc.gameSettings.entityShadows;
            GlStateManager.disableLighting();
            GlStateManager.color4f(n, n2, n3, 0.5f);
            RenderUtils.startBlend();
            mc.gameSettings.entityShadows = false;
            final int 筕ꦱ붃眓ꁈ웎 = 璧室䖼頉啖.getFireTimer();
            final boolean 콗햖竁ꈍ酋釒 = 璧室䖼頉啖.getFlag(0);
            璧室䖼頉啖.forceFireTicks(0);
            璧室䖼頉啖.setFlag(0, false);
            this.renderEntity(璧室䖼頉啖, 鶲웎頉붃䢶蚳, 鞞躚뗴ᔎ娍ꪕ, 붛嶗啖ኞ츚弻, mc.timer.renderPartialTicks,햖䩉待뼢鱀, this.renderBuffer);
            璧室䖼頉啖.forceFireTicks(筕ꦱ붃眓ꁈ웎);
            璧室䖼頉啖.setFlag(0, 콗햖竁ꈍ酋釒);
            mc.gameSettings.entityShadows = 펊褕室뗴嶗疂;
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
        this.currentRenderMode = RenderMode.泹唟쥦㞈쿨;
        GL11.glDepthFunc(GL_LEQUAL);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.depthMask(false);
        info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager.disableDepth();
    }

    public void renderEntity(final Entity 璧室䖼頉啖, final double n, final double n2, final double n3, final float n4,
                             final MatrixStack 햖䩉待뼢鱀, final IRenderTypeBuffer 㹔뗴㻣騜瀳) {
        mc.worldRenderer.renderManager.renderEntityStatic(璧室䖼頉啖,
                interpolate(n4, 璧室䖼頉啖.lastTickPosX, 璧室䖼頉啖.getPosX()) - n,
                interpolate(n4, 璧室䖼頉啖.lastTickPosY, 璧室䖼頉啖.getPosY()) - n2,
                interpolate(n4, 璧室䖼頉啖.lastTickPosZ, 璧室䖼頉啖.getPosZ()) - n3,
                interpolate(n4, 璧室䖼頉啖.prevRotationYaw, 璧室䖼頉啖.rotationYaw),
                n4,
                햖䩉待뼢鱀,
                㹔뗴㻣騜瀳,
                238);
    }

    public static double interpolate(final double n, final double n2, final double n3) {
        return n2 + n * (n3 - n2);
    }
    public static float interpolate(final float n, final float n2, final float n3) {
        return n2 + n * (n3 - n2);
    }
//    @EventTarget
//    public void 驋펊䩉웨䢿(final 杭捉훔늦杭 杭捉훔늦杭) {
////        if (this.isModuleEnable()) {
//            if (콗뎫鷏Ꮺ놣 != RenderMode.泹唟쥦㞈쿨) {
//                杭捉훔늦杭.웨ꦱ筕쥅哺(false);
//            }
////        }
//    }
    public static boolean cannotRenderName(){
        return currentRenderMode != RenderMode.泹唟쥦㞈쿨;
    };
//    @EventTarget
//    public void 陬㔢藸㦖釒(final 㱙啖햖蚳ぶ 㱙啖햖蚳ぶ) {
////        if (this.isModuleEnable()) {
//            if (콗뎫鷏Ꮺ놣 != RenderMode.泹唟쥦㞈쿨) {
//                if (㱙啖햖蚳ぶ.댠甐㼜揩붛() instanceof 䴂훔顸掬펊) {
//                    㱙啖햖蚳ぶ.鼒鱀哺敤霥堧(true);
//                }
//            }
////        }
//    }

    private void setupRender() {
//        哝鼒ኞ酭낛();
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
        final TextureManager 蒕䴂ᙽ釒卫ꪕ = mc.getTextureManager();
//        mc.蒕䴂ᙽ釒卫ꪕ();
//        蒕䴂ᙽ釒卫ꪕ.bindTexture(RESOURCE_LOCATION_EMPTY);
        mc.gameRenderer.lightmapTexture.enableLightmap();
        GL11.glLightModelfv(2899, new float[] { 0.4f, 0.4f, 0.4f, 1.0f });
        currentRenderMode = RenderMode.泹唟쥦㞈쿨;
        GlStateManager.disableLighting();
    }

}
