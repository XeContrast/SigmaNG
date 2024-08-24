// 
// Decompiled by Procyon v0.6.0
// 

package info.sigmaclient.sigma.sigma5.utils;


import java.util.Date;

public class Sigma5AnimationUtil
{
    public enum AnimState {
        ANIMING,
        SLEEPING;
        public boolean is(){
            return this == ANIMING;
        }
    }
    public int ใᏪ묙姮哝;
    public int 硙㼜陬䕦묙;
    public AnimState isAnim;
    public Date 䈔鶲쿨韤鱀;
    public Date 핇罡钘硙䎰;
    
    public Sigma5AnimationUtil(final int n, final int n2) {
        this(n, n2, AnimState.ANIMING);
    }
    
    public Sigma5AnimationUtil(final int time, final int 硙㼜陬䕦묙, final AnimState 蛊퉧쬷湗좯) {
        this.isAnim = 蛊퉧쬷湗좯.ANIMING;
        this.ใᏪ묙姮哝 = time;
        this.硙㼜陬䕦묙 = 硙㼜陬䕦묙;
        this.䈔鶲쿨韤鱀 = new Date();
        this.핇罡钘硙䎰 = new Date();
        this.animTo(蛊퉧쬷湗좯);
    }
    public Sigma5AnimationUtil copy(){
        Sigma5AnimationUtil util = new Sigma5AnimationUtil(this.ใᏪ묙姮哝, 硙㼜陬䕦묙, isAnim);
        util.䈔鶲쿨韤鱀 = 䈔鶲쿨韤鱀;
        util.핇罡钘硙䎰 = 핇罡钘硙䎰;
        return util;
    }
    
    public int 䩉堍綋殢曞唟() {
        return this.ใᏪ묙姮哝;
    }

    public void animTo(boolean animing) {
        AnimState 픓鷏㞈㕠㔢 = animing ? AnimState.ANIMING : AnimState.SLEEPING;
        if (this.isAnim == 픓鷏㞈㕠㔢) {
            return;
        }
        if(픓鷏㞈㕠㔢.is()) {
            this.䈔鶲쿨韤鱀 = new Date(new Date().getTime() - (long) (this.getAnim() * this.ใᏪ묙姮哝));
        }else {
            this.핇罡钘硙䎰 = new Date(new Date().getTime() - (long) ((1.0f - this.getAnim()) * this.硙㼜陬䕦묙));
        }
        this.isAnim = 픓鷏㞈㕠㔢;
    }
    public void animTo(AnimState 픓鷏㞈㕠㔢) {
        if (this.isAnim == 픓鷏㞈㕠㔢) {
            return;
        }
        if(픓鷏㞈㕠㔢.is()) {
            this.䈔鶲쿨韤鱀 = new Date(new Date().getTime() - (long) (this.getAnim() * this.ใᏪ묙姮哝));
        }else {
            this.핇罡钘硙䎰 = new Date(new Date().getTime() - (long) ((1.0f - this.getAnim()) * this.硙㼜陬䕦묙));
        }
        this.isAnim = 픓鷏㞈㕠㔢;
    }
    
    public void 呓셴塱欫罡㞈(final float n, boolean too) {
        if(too) {
            this.䈔鶲쿨韤鱀 = new Date(new Date().getTime() - (long) (n * this.ใᏪ묙姮哝));
        }else {
            this.핇罡钘硙䎰 = new Date(new Date().getTime() - (long) ((1.0f - n) * this.硙㼜陬䕦묙));
        }
    }
    public void reset(){
        this.䈔鶲쿨韤鱀 = new Date();
        this.핇罡钘硙䎰 = new Date();
    }
    public void reset(AnimState animState){
        this.䈔鶲쿨韤鱀 = new Date();
        this.핇罡钘硙䎰 = new Date();
        isAnim = animState;
    }
    public float getAnim() {
        // ignore?
        if (this.isAnim != AnimState.ANIMING) {
            return 1.0f - Math.min(this.硙㼜陬䕦묙, new Date().getTime() - this.핇罡钘硙䎰.getTime()) / (float)this.硙㼜陬䕦묙;
        }
        return Math.min(this.ใᏪ묙姮哝, new Date().getTime() - this.䈔鶲쿨韤鱀.getTime()) / (float)this.ใᏪ묙姮哝;
    }
    
    public static float 陬玑挐Ꮺ堍Ꮀ(final Date date, final Date date2, final float a, final float a2) {
        return Math.max(0.0f, Math.min(1.0f, Math.min(a, (float)(new Date().getTime() - ((date != null) ? date.getTime() : new Date().getTime()))) / a * (1.0f - Math.min(a2, (float)(new Date().getTime() - ((date2 != null) ? date2.getTime() : new Date().getTime()))) / a2)));
    }
    
    public static float 陬玑挐Ꮺ堍Ꮀ(final Date date, final float a) {
        return Math.max(0.0f, Math.min(1.0f, Math.min(a, (float)(new Date().getTime() - ((date != null) ? date.getTime() : new Date().getTime()))) / a));
    }
    
    public static float 陬玑挐Ꮺ堍Ꮀ(final Date date, final Date date2, final float n) {
        return 陬玑挐Ꮺ堍Ꮀ(date, date2, n, n);
    }
    
    public static boolean 㝛ศ陂錌㠠騜(final Date date, final float n) {
        return date != null && new Date().getTime() - date.getTime() > n;
    }
    public void setAnim(AnimState anim){
        this.isAnim = anim;
    }
    public float getValue(){
        return this.getAnim() * this.硙㼜陬䕦묙;
    }
}
