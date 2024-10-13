package info.sigmaclient.sigma.gui.configscreen;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.gui.font.JelloFontUtil;
import info.sigmaclient.sigma.gui.keybindmanager.KeyBindManager;
import info.sigmaclient.sigma.modules.gui.hide.ClickGUI;
import info.sigmaclient.sigma.sigma5.utils.ConfigButton;
import info.sigmaclient.sigma.sigma5.utils.Sigma5AnimationUtil;
import info.sigmaclient.sigma.sigma5.utils.Sigma5CheckBox;
import info.sigmaclient.sigma.sigma5.utils.Sigma5DrawText;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;
import static info.sigmaclient.sigma.gui.clickgui.JelloClickGui.calculateAnimationProgress;
import static info.sigmaclient.sigma.gui.clickgui.JelloClickGui.calculateReverseAnimationProgress;
import static info.sigmaclient.sigma.gui.font.RenderSystem.drawGradientRect;
import static info.sigmaclient.sigma.minimap.interfaces.InterfaceHandler.mc;
import static info.sigmaclient.sigma.modules.render.NameTags.blendColors;

public class GuiConfigScreen extends Screen {
    public static KeyBindManager keyBindManager = new KeyBindManager();
    Sigma5AnimationUtil Sigma5AnimationUtil = new Sigma5AnimationUtil(300, 100);
    public GuiConfigScreen() {
        super(new StringTextComponent("ConfigScreen"));
    }

    @Override
    protected void init() {
        Sigma5AnimationUtil = new Sigma5AnimationUtil(300, 100);
        final int max = Math.max((int)(this.height * 0.8f), 420);
        final int n2 = (int)(this.width * 0.8f);
        xPosition = 0;
        yPosition = this.height - max;
        int n4 = width;
        int n5 = max;

        int buttonWidth = 150;
        int buttonHeight = 32;
        int buttonY = n5 - 77;
//        鼒鄡嘖ꁈ柿 鼒鄡嘖ꁈ柿2 = 鼒鄡嘖ꁈ柿.待曞缰粤퉧.ಽ㢸ኞ韤햠鱀();
//        鼒鄡嘖ꁈ柿2.韤鼒嶗䩉塱葫(-65794);
        ConfigButton openKeybindManagerButton = new ConfigButton(
                (n4 - buttonWidth * 2) / 2 -10, buttonY, buttonWidth, buttonHeight,
                "Open Keybind Manager", (n) -> {
            Minecraft.getInstance().displayGuiScreen(keyBindManager);
        }, JelloFontUtil.jelloFont24
        );
        this.addButton(openKeybindManagerButton);

        ConfigButton openClickGuiButton = new ConfigButton(
                (n4 + buttonWidth) / 2 -70, buttonY, buttonWidth, buttonHeight,
                "Open Jello's Click GUI", (n) -> {
            Minecraft.getInstance().displayGuiScreen(ClickGUI.clickGui);
        }, JelloFontUtil.jelloFont24
        );
        this.addButton(openClickGuiButton);

        ConfigButton Credits = new ConfigButton(n4 / 2 - 100, n5 - 230, 200, buttonHeight +400, "Love From LUMO Development",(n)->{

        },JelloFontUtil.jelloFont16);
        this.addButton(Credits);

        Sigma5CheckBox guiBlurCheckBox = new Sigma5CheckBox("guiBlurCheckBox", n4 / 2 - 70 + 26, height - 215, 23, 23, (n)->{
            SigmaNG.getSigmaNG().guiBlur = !SigmaNG.getSigmaNG().guiBlur;
        });
        guiBlurCheckBox.enabled = SigmaNG.getSigmaNG().guiBlur;
        this.addButton(guiBlurCheckBox);
        Sigma5CheckBox inGameGuiBlurCheckBox = new Sigma5CheckBox("guiBlurIngameCheckBox", n4 / 2 + 130 - 65, height - 215, 23, 23, (n)->{
            SigmaNG.getSigmaNG().inGameGuiBlur = !SigmaNG.getSigmaNG().inGameGuiBlur;
        });
        inGameGuiBlurCheckBox.enabled = SigmaNG.getSigmaNG().inGameGuiBlur;
        this.addButton(inGameGuiBlurCheckBox);
        super.init();
    }
    int yPosition = 0;
    int xPosition = 0;
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(mc);
        float n2 = 1.3f - calculateAnimationProgress(Sigma5AnimationUtil.getAnim(), 0.0f, 1.0f, 1.0f) * 0.3f;
        float animationProgress = 1.0f;
        if (Sigma5AnimationUtil.isAnim == info.sigmaclient.sigma.sigma5.utils.Sigma5AnimationUtil.AnimState.SLEEPING) {
            n2 = 0.7f + calculateReverseAnimationProgress(Sigma5AnimationUtil.getAnim(), 0.0f, 1.0f, 1.0f) * 0.3f;
            animationProgress = Sigma5AnimationUtil.getAnim();
        }
        drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), blendColors(-1072689136, applyColor(-16711423, 0.1f), animationProgress), blendColors(-804253680, applyColor(-16711423, 0.1f), animationProgress));
