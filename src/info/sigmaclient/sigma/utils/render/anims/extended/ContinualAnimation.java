package info.sigmaclient.sigma.utils.render.anims.extended;

import info.sigmaclient.sigma.utils.render.anims.extended.impl.SmoothStepAnimation;
import lombok.Getter;

public class ContinualAnimation {

    private float output, endpoint;

    @Getter
    private Animation animation = new SmoothStepAnimation(0, 0, Direction.BACKWARDS);

    public void animate(float destination, int ms) {
        output = endpoint - animation.getOutput().floatValue();
        endpoint = destination;
        if (output != (endpoint - destination))
            animation = new SmoothStepAnimation(ms, endpoint - output, Direction.BACKWARDS);
    }


    public boolean isDone() {
        return output == endpoint || animation.isDone();
    }

    public float getOutput() {
        output = endpoint - animation.getOutput().floatValue();
        return output;
    }

}
