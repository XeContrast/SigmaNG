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
    public int animationDuration;
    public int sleepDuration;
    public AnimState isAnim;
    public Date animationStartTime;
    public Date sleepStartTime;
    
    public Sigma5AnimationUtil(final int n, final int n2) {
        this(n, n2, AnimState.ANIMING);
    }
    
    public Sigma5AnimationUtil(final int time, final int sleepDuration, final AnimState initialState) {
        this.isAnim = initialState.ANIMING;
        this.animationDuration = time;
        this.sleepDuration = sleepDuration;
        this.animationStartTime = new Date();
        this.sleepStartTime = new Date();
        this.animTo(initialState);
    }
    public Sigma5AnimationUtil copy(){
        Sigma5AnimationUtil util = new Sigma5AnimationUtil(this.animationDuration, sleepDuration, isAnim);
        util.animationStartTime = animationStartTime;
        util.sleepStartTime = sleepStartTime;
        return util;
    }
    
    public int 䩉堍綋殢曞唟() {
        return this.animationDuration;
    }

    public void animTo(boolean animing) {
        AnimState newState = animing ? AnimState.ANIMING : AnimState.SLEEPING;
        if (this.isAnim == newState) {
            return;
        }
        if(newState.is()) {
            this.animationStartTime = new Date(new Date().getTime() - (long) (this.getAnim() * this.animationDuration));
        }else {
            this.sleepStartTime = new Date(new Date().getTime() - (long) ((1.0f - this.getAnim()) * this.sleepDuration));
        }
        this.isAnim = newState;
    }
    public void animTo(AnimState newState) {
        if (this.isAnim == newState) {
            return;
        }
        if(newState.is()) {
            this.animationStartTime = new Date(new Date().getTime() - (long) (this.getAnim() * this.animationDuration));
        }else {
            this.sleepStartTime = new Date(new Date().getTime() - (long) ((1.0f - this.getAnim()) * this.sleepDuration));
        }
        this.isAnim = newState;
    }
    
    public void adjustAnimation(final float n, boolean too) {
        if(too) {
            this.animationStartTime = new Date(new Date().getTime() - (long) (n * this.animationDuration));
        }else {
            this.sleepStartTime = new Date(new Date().getTime() - (long) ((1.0f - n) * this.sleepDuration));
        }
    }
    public void reset(){
        this.animationStartTime = new Date();
        this.sleepStartTime = new Date();
    }
    public void reset(AnimState animState){
        this.animationStartTime = new Date();
        this.sleepStartTime = new Date();
        isAnim = animState;
    }
    public float getAnim() {
        // ignore?
        if (this.isAnim != AnimState.ANIMING) {
            return 1.0f - Math.min(this.sleepDuration, new Date().getTime() - this.sleepStartTime.getTime()) / (float)this.sleepDuration;
        }
        return Math.min(this.animationDuration, new Date().getTime() - this.animationStartTime.getTime()) / (float)this.animationDuration;
    }
    
    public static float calculateProgress(final Date date, final Date date2, final float a, final float a2) {
        return Math.max(0.0f, Math.min(1.0f, Math.min(a, (float)(new Date().getTime() - ((date != null) ? date.getTime() : new Date().getTime()))) / a * (1.0f - Math.min(a2, (float)(new Date().getTime() - ((date2 != null) ? date2.getTime() : new Date().getTime()))) / a2)));
    }
    
    public static float calculateProgress(final Date date, final float a) {
        return Math.max(0.0f, Math.min(1.0f, Math.min(a, (float)(new Date().getTime() - ((date != null) ? date.getTime() : new Date().getTime()))) / a));
    }
    
    public static float calculateProgress(final Date date, final Date date2, final float n) {
        return calculateProgress(date, date2, n, n);
    }
    
    public static boolean isExpired(final Date date, final float n) {
        return date != null && new Date().getTime() - date.getTime() > n;
    }
    public void setAnim(AnimState anim){
        this.isAnim = anim;
    }
    public float getValue(){
        return this.getAnim() * this.sleepDuration;
    }
}
