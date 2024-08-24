package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.TimerUtil;
import info.sigmaclient.sigma.utils.player.Rotation;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.item.EnderCrystalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;
import java.util.Random;


public class AutoExplosion extends Module {
    public NumberValue range = new NumberValue("Range", 5, 0, 8, NumberValue.NUMBER_TYPE.FLOAT);
    public NumberValue mcps = new NumberValue("MinCPS", 10, 0, 20, NumberValue.NUMBER_TYPE.INT){
        @Override
        public void onSetValue() {
            if(this.getValue().intValue() > cps.getValue().intValue()){
                this.pureSetValue(cps.getValue().intValue());
            }
            super.onSetValue();
        }
    };
    public NumberValue cps = new NumberValue("MaxCPS", 12, 0, 20, NumberValue.NUMBER_TYPE.INT){
        @Override
        public void onSetValue() {
            if(this.getValue().intValue() < mcps.getValue().intValue()){
                this.pureSetValue(mcps.getValue().intValue());
            }
            super.onSetValue();
        }
    };


    long calc_cps;
    TimerUtil timerUtil = new TimerUtil();
    public AutoExplosion() {
        super("AutoExplosion", Category.World, "Auto crystal legit");
     registerValue(range);
     registerValue(mcps);
     registerValue(cps);
    }

    public boolean isCrystal(ItemStack itemStack){
        return itemStack.getItem() instanceof EnderCrystalItem || itemStack.getTranslationKey().contains("crystal");
    }
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            if(!isCrystal(mc.player.getHeldItemMainhand()) && !isCrystal(mc.player.getHeldItemOffhand()) && !(mc.player.getHeldItemMainhand().getTranslationKey().contains("anchor")) && !(mc.player.getHeldItemMainhand().getTranslationKey().contains("glow") && mc.player.getHeldItemMainhand().getTranslationKey().contains("stone"))) return;
            if(!mc.gameSettings.keyBindUseItem.pressed) return;
            if(!timerUtil.hasTimeElapsed(calc_cps)) return;
            int cpss = (int) (mcps.getValue().intValue() + (cps.getValue().intValue() - mcps.getValue().intValue() + 1) * new Random().nextDouble());
            if(cpss == 0) return;
            timerUtil.reset();
            calc_cps = 1000 / cpss - 10;
            RayTraceResult result = mc.player.pick(range.getValue().floatValue(), 1.0F);
            if(result == null || result.getType() != RayTraceResult.Type.BLOCK) return;
                BlockPos bb = ((BlockRayTraceResult) result).getPos();
                List<EnderCrystalEntity> entityEnderCrystals = mc.world.getEntitiesWithinAABB(
                        EnderCrystalEntity.class, new AxisAlignedBB(
                                bb.getX(),
                                bb.getY(),
                                bb.getZ(),
                                bb.getX() + 1,
                                bb.getY() + 3,
                                bb.getZ() + 1
                        )
                );
                if (!entityEnderCrystals.isEmpty()) {
                    EnderCrystalEntity entityEnderCrystal = entityEnderCrystals.get(0);
                    if(!(entityEnderCrystal instanceof EnderCrystalEntity)) return;
                    Rotation calcRotation = RotationUtils.NCPRotation(entityEnderCrystal);
                    event.pitch = calcRotation.getPitch();
                    if(entityEnderCrystal.getDistance(mc.player) <= 3.0) {
                        mc.playerController.attackEntity(mc.player, entityEnderCrystal);
                        mc.player.swingArm(Hand.MAIN_HAND);
                        return;
                    }
                }else{
                    if (mc.playerController.processRightClickBlock(mc.player, mc.world, bb, Direction.UP, new Vector3d(bb), Hand.MAIN_HAND) == ActionResultType.SUCCESS) {
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }
                }
        }
       
    }

}
