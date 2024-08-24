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

public enum 㱙롤웎钘㐈 {
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
    public double ꪕ셴錌ศ딨;
    public double 髾玑䆧疂躚;
    public double ಽ뚔붃室骰;
    public float 值㔢弻펊㔢;
    public float ᔎ唟䢿挐䂷;
    public float 顸酋곻属䴂;
    public BlockRayTraceResult 쇽쟗콗㥇渺;
    public Entity 䩉핇韤䡸쿨;

    private 㱙롤웎钘㐈(final Item 藸䢶㠠뗴㞈, final float 佉圭댠㦖㞈, final float 䁞묙䩜딨錌, final float ꁈ陂䈔值䂷) {
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

    public static 㱙롤웎钘㐈 柿鱀쿨亟(final Item obj) {
        for (final 㱙롤웎钘㐈 㱙롤웎钘㐈 : values()) {
            if (㱙롤웎钘㐈.疂쥡Ꮀ䩜㻣().equals(obj)) {
                return 㱙롤웎钘㐈;
            }
        }
        return null;
    }

    public static float 殢嶗퉧竬鼒ศ(final float n) {
        return MathHelper.sin(n);
    }

    public static float 霥뚔玑䬾頉蒕(float n) {
        return MathHelper.cos(n);
    }

    public ArrayList<Vector3d> 뫤顸鷏㼜瀧() {
        final ArrayList<Vector3d> list = new ArrayList<>();
        final float n = (float) Math.toRadians(mc.player.rotationYaw);
        final float n2 = (float) Math.toRadians(mc.player.rotationPitch);
        final double n72 = mc.player.lastTickPosX + (mc.player.getPosX() - mc.player.lastTickPosX) * mc.timer.renderPartialTicks;
        final double n82 = mc.player.lastTickPosY + (mc.player.getPosY() - mc.player.lastTickPosY) * mc.timer.renderPartialTicks;
        final double n92 = mc.player.lastTickPosZ + (mc.player.getPosZ() - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks;
        this.ꪕ셴錌ศ딨 = n72;
        this.髾玑䆧疂躚 = n82 + mc.player.getHeight() - 0.10000000149011612;
        this.ಽ뚔붃室骰 = n92;
        final float n4 = Math.max(Math.min(20.0f, (72000 - mc.player.getItemInUseCount() + mc.timer.renderPartialTicks)), 0) / 20.0f;
        this.值㔢弻펊㔢 = -殢嶗퉧竬鼒ศ(n) * 霥뚔玑䬾頉蒕(n2) * this.䁞묙䩜딨錌 * n4;
        this.ᔎ唟䢿挐䂷 = -殢嶗퉧竬鼒ศ(n2) * this.䁞묙䩜딨錌 * n4;
        this.顸酋곻属䴂 = 霥뚔玑䬾頉蒕(n) * 霥뚔玑䬾頉蒕(n2) * this.䁞묙䩜딨錌 * n4;
        this.쇽쟗콗㥇渺 = null;
        this.䩉핇韤䡸쿨 = null;
        list.add(new Vector3d(this.ꪕ셴錌ศ딨, this.髾玑䆧疂躚, this.ಽ뚔붃室骰));
        while (this.쇽쟗콗㥇渺 == null) {
            if (this.䩉핇韤䡸쿨 != null) {
                break;
            }
            if (this.髾玑䆧疂躚 <= 0.0) {
                break;
            }
            final Vector3d 䈔㕠㥇ใၝ핇 = new Vector3d(this.ꪕ셴錌ศ딨, this.髾玑䆧疂躚, this.ಽ뚔붃室骰);
            final Vector3d 䈔㕠㥇ใၝ핇2 = new Vector3d(this.ꪕ셴錌ศ딨 + this.值㔢弻펊㔢, this.髾玑䆧疂躚 + this.ᔎ唟䢿挐䂷, this.ಽ뚔붃室骰 + this.顸酋곻属䴂);
            final float n5 = (float) ((this.藸䢶㠠뗴㞈 instanceof BowItem) ? 0.3 : 0.25);
            final List<Entity> 蚳ၝ㕠鱀甐觯 = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, new AxisAlignedBB(this.ꪕ셴錌ศ딨 - n5, this.髾玑䆧疂躚 - n5, this.ಽ뚔붃室骰 - n5, this.ꪕ셴錌ศ딨 + n5, this.髾玑䆧疂躚 + n5, this.ಽ뚔붃室骰 + n5).
                    offset(this.值㔢弻펊㔢, this.ᔎ唟䢿挐䂷, this.顸酋곻属䴂).
                    expand(1.0, 1.0, 1.0));
            if (!蚳ၝ㕠鱀甐觯.isEmpty()) {
                for (Entity entity : 蚳ၝ㕠鱀甐觯) {
                    this.䩉핇韤䡸쿨 = entity;
                }
                break;
            }
            final BlockRayTraceResult 웎웎콵湗竬岋 = mc.world.rayTraceBlocks(new RayTraceContext(䈔㕠㥇ใၝ핇, 䈔㕠㥇ใၝ핇2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, mc.player));
            if (웎웎콵湗竬岋.getType() != RayTraceResult.Type.MISS) {
                this.쇽쟗콗㥇渺 = 웎웎콵湗竬岋;
                this.ꪕ셴錌ศ딨 = this.쇽쟗콗㥇渺.getHitVec().x;
                this.髾玑䆧疂躚 = this.쇽쟗콗㥇渺.getHitVec().y;
                this.ಽ뚔붃室骰 = this.쇽쟗콗㥇渺.getHitVec().z;
                list.add(new Vector3d(this.ꪕ셴錌ศ딨, this.髾玑䆧疂躚, this.ಽ뚔붃室骰));
                break;
            }
            final float n6 = 0.99f;
            final float n7 = 0.05f;
            this.ꪕ셴錌ศ딨 += this.值㔢弻펊㔢;
            this.髾玑䆧疂躚 += this.ᔎ唟䢿挐䂷;
            this.ಽ뚔붃室骰 += this.顸酋곻属䴂;
            list.add(new Vector3d(this.ꪕ셴錌ศ딨, this.髾玑䆧疂躚, this.ಽ뚔붃室骰));
            this.值㔢弻펊㔢 *= n6;
            this.ᔎ唟䢿挐䂷 *= n6;
            this.顸酋곻属䴂 *= n6;
            this.ᔎ唟䢿挐䂷 -= n7;
        }
        return list;
    }
}