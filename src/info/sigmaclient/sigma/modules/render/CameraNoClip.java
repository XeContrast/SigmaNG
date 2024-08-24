package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class CameraNoClip extends Module {
    public CameraNoClip() {
        super("CameraNoClip", Category.Render, "No clip camera");
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }
}
