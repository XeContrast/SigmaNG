package info.sigmaclient.sigma.modules.movement.speeds.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.modules.movement.Speed;
import info.sigmaclient.sigma.modules.movement.speeds.SpeedModule;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.potion.Effect;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class NCPSpeed extends SpeedModule {
    boolean wasSlow = false;
    double moveSpeed;
    public NCPSpeed(Speed speed) {
        super("NCP", "Speed for NCP", speed);
    }

    @Override
    public void onEnable() {
        wasSlow = false;
        moveSpeed = MovementUtils.getBaseMoveSpeed();
        super.onEnable();
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        if (!mc.player.isInWater()) {
            if(wasSlow && MovementUtils.isMoving()){
                moveSpeed -= (moveSpeed - MovementUtils.getBaseMoveSpeed()) * 0.6;
                if(mc.player.isPotionActive(Effect.get(1))){
                    moveSpeed *= 0.9;
                }
                wasSlow = false;
            }
            if (mc.player.onGround && MovementUtils.isMoving()) {
                event.setY(mc.player.getMotion().y = 0.41999998688697815);
                moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.3;
                if(mc.player.isPotionActive(Effect.get(1))){
                    moveSpeed *= 0.9;
                }
                wasSlow = true;
            }
            moveSpeed -= moveSpeed / 80;
            if ((mc.player.movementInput.moveForward != 0.0f || mc.player.movementInput.moveStrafe != 0.0f)) {
                MovementUtils.strafing(event, moveSpeed);
            } else {
                mc.player.getMotion().x = 0;
                mc.player.getMotion().z = 0;
            }
        }
        
    }
}