//        this.㮃Wಽ待㐖㼜(n2, n2);  \
        if(n2 != 1) {
            GlStateManager.translate(sr.getScaledWidth() / 2.0, sr.getScaledHeight() / 2.0, 0);
            GlStateManager.scale(n2, n2, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2.0, -sr.getScaledHeight() / 2.0, 0);
        }
        float n = 1;
        this.drawHeaderText((width - 202) / 2, this.yPosition + 20, n);
        final StringBuilder append = new StringBuilder().append("You're currently using Sigma ");
//        SigmaMain.鼒釒쇽Ꮤ鶊();
        final String string = append.append(SigmaNG.getClientVersion()).toString();
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont20, (float)(this.xPosition + (width - JelloFontUtil.jelloFont20.getStringWidth(string)) / 2), (float)(this.yPosition + 56), string, applyColor(-65794, 0.4f * n));
        final String string2 = "Click GUI is currently bound to: " + InputMappings.getInputByCode(SigmaNG.getSigmaNG().moduleManager.getModule(ClickGUI.class).key).replace("key.keyboard.", "").toUpperCase() + " Key";
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont20, (float)(xPosition + (width - JelloFontUtil.jelloFont20.getStringWidth(string2)) / 2), (float)(height - 187), string2, applyColor(-65794, 0.6f * n));
        final String s = "Configure all your keybinds in the keybind manager!";
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont14, (float)(xPosition + (width - JelloFontUtil.jelloFont14.getStringWidth(s)) / 2), (float)(height - 187 + 15), s, applyColor(-65794, 0.4f * n));
        final String s2 = "GUI Blur: ";
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont20, (float)(xPosition + (width - JelloFontUtil.jelloFont20.getStringWidth(s2)) / 2 - 114)+55, (float)(height - 221 + 13), s2, applyColor(-65794, 0.5f * n));
        final String s3 = "GPU Accelerated: ";
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont20, (float)(xPosition + (width - JelloFontUtil.jelloFont20.getStringWidth(s3)) / 2 + 52)-23, (float)(height - 221 + 13), s3, applyColor(-65794, 0.5f * n));

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }


    private void drawHeaderText(final int n, final int n2, final float n3) {
        Sigma5DrawText.drawString(JelloFontUtil.jelloFontBold40, (float)n + 50.5f, (float)(n2 + 7), "Jello", applyColor(-65794, n3));
        Sigma5DrawText.drawString(JelloFontUtil.jelloFont25, (float)(n + 99f), (float)(n2 + 14), "for Sigma", applyColor(-65794, 0.86f * n3));
    }
    @Override
    public void initGui() {
//        Minecraft.getMinecraft().displayGuiScreen(keyBindManager);
        super.initGui();
    }
}
