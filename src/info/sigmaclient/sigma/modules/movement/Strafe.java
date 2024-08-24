package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.event.impl.player.JumpEvent;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.process.impl.player.BlinkProcess;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.util.math.MathHelper;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class Strafe extends Module {
    public Strafe() {
        super("Strafe", Category.Movement,"Strafe in air");
    }


    @EventTarget
    public void onMoveEvent(MoveEvent event){
        MovementUtils.strafing(event,MovementUtils.getSpeed());
    }
}
