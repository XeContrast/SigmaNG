package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class BrainFreeze extends Module {
    public BrainFreeze() {
        super("BrainFreeze", Category.Gui, "Freeze your brain");
    }

   @EventTarget
    public void onRenderEvent(RenderEvent event)
    {
        
    }
}
