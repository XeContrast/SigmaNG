package info.sigmaclient.sigma.sigma5.utils;

import lombok.Getter;

public class Custom5AnimationUtil {
    private float value;
    @Getter
    private float defaultValue;
    private float endValue;
    private float lastValue;

    public AnimState isAnim;

    public Custom5AnimationUtil(float end, float value) {
        this.defaultValue = value;
        this.value = value;
        this.endValue = end;

        isAnim = (end > value)? AnimState.ANIMING: AnimState.BACKING;
    }
    public Custom5AnimationUtil(float end, float value , AnimState state) {
        this.defaultValue = value;
        this.value = value;
        this.endValue = end;

        isAnim = state;
    }
    public static float calculateCompensation(float target, float current, double speed) {
        float diff = current - target;
        double max = speed * 3;
        if (diff > speed) {
            current -= max - 0.005;
            if (current < target) {
                current = target;
            }
        } else if (diff < -speed) {
            current += max + 0.005;
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }
        return current;
    }
    public void interpolate(float targetY, double speed) {
        if(speed == 0) return;
        lastValue = value;
        double deltaY = Math.max((Math.abs(targetY - value) * 0.35f) / (10 / speed), 0.05);
        if(speed <= 0){
            deltaY = -speed;
        }
        value = calculateCompensation(targetY, value, deltaY);
        if(Math.abs(targetY - value) <= 0.05){
            value = targetY;
        }
    }

    public void anim(float speed, boolean back){
        if(back) {
            // X: alpha Y : scale
            this.interpolate(this.defaultValue, speed);
        }else{
            // X: alpha Y : scale
            this.interpolate(this.endValue, (speed));
        }
    }

    public float getValueNoTrans() {
        return value;
    }

//    public float getAnim() {
////        System.out.println(value + "|" + lastValue);
//        return smoothTrans(value, lastValue);
//    }

    public float getAnim(){
        return this.value/this.endValue;
    }

    public void setLValue(float y) {
        this.lastValue = y;
    }
    public void setValue(float y) {
        if(y <= endValue) {
            this.lastValue = y;
            this.value = y;
        }else {
            this.lastValue = endValue;
            this.value = endValue;
        }
    }

    public void animTo(AnimState animState){
        this.isAnim = animState;
        if(isAnim == AnimState.SLEEPING)return;
        this.anim(1, isAnim == AnimState.BACKING);
    }

    public void animTo(float speed ,AnimState animState){
        this.isAnim = animState;
        if(isAnim == AnimState.SLEEPING)return;
        this.anim(speed, isAnim == AnimState.BACKING);
    }

    public void setAnim(AnimState isAnim) {
        this.isAnim = isAnim;
    }

    public void reset(){
        this.setValue(defaultValue);
        this.isAnim = (this.endValue > this.value)? AnimState.ANIMING: AnimState.BACKING;
    }

    public void reset(AnimState state){
        this.setValue(defaultValue);
        this.isAnim = state;
    }


    public float getValue() {
        return value;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public float getEndValue() {
        return endValue;
    }

    public enum AnimState{
        SLEEPING,ANIMING,BACKING;
        public boolean is(){
            return this != SLEEPING;
        }
    }
}
