package info.sigmaclient.sigma.utils.player;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;

public class PlayerUtil {
    public static Block block(final double x, final double y, final double z) {
        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    public static Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return block(mc.player.getPosX() + offsetX, mc.player.getPosY() + offsetY, mc.player.getPosZ() + offsetZ);
    }

    public static boolean isBlockUnder() {
        if (mc.player.getPosY() < 0) return false;
        for (int offset = 0; offset < (int) mc.player.getPosY() + 2; offset += 2) {
            AxisAlignedBB bb = mc.player.getBoundingBox().offset(0, -offset, 0);
            if (!mc.world.getCollisionShapes(mc.player, bb).toList().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isBlockUnder(int distance) {
        for(int y = (int)mc.player.getPosY(); y >= (int)mc.player.getPosY() - distance; --y) {
            if (!(mc.world.getBlockState(new BlockPos(mc.player.getPosX(), y, mc.player.getPosZ())).getBlock() instanceof AirBlock)) {
                return true;
            }
        }

        return false;
    }
    public static float getBlockUnderDist() {
        if (mc.player.getPosY() < 0 || mc.player.isPassenger()) return 0;
        for (int offset = 0; offset < 60; offset += 1) {
            if(!mc.world.getBlockState(new BlockPos(mc.player.getPosX(),mc.player.getPosY() - offset,mc.player.getPosZ())).isAir()){
                return offset;
            }
        }
        return 0;
    }
}
