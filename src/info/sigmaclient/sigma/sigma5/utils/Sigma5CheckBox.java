package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.utils.render.ColorUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;
import static info.sigmaclient.sigma.sigma5.utils.SigmaRenderUtils.drawRoundedRect;
import static info.sigmaclient.sigma.modules.render.NameTags.blendColors;

public class Sigma5CheckBox extends Button
{
    private static String[] checkboxStates;
    public Sigma5AnimationUtil animationUtil;
    public boolean enabled = false;
    public Sigma5CheckBox(final String s, final int n, final int n2, final int n3, final int n4, IPressable d) {
        super(n, n2, n3, n4, new StringTextComponent(s), d);
        this.animationUtil = new Sigma5AnimationUtil(70, 90);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        if(b){
            enabled = !enabled;
        }
        return b;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.animationUtil.animTo(!this.enabled ? Sigma5AnimationUtil.AnimState.ANIMING : Sigma5AnimationUtil.AnimState.SLEEPING);

        float ex = 0;
        drawRoundedRect((float)this.x - ex, (float)this.y - ex, (float)this.width + ex * 2, (float)this.height + ex * 2, 11f, applyColor(-4144960, (this.isHovered() ? 0.6f : 0.43f) * this.animationUtil.getAnim() * 1));
        final float n2 = (1.0f - this.animationUtil.getAnim()) * 1;
        drawRoundedRect((float)this.x - ex, (float)this.y - ex, (float)this.width + ex * 2, (float)this.height + ex * 2, 11f, applyColor(blendColors(-14047489, -16711423, this.isHovered() ? 0.9f : 1.0f), n2));
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + width / 2), (float)(y + height / 2), 0.0f);
        GL11.glScalef(1.5f - 0.5f * n2, 1.5f - 0.5f * n2, 0.0f);
        GL11.glTranslatef((float)(-x - width / 2), (float)(-y - height / 2), 0.0f);
        RenderUtils.drawTextureLocationZoom((float)this.x + this.width / 4f, (float)this.y + this.height / 4f, (float)this.width / 2f, (float)this.height / 2f, "check" , ColorUtils.reAlpha(-65794, n2));
        GL11.glPopMatrix();
//        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    }

}
