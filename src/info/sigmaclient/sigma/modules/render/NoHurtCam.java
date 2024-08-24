package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class NoHurtCam extends Module {
    public NoHurtCam() {
        super("NoHurtCam", Category.Render, "No hurt camera render.");
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }
}
