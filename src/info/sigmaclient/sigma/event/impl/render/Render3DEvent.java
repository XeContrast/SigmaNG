package info.sigmaclient.sigma.event.impl.render;

import info.sigmaclient.sigma.sigma5.SelfDestructManager;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.event.Event;

import static info.sigmaclient.sigma.modules.render.ESP.shadowESP;

public class Render3DEvent extends Event {
    public float renderTime;
    public Render3DEvent(float renderTime){
        this.renderTime = renderTime;
        if(SelfDestructManager.destruct) return;
        GlStateManager.disableLighting();
    }
}
