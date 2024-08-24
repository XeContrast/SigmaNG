package info.sigmaclient.sigma.event.impl.render;

import info.sigmaclient.sigma.event.Event;

public class RenderShaderEvent extends Event {
    public float renderTime;
    public RenderShaderEvent(float renderTime){
        this.renderTime = renderTime;
    }
}
