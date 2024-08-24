package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.gui.font.JelloFontRenderer;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.霥瀳놣㠠釒;

public class ConfigButton extends Button {
    private static String[] 醧쿨浣ᔎ甐;
    public Custom5AnimationUtil 㐈뫤뼢嘖竬;
    public JelloFontRenderer fontRenderer;
    public String str;
    public ConfigButton(final int n, final int n2, final int n3, final int n4, final String 鼒鄡嘖ꁈ柿, final IPressable 樽塱掬鼒曞竁, JelloFontRenderer f) {
        super(n, n2, n3, n4, new StringTextComponent(鼒鄡嘖ꁈ柿), 樽塱掬鼒曞竁);
        this.fontRenderer = f;
        this.㐈뫤뼢嘖竬 = new Custom5AnimationUtil((float) (fontRenderer.getStringWidth(鼒鄡嘖ꁈ柿)/2) * 5,0);

        this.str = 鼒鄡嘖ꁈ柿;
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
        final int ใ퉧뫤ใ츚属 = c.getRGB();
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int 汌蚳洝陬괠ꪕ = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        float n = 1;
        this.isHovered = mouseX >= n2 - 汌蚳洝陬괠ꪕ / 2 && mouseY >= n3 && mouseX < n2 + 汌蚳洝陬괠ꪕ / 2 && mouseY < n3 + n4;
        this.㐈뫤뼢嘖竬.animTo(this.isHovered ? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        final float n5 = (float) Math.pow(this.㐈뫤뼢嘖竬.getAnim(), 3.0);
        Sigma5DrawText.drawString(this.fontRenderer, (float) n2 - 汌蚳洝陬괠ꪕ / 2, (float) n3 + 5, str, 霥瀳놣㠠釒(ใ퉧뫤ใ츚属, n * 䩜뵯哝䄟픓(ใ퉧뫤ใ츚属) * alpha));
        RenderUtils.drawRect(n2 - 汌蚳洝陬괠ꪕ / 2 * n5, (float) (n3 + n4), n2 + 汌蚳洝陬괠ꪕ / 2 * n5, (float) (n3 + n4 + 1), 霥瀳놣㠠釒(ใ퉧뫤ใ츚属, n * 䩜뵯哝䄟픓(ใ퉧뫤ใ츚属) * alpha));
//        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        final int ใ퉧뫤ใ츚属 = -65794;
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int 汌蚳洝陬괠ꪕ = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        float n = 1;
        this.isHovered = mouseX >= n2 - 汌蚳洝陬괠ꪕ / 2 && mouseY >= n3 && mouseX < n2 + 汌蚳洝陬괠ꪕ / 2 && mouseY < n3 + n4;
        this.㐈뫤뼢嘖竬.animTo(this.isHovered ? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        final float n5 = (float) Math.pow(this.㐈뫤뼢嘖竬.getAnim(), 3.0);
        Sigma5DrawText.drawString(this.fontRenderer, (float) n2 - 汌蚳洝陬괠ꪕ / 2, (float) n3 + 5, str, 霥瀳놣㠠釒(ใ퉧뫤ใ츚属, n * 䩜뵯哝䄟픓(ใ퉧뫤ใ츚属)));
        RenderUtils.drawRect(n2 - 汌蚳洝陬괠ꪕ / 2 * n5, (float) (n3 + n4), n2 + 汌蚳洝陬괠ꪕ / 2 * n5, (float) (n3 + n4 + 1), 霥瀳놣㠠釒(ใ퉧뫤ใ츚属, n * 䩜뵯哝䄟픓(ใ퉧뫤ใ츚属)));
//        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        final int n2 = x + width / 2;
        final int n3 = y + height / 2;
        final int 汌蚳洝陬괠ꪕ = (int) fontRenderer.getStringWidth(str);
        final int n4 = 18;
        return mouseX >= n2 - 汌蚳洝陬괠ꪕ / 2 && mouseY >= n3 && mouseX < n2 + 汌蚳洝陬괠ꪕ / 2 && mouseY < n3 + n4;
    }

    public static float 䩜뵯哝䄟픓(final int n) {
        return (n >> 24 & 0xFF) / 255.0f;
    }
}
