package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class Notifications extends Module {

    public Notifications() {
        super("Notifications", Category.Gui, "new Notifications", true);
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
//        NotificationManager.onRender2D();
        SigmaNG.getSigmaNG().notificationManager.onRender();
    }
    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
    }
}
