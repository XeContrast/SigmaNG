package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.sigma5.utils.Vector2fWrapper;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;
import net.minecraft.util.math.vector.Vector2f;

import java.awt.*;

import static info.sigmaclient.sigma.modules.Category.Render;
import static info.sigmaclient.sigma.modules.render.NameTags.getColorFromNameTag;


public class DVDSimulator extends Module {
    private static String[] moduleNames;
    public Vector2fWrapper positionWrapper;
    public float posX;
    public float posY;
    public float velocityX;
    public float velocityY;
    public Vector2f dimensions;
    public int color;

    public DVDSimulator() {
        super(DVDSimulator.moduleNames[0], Render, DVDSimulator.moduleNames[1]);
        this.positionWrapper = new Vector2fWrapper(1.0f, 1.0f);
        this.posY = 0.0f;
        this.velocityX = 1.0f;
        this.velocityY = 1.0f;
        this.dimensions = new Vector2f(201 / 2f, 90 / 2f);
        this.color = 0;
        this.updateColor();
    }

    @Override
    public void onEnable() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.posX = (float) ((sr.getScaledWidth() - this.dimensions.x) * Math.random());
        this.posY = (float) ((sr.getScaledHeight() - this.dimensions.y) * Math.random());
        super.onEnable();
    }

    @EventTarget
    public void onRenderEvent(RenderEvent event) {
        ScaledResolution sr = new ScaledResolution(mc);
        final int screenHeight = sr.getScaledHeight();
        final int screenWidth = sr.getScaledWidth();
        final int n = 2;
        if (this.posY > n) {
            if (this.posY + this.dimensions.y > screenHeight) {
                this.velocityY = -1.0f;
                this.updateColor();
            }
        } else {
            this.velocityY = 1.0f;
            this.updateColor();
        }
        if (this.posX > n) {
            if (this.posX + this.dimensions.x > screenWidth) {
                this.velocityX = -1.0f;
                this.updateColor();
            }
        } else {
            this.velocityX = 1.0f;
            this.updateColor();
        }
        this.posX += this.velocityX * n;
        this.posY += this.velocityY * n;
        RenderUtils.drawTextureLocationZoom(this.posX, this.posY, (float) this.dimensions.x, (float) this.dimensions.y, "dvd", new Color(getColorFromNameTag(this.color, 0.8f)));
        
    }

    private void updateColor() {
        this.color = Color.getHSBColor((float) Math.random(), 0.6f, 1.0f).getRGB();
    }

    static {
        DVDSimulator.moduleNames = new String[]{"DVD Simulator", "wtf"};
    }
}
