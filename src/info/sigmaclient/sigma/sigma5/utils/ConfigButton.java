package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.gui.font.JelloFontRenderer;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;

public class ConfigButton extends Button {
    private static String[] stringArray;
    public Custom5AnimationUtil animationUtil;
    public JelloFontRenderer fontRenderer;
    public String str;
    public ConfigButton(final int n, final int n2, final int n3, final int n4, final String buttonText, final IPressable onPress, JelloFontRenderer f) {
        super(n, n2, n3, n4, new StringTextComponent(buttonText), onPress);
        this.fontRenderer = f;
        this.animationUtil = new Custom5AnimationUtil((float) (fontRenderer.getStringWidth(buttonText)/2) * 5,0);

        this.str = buttonText;
    }
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.active && this.visible)
        {
            if (this.isValidClickButton(button))
            {
                boolean flag = this.clicked(mouseX, mouseY);

                if (flag)
                {
//                    this.playDownSound(Minecraft.getMinecraft().getSoundHandler());
                    this.onClick(mouseX, mouseY);
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }
    public void myRender(int mouseX, int mouseY, Color c, float alpha) {
        final int colorRGB = c.getRGB();
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int stringWidth = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        float n = 1;
        this.isHovered = mouseX >= n2 - stringWidth / 2 && mouseY >= n3 && mouseX < n2 + stringWidth / 2 && mouseY < n3 + n4;
        this.animationUtil.animTo(this.isHovered ? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        final float n5 = (float) Math.pow(this.animationUtil.getAnim(), 3.0);
        Sigma5DrawText.drawString(this.fontRenderer, (float) n2 - stringWidth / 2, (float) n3 + 5, str, applyColor(colorRGB, n * calculateAlpha(colorRGB) * alpha));
        RenderUtils.drawRect(n2 - stringWidth / 2 * n5, (float) (n3 + n4), n2 + stringWidth / 2 * n5, (float) (n3 + n4 + 1), applyColor(colorRGB, n * calculateAlpha(colorRGB) * alpha));
//        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        final int colorRGB = -65794;
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int stringWidth = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        float n = 1;
        this.isHovered = mouseX >= n2 - stringWidth / 2 && mouseY >= n3 && mouseX < n2 + stringWidth / 2 && mouseY < n3 + n4;
        this.animationUtil.animTo(this.isHovered ? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        final float n5 = (float) Math.pow(this.animationUtil.getAnim(), 3.0);
        Sigma5DrawText.drawString(this.fontRenderer, (float) n2 - stringWidth / 2, (float) n3 + 5, str, applyColor(colorRGB, n * calculateAlpha(colorRGB)));
        RenderUtils.drawRect(n2 - stringWidth / 2 * n5, (float) (n3 + n4), n2 + stringWidth / 2 * n5, (float) (n3 + n4 + 1), applyColor(colorRGB, n * calculateAlpha(colorRGB)));
//        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int stringWidth = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        return mouseX >= n2 - stringWidth / 2 && mouseY >= n3 && mouseX < n2 + stringWidth / 2 && mouseY < n3 + n4;
    }

    public static float calculateAlpha(final int n) {
        return (n >> 24 & 0xFF) / 255.0f;
    }
}
