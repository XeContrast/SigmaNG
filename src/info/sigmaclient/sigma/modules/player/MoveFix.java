package info.sigmaclient.sigma.modules.player;

import baritone.api.event.events.TickEvent;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.JumpEvent;
import info.sigmaclient.sigma.event.impl.player.StrafeEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.combat.Killaura;
import info.sigmaclient.sigma.utils.player.FixedRotations;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.util.math.MathHelper;

public class MoveFix extends Module {
    public MoveFix() {
        super("MoveFix", Category.Player, "Fixing Move");
        registerValue(mode);
    }

    public static ModeValue mode = new ModeValue("Mode","Silent",new String[] {
            "None",
            "Silent",
            "Strict"
    });

    public static FixedRotations fixedRotations;

    @Override
    public void onEnable() {
        fixedRotations = new FixedRotations(mc.player.rotationYaw, mc.player.rotationPitch);
        super.onEnable();
    }

    @EventTarget
    public static void StrafeEvent(StrafeEvent event) {
        if (defuse())
            return;

        switch (mode.getValue()) {
            case "Strict":
                event.yaw = Killaura.rotations[0];
                break;
            case "Silent":
                event.setYaw(fixedRotations.getYaw());

                float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(fixedRotations.getYaw()) - MathHelper.wrapAngleTo180_float(MovementUtils.getPlayerDirection())) + 22.5F;

                if (diff < 0) {
                    diff = 360 + diff;
                }

                int a = (int) (diff / 45.0);

                float forward = (float) (event.getForward() != 0 ? Math.abs(event.getForward()) : Math.abs(event.getStrafe()));
                float strafe = 0;

                for (int i = 0; i < 8 - a; i++) {
                    float[] dirs = MovementUtils.incrementMoveDirection(forward, strafe);

                    forward = dirs[0];
                    strafe = dirs[1];
                }

                event.setForward(forward);
                event.setStrafe(strafe);
                break;
        }
    }

    @EventTarget
    public static void JumpEvent(JumpEvent event) {
        if (defuse())
            return;

        event.yaw = Killaura.rotations[0];
    }

    @EventTarget
    public static void TickEvent(TickEvent event) {
        if (defuse())
            return;

        if (mode.is("Silent")) {
            float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(fixedRotations.getYaw()) - MathHelper.wrapAngleTo180_float(MovementUtils.getPlayerDirection())) + 22.5F;

            if (diff < 0) {
                diff = 360 + diff;
            }

            int a = (int) (diff / 45.0);

            float forward = mc.player.moveForward != 0 ? Math.abs(mc.player.moveForward) : Math.abs(mc.player.moveStrafing);
            float strafe = 0;

            for (int i = 0; i < 8 - a; i++) {
                float[] dirs = MovementUtils.incrementMoveDirection(forward, strafe);

                forward = dirs[0];
                strafe = dirs[1];
            }

            if(forward < 0.8F) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.player.setSprinting(false);
            }
        }
    }

    public static boolean defuse() {
        return mode.is("None") || (!SigmaNG.getSigmaNG().moduleManager.getModule(Killaura.class).enabled || Killaura.attackTarget == null);
    }
}
