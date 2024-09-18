package info.sigmaclient.sigma.sigma5.killaura;

import info.sigmaclient.sigma.utils.player.Rotation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import static info.sigmaclient.sigma.minimap.XaeroMinimap.mc;


public class NCPRotation {

    public static Vector3d calculatePosition(final Entity Entity) {
        return calculatePosition(Entity.getBoundingBox());
    }

    public static Vector3d calculatePosition(final Entity Entity,final double top) {
        return calculatePosition(Entity.getBoundingBox(),top);
    }


//    public static boolean 䆧哝掬훔睬(final Vector3d Vector3d) {
//        final 㔢弻콵唟㨳曞 웎웎콵湗竬岋 = mc.world.rayTraceBlocks(new RayTraceContext(new Vector3d(mc.player.getPosX(), mc.player.getPosY() + mc.player.getEyeHeight(), mc.player.getPosZ()), Vector3d, 㕠鞞蓳뼢㐖㥇.䣓㼜ኞ웎堧酭, 曞䢶罡䂷㦖㦖.ၝኞ샱欫䩉卫, mc.player));
//        final boolean b = 웎웎콵湗竬岋.浣褕괠殢䈔蒕() == 酋곻姮卒敤䴂.펊郝啖䩉汌觯 || 웎웎콵湗竬岋.浣褕괠殢䈔蒕() == 酋곻姮卒敤䴂.䁞뎫佉霥釒낛;
//        mc.姮䢶牰㝛硙䩜.錌钘蒕骰聛属(웎웎콵湗竬岋.䢶츚醧敤괠셴()).딨挐湗驋圭낛();
//        return b;
//    }


    public static Vector3d calculatePosition(final AxisAlignedBB boundingBox) {
        // Get the center x-coordinate of the bounding box
        final double centerX = boundingBox.getCenter().x;
        // Get the minimum y-coordinate of the bounding box
        final double minY = boundingBox.minY;
        // Get the center z-coordinate of the bounding box
        final double centerZ = boundingBox.getCenter().z;

        // Calculate 95% of the height of the bounding box
        final double height = (boundingBox.maxY - minY) * 0.95;
        // Calculate 95% of the width of the bounding box
        final double width = (boundingBox.maxX - boundingBox.minX) * 0.95;
        // Calculate 95% of the depth of the bounding box
        final double depth = (boundingBox.maxZ - boundingBox.minZ) * 0.95;

        // Create a new Vector3d object with adjusted coordinates
        return new Vector3d(
                // Clamp the player's x-coordinate within the adjusted width range
                Math.max(centerX - width / 2.0, Math.min(centerX + width / 2.0, mc.player.getPosX())),
                // Clamp the player's y-coordinate within the adjusted height range
                Math.max(minY, Math.min(minY + height, mc.player.getPosY() + mc.player.getEyeHeight())),
                // Clamp the player's z-coordinate within the adjusted depth range
                Math.max(centerZ - depth / 2.0, Math.min(centerZ + depth / 2.0, mc.player.getPosZ()))
        );
    }

    public static Vector3d calculatePosition(final AxisAlignedBB boundingBox,final double top) {
        // Get the center x-coordinate of the bounding box
        final double centerX = boundingBox.getCenter().x;
        // Get the minimum y-coordinate of the bounding box
        final double minY = boundingBox.minY;
        // Get the center z-coordinate of the bounding box
        final double centerZ = boundingBox.getCenter().z;

        // Calculate 95% of the height of the bounding box
        final double height = (boundingBox.maxY - minY);
        // Calculate 95% of the width of the bounding box
        final double width = (boundingBox.maxX - boundingBox.minX) * 0.95;
        // Calculate 95% of the depth of the bounding box
        final double depth = (boundingBox.maxZ - boundingBox.minZ) * 0.95;

        // Create a new Vector3d object with adjusted coordinates
        return new Vector3d(
                // Clamp the player's x-coordinate within the adjusted width range
                Math.max(centerX - width / 2.0, Math.min(centerX + width / 2.0, mc.player.getPosX())),
                // Clamp the player's y-coordinate within the adjusted height range
                Math.max(minY, Math.min(minY + height * top, mc.player.getPosY() + mc.player.getEyeHeight())),
                // Clamp the player's z-coordinate within the adjusted depth range
                Math.max(centerZ - depth / 2.0, Math.min(centerZ + depth / 2.0, mc.player.getPosZ()))
        );
    }


