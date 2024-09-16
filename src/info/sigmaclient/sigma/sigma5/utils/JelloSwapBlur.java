package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class JelloSwapBlur {
    static String jello = "{\"targets\":[\"jelloswap\",\"jello\"],\"passes\":[{\"name\":\"blur\",\"intarget\":\"minecraft:main\",\"outtarget\":\"jelloswap\",\"uniforms\":[{\"name\":\"BlurDir\",\"values\":[1,0]},{\"name\":\"Radius\",\"values\":[20]}]},{\"name\":\"blur\",\"intarget\":\"jelloswap\",\"outtarget\":\"jello\",\"uniforms\":[{\"name\":\"BlurDir\",\"values\":[0,1]},{\"name\":\"Radius\",\"values\":[20]}]}]}";
    public static void applyBlurEffect() {
        GL11.glPushMatrix();
        BlurUtils.blur(35f);
        RenderSystem.enableTexture();
        GL11.glPopMatrix();
        Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
    }
}
