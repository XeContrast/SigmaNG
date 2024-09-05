package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ColorValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import info.sigmaclient.sigma.utils.player.ScaffoldUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;
import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.drawOutlinedBox;


public class HoleFinder extends Module {
    public NumberValue range = new NumberValue("Range", 3, 0, 6, NumberValue.NUMBER_TYPE.INT);
    public BooleanValue autoHole = new BooleanValue("AutoHole", true);
    public ColorValue colorValue = new ColorValue("Color", -1);
    public HoleFinder() {
        super("HoleFinder", Category.World, "ESP all hole.");
     registerValue(range);
     registerValue(autoHole);
     registerValue(colorValue);
    }
    ScaffoldUtils.BlockCache currentPos = null;
    ScaffoldUtils.BlockCache lastCurrentPos = null;
    ArrayList<BlockPos> holes = new ArrayList<>();
    float sb = 0;
    int delays = 0;
    public boolean isOkBlock(BlockPos block2){
        Block block11 = mc.world.getBlockState(block2.add(0,-1,0)).getBlock();
        Block block112 = mc.world.getBlockState(block2.add(0,1,0)).getBlock();
        Block block = mc.world.getBlockState(block2).getBlock();
        Block block1 = mc.world.getBlockState(block2.add(1,0,0)).getBlock();
        Block block5 = mc.world.getBlockState(block2.add(-1,0,0)).getBlock();
        Block block3 = mc.world.getBlockState(block2.add(0,0,1)).getBlock();
        Block block4 = mc.world.getBlockState(block2.add(0,0,-1)).getBlock();
        if(mc.player.getDistance(block2.getX() + 0.5, block2.getY() + 0.5, block2.getZ() + 0.5) - 0.5f > range.getValue().floatValue() + 0.5f) return false;
        return block11.getDefaultState().isSolid() && block instanceof AirBlock && block112 instanceof AirBlock && block1.getDefaultState().isSolid() && block3.getDefaultState().isSolid() && block4.getDefaultState().isSolid() && block5.getDefaultState().isSolid();
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        GlStateManager.disableLighting();
        if (this.holes != null) {
            for(BlockPos currentPos : holes) {
                final int 霥瀳놣㠠釒 = applyColor(colorValue.getColorInt(), 0.1f);
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                RenderUtils.renderPos r = RenderUtils.getRenderPos();
                final double n = currentPos.getX() - r.renderPosX;
                final double n2 = currentPos.getY() - r.renderPosY;
                final double n3 = currentPos.getZ() - r.renderPosZ;
                drawOutlinedBox(new AxisAlignedBB(n, n2, n3, n + 1, n2 + 1f, n3 + 1), 霥瀳놣㠠釒);
                GL11.glEnable(2929);
                GL11.glPopMatrix();
            }
        }
        
    }

    public ArrayList<BlockPos> getBlockCache(BlockPos pos, int range) {
        ArrayList<BlockPos> poses = new ArrayList<>();
        for(int x = -range; x < range; x ++){
            for(int y = -range; y < range; y ++){
                for(int z = -range; z < range; z ++){
                    BlockPos pos1 = pos.add(x,y,z);
                    if (isOkBlock(pos1))
                        poses.add(pos1);
                }
            }
        }
        return poses;
    }
  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            // LOL
            if(mc.player.ticksExisted % 5 == 0)
                holes = getBlockCache(new BlockPos(mc.player.getPositionVector()) ,range.getValue().intValue());
        }
       
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        mc.timer.setTimerSpeed(1f);
        if (this.holes != null) {
            for (BlockPos currentPos : holes) {
                if(mc.player.fallDistance > 0){
                    if(new BlockPos(mc.player.getPositionVec()).add(0,-0.5,0).equals(currentPos)){
                        mc.timer.setTimerSpeed(2);
                        MovementUtils.strafing2(MovementUtils.getBaseMoveSpeed() * 0.9f,RotationUtils.toRotation(new Vector3d(currentPos.getX() + 0.5, currentPos.getY(), currentPos.getZ() + 0.5)).getYaw(), event);
                    }
                    if(new BlockPos(mc.player.getPositionVec()).equals(currentPos)){
                        mc.timer.setTimerSpeed(1f);
                    }
                }
            }
        }
        
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        mc.timer.setTimerSpeed(1f);
        holes.clear();
        super.onDisable();
    }
}
