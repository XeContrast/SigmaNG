package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.gui.hud.notification.NotificationManager;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class RearView extends Module {
    public RearView() {
        super("RearView", Category.Gui, "View rear your body.");
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        NotificationManager.notify("RearView", "itz WIP, so I disable it.", 4000);
        super.onEnable();
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }
}