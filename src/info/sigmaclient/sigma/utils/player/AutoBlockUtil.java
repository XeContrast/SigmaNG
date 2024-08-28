package info.sigmaclient.sigma.utils.player;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.event.impl.player.MouseClickEvent;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public class AutoBlockUtil {
    public static void BlockWithInteract(Entity entity)
    {
        for (Hand hand : Hand.values())
        {
            ItemStack itemstack = mc.player.getHeldItem(hand);

            Vector3d vector3d = entity.getEyePosition(Module.mc.timer.renderPartialTicks);

            Vector3d vector3d1 = entity.getLookCustom(1.0f, mc.player.lastReportedYaw, mc.player.lastReportedPitch);
            Vector3d vector3d2 = vector3d.add(vector3d1.x * (double) 4.5, vector3d1.y * (double) 4.5, vector3d1.z * (double) 4.5);
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vector3d1.scale(4.5)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2,
                    axisalignedbb, (p_lambda$getMouseOver$0_0_) ->
                            !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith() && p_lambda$getMouseOver$0_0_.equals(entity), 4.5 * 4.5);

            if (entityraytraceresult != null)
            {
                if (entityraytraceresult.getType() == RayTraceResult.Type.ENTITY) {


                    ActionResultType actionresulttype = mc.playerController.interactWithEntity(mc.player, entity, entityraytraceresult, hand);

                    if (!actionresulttype.isSuccessOrConsume()) {
                        actionresulttype = mc.playerController.interactWithEntity(mc.player, entity, hand);
                    }

                    if (actionresulttype.isSuccessOrConsume()) {
                        return;
                    }
                }
            }

            if (!itemstack.isEmpty())
            {
                mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(hand));
            }
        }
    }

    public static void Block()
    {
        for (Hand hand : Hand.values())
        {
            ItemStack itemstack = mc.player.getHeldItem(hand);
            if (!itemstack.isEmpty())
            {
                mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(hand));
            }
        }
    }

    public static void unBlock()
    {
        mc.playerController.syncCurrentPlayItem();
        mc.getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
    }
}