    public static Vector3d nearestPoint(final AxisAlignedBB boundingBox) {
        // 获取玩家的位置
        double playerX = mc.player.getPosX();
        double playerY = mc.player.getPosY() + mc.player.getEyeHeight();
        double playerZ = mc.player.getPosZ();

        // 获取碰撞箱的中心和边界
        double centerX = boundingBox.getCenter().x;
        double minY = boundingBox.minY;
        double centerZ = boundingBox.getCenter().z;

        // 计算碰撞箱的尺寸
        double height = boundingBox.maxY - boundingBox.minY;
        double width = boundingBox.maxX - boundingBox.minX;
        double depth = boundingBox.maxZ - boundingBox.minZ;

        // 计算最近点
        double closestX = MathHelper.clamp(playerX, centerX - width / 2.0, centerX + width / 2.0);
        double closestY = MathHelper.clamp(playerY, minY, minY + height);
        double closestZ = MathHelper.clamp(playerZ, centerZ - depth / 2.0, centerZ + depth / 2.0);

        return new Vector3d(closestX, closestY, closestZ);
    }
    public static Vector3d nearestPointBody(final AxisAlignedBB boundingBox) {
        // Get the center x-coordinate of the bounding box
        final double centerX = boundingBox.getCenter().x;
        // Get the minimum y-coordinate of the bounding box
        final double minY = boundingBox.minY;
        // Get the center z-coordinate of the bounding box
        final double centerZ = boundingBox.getCenter().z;

        // Use the full height of the bounding box
        final double height = (boundingBox.maxY - minY);
        // Use the full width of the bounding box
        final double width = (boundingBox.maxX - boundingBox.minX);
        // Use the full depth of the bounding box
        final double depth = (boundingBox.maxZ - boundingBox.minZ);

        // Create a new Vector3d object with adjusted coordinates
        return new Vector3d(
                // Clamp the player's x-coordinate within the full width range
                Math.max(centerX - width / 2.0, Math.min(centerX + width / 2.0, mc.player.getPosX())),
                // Clamp the player's y-coordinate within the full height range
                Math.max(minY, Math.min(minY + height, mc.player.getPosY())),
                // Clamp the player's z-coordinate within the full depth range
                Math.max(centerZ - depth / 2.0, Math.min(centerZ + depth / 2.0, mc.player.getPosZ()))
        );
    }
    public static Rotation calculateRotation(final Vector3d 䈔㕠㥇ใၝ핇) {
        final float[] calculateRotationAngles = calculateRotationAngles(mc.player.getPositionVec().add(0.0, mc.player.getEyeHeight(), 0.0), 䈔㕠㥇ใၝ핇);
        return new Rotation(calculateRotationAngles[0], calculateRotationAngles[1]);
    }
    public static float[] calculateRotationAngles(final Vector3d 䈔㕠㥇ใၝ핇, final Vector3d 䈔㕠㥇ใၝ핇2) {
        final double x = 䈔㕠㥇ใၝ핇2.x - 䈔㕠㥇ใၝ핇.x;
        final double y = 䈔㕠㥇ใၝ핇2.z - 䈔㕠㥇ใၝ핇.z;
        return new float[] { 渺뗴䢶좯鶲(0.0f, (float)(Math.atan2(y, x) * 180.0 / 3.141592653589793) - 90.0f, 360.0f), 渺뗴䢶좯鶲(mc.player.rotationPitch, (float)(-(Math.atan2(䈔㕠㥇ใၝ핇2.y - 䈔㕠㥇ใၝ핇.y, 鶲佉侃陂늦酋(x * x + y * y)) * 180.0 / 3.141592653589793)), 360.0f) };
    }
    public static float 鶲佉侃陂늦酋(final double a) {
        return (float)Math.sqrt(a);
    }
    public static float 哝㻣낛䴂㕠㢸(final float n) {
        float n2 = n % 360.0f;
        if (n2 >= 180.0f) {
            n2 -= 360.0f;
        }
        if (n2 < -180.0f) {
            n2 += 360.0f;
        }
        return n2;
    }

    public static float 渺뗴䢶좯鶲(final float n, final float n2, final float n3) {
        float 哝㻣낛䴂㕠㢸 = 哝㻣낛䴂㕠㢸(n2 - n);
        if (哝㻣낛䴂㕠㢸 > n3) {
            哝㻣낛䴂㕠㢸 = n3;
        }
        if (哝㻣낛䴂㕠㢸 < -n3) {
            哝㻣낛䴂㕠㢸 = -n3;
        }
        return n + 哝㻣낛䴂㕠㢸;
    }
    public static Rotation NCPRotation(final Entity Entity) {
        final Vector3d 浣罡낛姮姮 = calculatePosition(Entity);
        return calculateRotation(浣罡낛姮姮);
    }

    public static Rotation NCPRotation(final Entity Entity, final double top) {
        final Vector3d 浣罡낛姮姮 = calculatePosition(Entity,top);
        return calculateRotation(浣罡낛姮姮);
    }

    public static Rotation NCPRotation(final AxisAlignedBB Entity) {
        final Vector3d 浣罡낛姮姮 = calculatePosition(Entity);
        return calculateRotation(浣罡낛姮姮);
    }
}
