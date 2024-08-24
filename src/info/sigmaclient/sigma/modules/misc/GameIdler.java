package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import org.lwjgl.glfw.GLFW;


public class GameIdler extends Module {
    public GameIdler() {
        super("GameIdler", Category.Misc, "Save memory when your focus on other windows.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

     @Override
    public void onDisable() {
        mc.getMainWindow().setFramerateLimit(mc.gameSettings.framerateLimit);
        super.onDisable();
    }

    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        if (GLFW.glfwGetWindowAttrib(mc.getMainWindow().getHandle(), 131073) == 1) {
            mc.getMainWindow().setFramerateLimit(mc.gameSettings.framerateLimit);
        }
        else {
            mc.getMainWindow().setFramerateLimit(5);
        }
       
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }
}
