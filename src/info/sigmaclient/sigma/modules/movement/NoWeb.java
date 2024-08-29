package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class NoWeb extends Module {
    public ModeValue type = new ModeValue("Type", "NCP", new String[]{"NCP", "Vanilla", "Intave", "FastFall"});

    public NoWeb() {
        super("NoWeb", Category.Movement, "Anti web slow");
        registerValue(type);
    }
    //这写的真能绕吗哥们

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    public void onUpdateEvent(MotionEvent event) {
        if (event.isPre()) {
            this.suffix = type.getValue();
        }
    }
}
