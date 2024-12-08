package info.sigmaclient.sigma.utils.player;

import lombok.Getter;
import net.minecraft.client.Minecraft;

@Getter
public class FixedRotations {

    private float yaw, pitch;
    private float lastYaw, lastPitch;

    public FixedRotations(float startingYaw, float startingPitch) {
        lastYaw = yaw = startingYaw;
        lastPitch = pitch = startingPitch;
    }

    public void updateRotations(float requestedYaw, float requestedPitch) {
        lastYaw = yaw;
        lastPitch = pitch;

        float gcd = getGCD();

        float yawDiff = (requestedYaw - yaw);
        float pitchDiff = (requestedPitch - pitch);

        float fixedYawDiff = yawDiff - (yawDiff % gcd);
        float fixedPitchDiff = pitchDiff - (pitchDiff % gcd);

        yaw += fixedYawDiff;
        pitch += fixedPitchDiff;

        pitch = Math.max(-90, Math.min(90, pitch));
    }

    public static float getGCD() {
        return (float) (Math.pow(Minecraft.getInstance().gameSettings.mouseSensitivity * 0.6 + 0.2, 3) * 1.2);
    }

}
