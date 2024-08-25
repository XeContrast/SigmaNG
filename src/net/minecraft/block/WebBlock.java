package net.minecraft.block;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.modules.movement.NoWeb;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static info.sigmaclient.sigma.modules.Module.mc;

public class WebBlock extends Block
{
    public WebBlock(AbstractBlock.Properties properties)
    {
        super(properties);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if(!SigmaNG.getSigmaNG().moduleManager.getModule(NoWeb.class).enabled) {
            entityIn.setMotionMultiplier(state, new Vector3d(0.25D, 0.05F, 0.25D));
        }else{
            if (SigmaNG.getSigmaNG().moduleManager.getModule(NoWeb.class).enabled) {
                switch (((NoWeb) SigmaNG.getSigmaNG().moduleManager.getModule(NoWeb.class)).type.getValue()) {
                    case "Intave":
                        slow(entityIn,state);
                        if (mc.player.isInWater()) {
                            if (MovementUtils.isMoving() && mc.player.moveStrafing == 0.0) {
                                if (mc.player.onGround) {
                                    if (mc.player.ticksExisted % 3 == 0) {
                                        MovementUtils.strafing(0.734f);
                                    } else {
                                        mc.player.jump();
                                        MovementUtils.strafing(0.346f);
                                    }
                                }
                            }
                        }
                        break;
                    case "NCP":
                        entityIn.setMotionMultiplier(state, new Vector3d(0.98f, 0.05F, 0.98f));
                        break;
                    case "FastFall":
                        entityIn.setMotionMultiplier(state, new Vector3d(0.25D, 0.05F, 0.25D));
                        if (mc.player.onGround) mc.player.jump();
                        if (mc.player.getMotion().y > 0f) {
                            mc.player.getMotion().y -= mc.player.getMotion().y * 2;
                        }
                        break;
                    case "Vanilla":
                        break;
                }
            }
        }
    }
    public void slow(Entity entityIn,BlockState state) {
        entityIn.setMotionMultiplier(state, new Vector3d(0.25D, 0.05F, 0.25D));
    }
}
