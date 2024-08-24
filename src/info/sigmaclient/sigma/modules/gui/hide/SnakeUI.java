package info.sigmaclient.sigma.modules.gui.hide;

import info.sigmaclient.sigma.gui.games.snake.SnakeGame;
import info.sigmaclient.sigma.gui.keybindmanager.KeyBindManager;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.Minecraft;

public class SnakeUI extends Module {
    public static SnakeGame keyGui = new SnakeGame();
    public static boolean isEnableFirst = false;

    public SnakeUI() {
        super("Snake", Category.Gui, "WHAT? you find this???", 0);
        this.SpecialGUI = true;
    }

    @Override
    public void onEnable() {

        this.enabled = false;;
        isEnableFirst = true;
        Minecraft.getInstance().displayGuiScreen(SnakeUI.keyGui);
        super.onEnable();
    }
}
