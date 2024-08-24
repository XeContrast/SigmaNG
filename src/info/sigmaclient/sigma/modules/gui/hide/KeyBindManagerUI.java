package info.sigmaclient.sigma.modules.gui.hide;

import info.sigmaclient.sigma.gui.keybindmanager.KeyBindManager;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.Minecraft;

public class KeyBindManagerUI extends Module {
    public static KeyBindManager keyGui = new KeyBindManager();
    public static boolean isEnableFirst = false;

    public KeyBindManagerUI() {
        super("KeyBindManager", Category.Gui, "WHAT? you find this???", 0);
        this.SpecialGUI = true;
    }

    @Override
    public void onEnable() {

        this.enabled = false;;
        isEnableFirst = true;
        Minecraft.getInstance().displayGuiScreen(KeyBindManagerUI.keyGui);
        super.onEnable();
    }
}
