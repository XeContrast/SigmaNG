package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ColorValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import info.sigmaclient.sigma.utils.player.ScaffoldUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import org.lwjgl.opengl.GL11;

import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.ࡅ揩柿괠竁頉;
import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.霥瀳놣㠠釒;
import top.fl0wowp4rty.phantomshield.annotations.Native;

public class Nuker extends Module {
    public ModeValue modeValue = new ModeValue("Type", "Bed", new String[]{"Bed"});
    public NumberValue range = new NumberValue("Range", 3, 0, 6, NumberValue.NUMBER_TYPE.INT);
    public NumberValue delay = new NumberValue("Delay", 0, 0, 1, NumberValue.NUMBER_TYPE.FLOAT);
    public BooleanValue rot = new BooleanValue("Rotations", false);
    public BooleanValue oneHit = new BooleanValue("OneHit", false);
    public ColorValue colorValue = new ColorValue("Color", -1);
    public Nuker() {
        super("Nuker", Category.World, "Break blocks.");
     registerValue(modeValue);
     registerValue(range);
     registerValue(delay);
     registerValue(rot);
     registerValue(oneHit);
     registerValue(colorValue);
    }
    ScaffoldUtils.BlockCache currentPos = null;
    ScaffoldUtils.BlockCache lastCurrentPos = null;
    public int tick = 0;
    float sb = 0;
    int delays = 0;
    public boolean isOkBlock(BlockPos block2){
        Block block = mc.world.getBlockState(block2).getBlock();
        if(mc.player.getDistance(block2.getX() + 0.5, block2.getY() + 0.5, block2.getZ() + 0.5) - 0.5f > range.getValue().floatValue() + 0.5f) return false;
        switch (modeValue.getValue()){
            case "All":
                return !(block instanceof AirBlock) && !(block instanceof FlowingFluidBlock);
            case "Bed":
                return block instanceof BedBlock || block.getTranslationKey().contains("_wool") || block.getTranslationKey().contains("_wood") || block.getTranslationKey().contains("_stone");
            case "Egg":
                return block instanceof DragonEggBlock;
            case "Cake":
                return block instanceof CakeBlock;
            case "Tnt":
                return block instanceof TNTBlock;
            case "Ore":
                return block instanceof OreBlock;
            case "Ore&Block":
                return block instanceof OreBlock || block == Blocks.DIAMOND_BLOCK || block == Blocks.IRON_BLOCK || block == Blocks.COAL_BLOCK || block == Blocks.GOLD_BLOCK || block == Blocks.EMERALD_BLOCK || block == Blocks.NETHERITE_BLOCK;
        }
        return false;
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        if (this.currentPos != null) {
            final int 霥瀳놣㠠釒 = 霥瀳놣㠠釒(colorValue.getColorInt(), 0.4f);
            GL11.glPushMatrix();
            GL11.glDisable(2929);
            RenderUtils.renderPos r = RenderUtils.getRenderPos();
            final double n = currentPos.getPosition().getX() - r.renderPosX;
            final double n2 = currentPos.getPosition().getY() - r.renderPosY;
            final double n3 = currentPos.getPosition().getZ() - r.renderPosZ;
            ࡅ揩柿괠竁頉(new AxisAlignedBB(n, n2, n3, n + 1, n2 + 1, n3 + 1), 霥瀳놣㠠釒);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
        }
        
    }

