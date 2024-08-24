package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;

public class CapeLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    public CapeLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> playerModelIn)
    {
        super(playerModelIn);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity LivingEntityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (LivingEntityIn.hasPlayerInfo() && !LivingEntityIn.isInvisible() && LivingEntityIn.isWearing(PlayerModelPart.CAPE) && LivingEntityIn.getLocationCape() != null)
        {
            ItemStack itemstack = LivingEntityIn.getItemStackFromSlot(EquipmentSlotType.CHEST);

            if (itemstack.getItem() != Items.ELYTRA)
            {
                matrixStackIn.push();
                matrixStackIn.translate(0.0D, 0.0D, 0.125D);
                double d0 = MathHelper.lerp((double)partialTicks, LivingEntityIn.prevChasingPosX, LivingEntityIn.chasingPosX) - MathHelper.lerp((double)partialTicks, LivingEntityIn.prevPosX, LivingEntityIn.getPosX());
                double d1 = MathHelper.lerp((double)partialTicks, LivingEntityIn.prevChasingPosY, LivingEntityIn.chasingPosY) - MathHelper.lerp((double)partialTicks, LivingEntityIn.prevPosY, LivingEntityIn.getPosY());
                double d2 = MathHelper.lerp((double)partialTicks, LivingEntityIn.prevChasingPosZ, LivingEntityIn.chasingPosZ) - MathHelper.lerp((double)partialTicks, LivingEntityIn.prevPosZ, LivingEntityIn.getPosZ());

                float f = LivingEntityIn.prevRenderYawOffset + (LivingEntityIn.renderYawOffset - LivingEntityIn.prevRenderYawOffset);
                double d3 = (double)MathHelper.sin(f * ((float)Math.PI / 180F));
                double d4 = (double)(-MathHelper.cos(f * ((float)Math.PI / 180F)));
                float f1 = (float)d1 * 10.0F;
                f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
                float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
                float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                f3 = MathHelper.clamp(f3, -20.0F, 20.0F);

                if (f2 < 0.0F)
                {
                    f2 = 0.0F;
                }

                if (f2 > 165.0F)
                {
                    f2 = 165.0F;
                }

                if (f1 < -5.0F)
                {
                    f1 = -5.0F;
                }

                float f4 = MathHelper.lerp(partialTicks, LivingEntityIn.prevCameraYaw, LivingEntityIn.cameraYaw);
                f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, LivingEntityIn.prevDistanceWalkedModified, LivingEntityIn.distanceWalkedModified) * 6.0F) * 32.0F * f4;

                if (LivingEntityIn.isCrouching())
                {
                    f1 += 25.0F;
                }

                float f5 = Config.getAverageFrameTimeSec() * 20.0F;
                f5 = Config.limit(f5, 0.02F, 1.0F);
                LivingEntityIn.capeRotateX = MathHelper.lerp(f5, LivingEntityIn.capeRotateX, 6.0F + f2 / 2.0F + f1);
                LivingEntityIn.capeRotateZ = MathHelper.lerp(f5, LivingEntityIn.capeRotateZ, f3 / 2.0F);
                LivingEntityIn.capeRotateY = MathHelper.lerp(f5, LivingEntityIn.capeRotateY, 180.0F - f3 / 2.0F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(LivingEntityIn.capeRotateX));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(LivingEntityIn.capeRotateZ));
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(LivingEntityIn.capeRotateY));
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(LivingEntityIn.getLocationCape()));
                this.getEntityModel().renderCape(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
                matrixStackIn.pop();
            }
        }
    }
}
