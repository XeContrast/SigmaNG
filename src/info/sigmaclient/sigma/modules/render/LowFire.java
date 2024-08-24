package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class LowFire extends Module {
    public LowFire() {
        super("LowFire", Category.Render, "Fire makes me blind.");
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }
}
