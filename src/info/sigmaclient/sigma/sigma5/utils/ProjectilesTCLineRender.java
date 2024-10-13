package info.sigmaclient.sigma.sigma5.utils;

import net.minecraft.entity.Entity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;

public enum ProjectilesTCLineRender {
    BOW(Items.BOW, 0.0f, 3.0f, 0.0f),
    SNOWBALL(Items.SNOWBALL, 0.0f, 1.875f, 0.0f),
    EGG(Items.EGG, 0.0f, 1.875f, 0.0f),
    ENDER_PEARL(Items.ENDER_PEARL, 0.0f, 1.875f, 0.0f),
    SPLASH_POTION(Items.SPLASH_POTION, 0.0f, 0.5f, 0.0f),
    EXPERIENCE_BOTTLE(Items.EXPERIENCE_BOTTLE, 0.0f, 0.6f, 0.0f),
    TRIDENT(Items.TRIDENT, 0.0f, 2.5f, 0.0f);

    private Item item;
    private float startVelocity;
    private float velocityMultiplier;
    private float gravity;
    public double hitX;
    public double hitY;
    public double hitZ;
    public float motionX;
    public float motionY;
    public float motionZ;
    public BlockRayTraceResult hitResult;
    public Entity hitEntity;

    private ProjectilesTCLineRender(final Item item, final float startVelocity, final float velocityMultiplier, final float gravity) {
        this.item = item;
        this.startVelocity = startVelocity;
        this.velocityMultiplier = velocityMultiplier;
        this.gravity = gravity;
    }

    public float getStartVelocity() {
        return this.startVelocity;
    }

    public float getGravity() {
        return this.gravity;
    }

    public Item getItem() {
        return this.item;
    }

    public static ProjectilesTCLineRender lineRender(final Item obj) {
        for (final ProjectilesTCLineRender ProjectilesTCLineRender : values()) {
            if (ProjectilesTCLineRender.getItem().equals(obj)) {
                return ProjectilesTCLineRender;
            }
        }
        return null;
    }

    public static float calculateSin(final float n) {
        return MathHelper.sin(n);
    }

    public static float calculateCos(float n) {
        return MathHelper.cos(n);
    }

    public ArrayList<Vector3d> projectilePath() {
        final ArrayList<Vector3d> list = new ArrayList<>();
        final float n = (float) Math.toRadians(mc.player.rotationYaw);
        final float n2 = (float) Math.toRadians(mc.player.rotationPitch);
        final double n72 = mc.player.lastTickPosX + (mc.player.getPosX() - mc.player.lastTickPosX) * mc.timer.renderPartialTicks;
        final double n82 = mc.player.lastTickPosY + (mc.player.getPosY() - mc.player.lastTickPosY) * mc.timer.renderPartialTicks;
        final double n92 = mc.player.lastTickPosZ + (mc.player.getPosZ() - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks;
        this.hitX = n72;
        this.hitY = n82 + mc.player.getHeight() - 0.10000000149011612;
        this.hitZ = n92;
        final float n4 = Math.max(Math.min(20.0f, (72000 - mc.player.getItemInUseCount() + mc.timer.renderPartialTicks)), 0) / 20.0f;
        this.motionX = -calculateSin(n) * calculateCos(n2) * this.velocityMultiplier * n4;
        this.motionY = -calculateSin(n2) * this.velocityMultiplier * n4;
        this.motionZ = calculateCos(n) * calculateCos(n2) * this.velocityMultiplier * n4;
        this.hitResult = null;
        this.hitEntity = null;
        list.add(new Vector3d(this.hitX, this.hitY, this.hitZ));
        while (this.hitResult == null) {
            if (this.hitEntity != null) {
                break;
            }
            if (this.hitY <= 0.0) {
                break;
            }
            final Vector3d currentPosition = new Vector3d(this.hitX, this.hitY, this.hitZ);
            final Vector3d nextPosition = new Vector3d(this.hitX + this.motionX, this.hitY + this.motionY, this.hitZ + this.motionZ);
            final float n5 = (float) ((this.item instanceof BowItem) ? 0.3 : 0.25);
            final List<Entity> entitiesInPath = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, new AxisAlignedBB(this.hitX - n5, this.hitY - n5, this.hitZ - n5, this.hitX + n5, this.hitY + n5, this.hitZ + n5).
                    offset(this.motionX, this.motionY, this.motionZ).
                    expand(1.0, 1.0, 1.0));
            if (!entitiesInPath.isEmpty()) {
                for (Entity entity : entitiesInPath) {
                    this.hitEntity = entity;
                }
                break;
            }
            final BlockRayTraceResult blockHitResult = mc.world.rayTraceBlocks(new RayTraceContext(currentPosition, nextPosition, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, mc.player));
            if (blockHitResult.getType() != RayTraceResult.Type.MISS) {
                this.hitResult = blockHitResult;
                this.hitX = this.hitResult.getHitVec().x;
                this.hitY = this.hitResult.getHitVec().y;
                this.hitZ = this.hitResult.getHitVec().z;
                list.add(new Vector3d(this.hitX, this.hitY, this.hitZ));
                break;
            }
            final float n6 = 0.99f;
            final float n7 = 0.05f;
            this.hitX += this.motionX;
            this.hitY += this.motionY;
            this.hitZ += this.motionZ;
            list.add(new Vector3d(this.hitX, this.hitY, this.hitZ));
            this.motionX *= n6;
            this.motionY *= n6;
            this.motionZ *= n6;
            this.motionY -= n7;
        }
        return list;
    }
}