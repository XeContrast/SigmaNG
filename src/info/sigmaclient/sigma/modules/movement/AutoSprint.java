package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.KeyEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class AutoSprint extends Module {
    public static BooleanValue keepSprint = new BooleanValue("Keep Sprint", false);
    public static BooleanValue noHurt = new BooleanValue("NoHurt", false);
    public static BooleanValue noJumpDelay = new BooleanValue("No Jump Delay", false);
    public AutoSprint() {
        super("AutoSprint", Category.Movement, "Auto sprint for yo");
        registerValue(keepSprint);
        registerValue(noHurt);
        registerValue(noJumpDelay);
    }
    public static boolean sprint = true;

    @EventTarget
    public void onUpdateEvent(MotionEvent event){
        mc.gameSettings.keyBindSprint.pressed = sprint;
        sprint = true;
       
    }

    @EventTarget
    public void onKeyEvent(KeyEvent event) {
    }
}