    public ScaffoldUtils.BlockCache getBlockCache(BlockPos pos, int range) {

        double bed_y = 0,bed_x = 0,bed_z = 0;
        double max_tick = 0;
        for(int x = -range; x < range; x ++){
            for(int y = -range; y < range; y ++){
                for(int z = -range; z < range; z ++){
                    BlockPos pos1 = pos.add(x,y,z);
                    if (isOkBlock(pos1)) {
                        if (mc.world.getBlockState(pos1).getBlock() instanceof BedBlock) {
                            bed_y = pos1.getY();
                            bed_x = pos1.getX();
                            bed_z = pos1.getZ();
                            max_tick = (int) mc.player.getDistance(bed_x, bed_y, bed_z);
                            if (max_tick - tick < 1) {
                                return new ScaffoldUtils.BlockCache(pos1, Direction.UP);
                            }
                        } else {
                            double dis = max_tick - tick + mc.player.getDistance(bed_x, bed_y, bed_z);
                            if (pos1.getY() != bed_y || Math.sqrt(pos1.distanceSq(new Vector3i(bed_x, bed_y, bed_z))) > Math.max(1,dis))
                                continue;

                            return new ScaffoldUtils.BlockCache(pos1, Direction.UP);
                        }

                    }
                }
            }
        }
        return null;
    }
    public void stop(){
        sb = 0;
        delays = 0;
        if(lastCurrentPos != null) {
            if(!oneHit.isEnable())
            mc.playerController.sendDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, lastCurrentPos.getPosition(), lastCurrentPos.getFacing());
            lastCurrentPos = null;
            tick++;

        }
    }
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            // LOL
            ScaffoldUtils.BlockCache blockCache = getBlockCache(new BlockPos(mc.player.getPositionVector()) ,range.getValue().intValue());
            boolean stop = false;
            currentPos = blockCache;
            if(blockCache == null){
                if(currentPos != null){
                    currentPos = null;
                    stop = true;
                }
            }
            if(currentPos != null && mc.player.getDistance(currentPos.getPosition().getX() + 0.5, currentPos.getPosition().getY() + 0.5, currentPos.getPosition().getZ() + 0.5) - 0.5f > range.getValue().floatValue() + 0.5f){
                stop = true;

                currentPos = null;
            }
            if(delays > 0){
                delays --;
                currentPos = null;
                stop = true;
            }
            if(stop || currentPos == null){
                stop();
                return;
            }
            BlockPos currentPos = this.currentPos.getPosition();
            if(rot.isEnable()){
                float[] rots = RotationUtils.scaffoldRots(currentPos.getX() + 0.5, currentPos.getY() + 0.5, currentPos.getZ() + 0.5, mc.player.prevRotationYaw, mc.player.prevRotationPitch, 180, 180, false);
                event.yaw = rots[0];
                event.pitch = rots[1];
            }
            if(lastCurrentPos == null) {
                if(oneHit.isEnable()) {
                    mc.player.swingArm(Hand.MAIN_HAND);
                    mc.playerController.clickBlock(currentPos, blockCache.getFacing());
                }else
                {
                    mc.player.swingArm(Hand.MAIN_HAND);
                    mc.playerController.sendDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, currentPos, blockCache.getFacing());
                }
                lastCurrentPos = this.currentPos;
            }else {

                if (oneHit.isEnable()) {
                } else {
                    mc.player.swingArm(Hand.MAIN_HAND);
                }
            }
            Block block = mc.world.getBlockState(blockCache.getPosition()).getBlock();
            sb += block.getPlayerRelativeBlockHardness(block.getDefaultState(), mc.player, mc.world, blockCache.getPosition());
            if(sb >= 1.0F){
                stop();
                delays = (int) (delay.getValue().floatValue() * 10);
            }


//            lastCurrentPos = currentPos;
        }
       
    }

    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        if(currentPos != null && mc.currentScreen == null){
            BlockState block = mc.world.getBlockState(currentPos.getPosition());
            int bestSlot = -1;
            float digSpeed = 1;
            for(int i = 0;i <= 8;i ++){
                ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                float d = itemStack.getDestroySpeed(block);
                if(d > digSpeed){
                    bestSlot = i;
                    digSpeed = d;
                }
            }
            if(bestSlot != -1){

                mc.player.inventory.currentItem = bestSlot;
            }
        }
    }
    @Override
    public void onEnable() {
        currentPos = null;
        tick = 0;
        sb = 0;
        delays = 0;
        stop();
        super.onEnable();
    }

     @Override
    public void onDisable() {
        if(currentPos != null){
            stop();
            lastCurrentPos = null;
            currentPos = null;
        }
        super.onDisable();
    }
}
