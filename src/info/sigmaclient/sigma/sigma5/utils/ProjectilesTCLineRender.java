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
    髾콵䎰葫甐(Items.BOW, 0.0f, 3.0f, 0.0f),
    㱙햠髾瀳놣(Items.SNOWBALL, 0.0f, 1.875f, 0.0f),
    眓훔顸샱呓(Items.EGG, 0.0f, 1.875f, 0.0f),
    㱙曞䡸粤䴂(Items.ENDER_PEARL, 0.0f, 1.875f, 0.0f),
    ศ鏟藸㱙掬(Items.SPLASH_POTION, 0.0f, 0.5f, 0.0f),
    捉埙㠠弻䕦(Items.EXPERIENCE_BOTTLE, 0.0f, 0.6f, 0.0f),
    꿩랾ꪕ霥㹔(Items.TRIDENT, 0.0f, 2.5f, 0.0f);

    private Item 藸䢶㠠뗴㞈;
    private float 佉圭댠㦖㞈;
    private float 䁞묙䩜딨錌;
    private float ꁈ陂䈔值䂷;
    public double hitX;
    public double hitY;
    public double hitZ;
    public float 值㔢弻펊㔢;
    public float ᔎ唟䢿挐䂷;
    public float 顸酋곻属䴂;
    public BlockRayTraceResult hitResult;
    public Entity hitEntity;

    private ProjectilesTCLineRender(final Item 藸䢶㠠뗴㞈, final float 佉圭댠㦖㞈, final float 䁞묙䩜딨錌, final float ꁈ陂䈔值䂷) {
        this.藸䢶㠠뗴㞈 = 藸䢶㠠뗴㞈;
        this.佉圭댠㦖㞈 = 佉圭댠㦖㞈;
        this.䁞묙䩜딨錌 = 䁞묙䩜딨錌;
        this.ꁈ陂䈔值䂷 = ꁈ陂䈔值䂷;
    }

    public float 罡묙塱ꪕ待() {
        return this.佉圭댠㦖㞈;
    }

    public float 汌ᢻ㠠䬾붃() {
        return this.ꁈ陂䈔值䂷;
    }

    public Item 疂쥡Ꮀ䩜㻣() {
        return this.藸䢶㠠뗴㞈;
    }

    public static ProjectilesTCLineRender lineRender(final Item obj) {
        for (final ProjectilesTCLineRender ProjectilesTCLineRender : values()) {
            if (ProjectilesTCLineRender.疂쥡Ꮀ䩜㻣().equals(obj)) {
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
        this.值㔢弻펊㔢 = -calculateSin(n) * calculateCos(n2) * this.䁞묙䩜딨錌 * n4;
        this.ᔎ唟䢿挐䂷 = -calculateSin(n2) * this.䁞묙䩜딨錌 * n4;
        this.顸酋곻属䴂 = calculateCos(n) * calculateCos(n2) * this.䁞묙䩜딨錌 * n4;
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
            final Vector3d 䈔㕠㥇ใၝ핇 = new Vector3d(this.hitX, this.hitY, this.hitZ);
            final Vector3d 䈔㕠㥇ใၝ핇2 = new Vector3d(this.hitX + this.值㔢弻펊㔢, this.hitY + this.ᔎ唟䢿挐䂷, this.hitZ + this.顸酋곻属䴂);
            final float n5 = (float) ((this.藸䢶㠠뗴㞈 instanceof BowItem) ? 0.3 : 0.25);
            final List<Entity> 蚳ၝ㕠鱀甐觯 = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, new AxisAlignedBB(this.hitX - n5, this.hitY - n5, this.hitZ - n5, this.hitX + n5, this.hitY + n5, this.hitZ + n5).
                    offset(this.值㔢弻펊㔢, this.ᔎ唟䢿挐䂷, this.顸酋곻属䴂).
                    expand(1.0, 1.0, 1.0));
            if (!蚳ၝ㕠鱀甐觯.isEmpty()) {
                for (Entity entity : 蚳ၝ㕠鱀甐觯) {
                    this.hitEntity = entity;
                }
                break;
            }
            final BlockRayTraceResult 웎웎콵湗竬岋 = mc.world.rayTraceBlocks(new RayTraceContext(䈔㕠㥇ใၝ핇, 䈔㕠㥇ใၝ핇2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, mc.player));
            if (웎웎콵湗竬岋.getType() != RayTraceResult.Type.MISS) {
                this.hitResult = 웎웎콵湗竬岋;
                this.hitX = this.hitResult.getHitVec().x;
                this.hitY = this.hitResult.getHitVec().y;
                this.hitZ = this.hitResult.getHitVec().z;
                list.add(new Vector3d(this.hitX, this.hitY, this.hitZ));
                break;
            }
            final float n6 = 0.99f;
            final float n7 = 0.05f;
            this.hitX += this.值㔢弻펊㔢;
            this.hitY += this.ᔎ唟䢿挐䂷;
            this.hitZ += this.顸酋곻属䴂;
            list.add(new Vector3d(this.hitX, this.hitY, this.hitZ));
            this.值㔢弻펊㔢 *= n6;
            this.ᔎ唟䢿挐䂷 *= n6;
            this.顸酋곻属䴂 *= n6;
            this.ᔎ唟䢿挐䂷 -= n7;
        }
        return list;
    }
}