package info.sigmaclient.sigma.gui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.premium.AntiCrack;
import info.sigmaclient.sigma.premium.PremiumManager;
import info.sigmaclient.sigma.sigma5.utils.Custom5AnimationUtil;
import info.sigmaclient.sigma.utils.key.ClickUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.anims.extended.Direction;
import info.sigmaclient.sigma.utils.render.anims.extended.impl.DecelerateAnimation;
import info.sigmaclient.sigma.utils.render.blurs.JelloBlur;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static info.sigmaclient.sigma.sigma5.utils.SomeAnim.欫좯콵甐鶲㥇;

public class SelectAddonsGUI extends Screen {
    private final String RECT_BLUR = "sigma/shadowgui.png";
    public SelectAddonsGUI() {
        super(new StringTextComponent("MainMenu-Select"));
        anim = 255;
        blurAlpha = new DecelerateAnimation(300, 300/255);
        blurAlpha.setDirection(Direction.FORWARDS);
        initMain = false;
    }
    private Minecraft mc = Minecraft.getInstance();
    private boolean initMain;
    private float anim;

    public static float animatedMouseX;
    public static float animatedMouseY;
    private final ResourceLocation BACKGROUND = new ResourceLocation("sigma/select/sele.png");
    private final Custom5AnimationUtil LogoAnim = new Custom5AnimationUtil(50,0);
    private final Custom5AnimationUtil NoAddonAnim = new Custom5AnimationUtil(2.5f,0);
    private final Custom5AnimationUtil SigmaAnim = new Custom5AnimationUtil(2.5f,0);
    private final Custom5AnimationUtil JelloAnim = new Custom5AnimationUtil(2.5f,0);

    private final DecelerateAnimation blurAlpha;
    @Getter
    private float blurAlphaValue = 0F;

    public SigmaGuiMainMenu.ParticleEngine pe = new SigmaGuiMainMenu.ParticleEngine();

    boolean Opened = false,isClosing;

    private Matrix4f matrix = new Matrix4f();

    @Override
    protected void init() {
        super.init();
        if(SigmaNG.init){
            this.closeScreen();
        }
        if(!Opened) {
            initMain = false;
            anim = 255;
            LogoAnim.reset();
            NoAddonAnim.reset(Custom5AnimationUtil.AnimState.SLEEPING);
            SigmaAnim.reset(Custom5AnimationUtil.AnimState.SLEEPING);
            JelloAnim.reset(Custom5AnimationUtil.AnimState.SLEEPING);
        }
        animatedMouseX = 0;
        animatedMouseY = 0;

        Opened = true;
    }

    @Override
    public void onClose() {
        super.onClose();
        Opened = false;
        isClosing = true;
        blurAlpha.setDirection(Direction.BACKWARDS);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if(NoAddonAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            NoAddonAnim.anim(1.25f, NoAddonAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }
        if(SigmaAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            SigmaAnim.anim(1.25f, SigmaAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }
        if(JelloAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            JelloAnim.anim(1.25f, JelloAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }

        matrix = matrixStack.getLast().getMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(NoAddonAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            NoAddonAnim.anim(1.25f, NoAddonAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }
        if(SigmaAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            SigmaAnim.anim(1.25f, SigmaAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }
        if(JelloAnim.isAnim != Custom5AnimationUtil.AnimState.SLEEPING) {
            JelloAnim.anim(1.25f, JelloAnim.isAnim == Custom5AnimationUtil.AnimState.BACKING);
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getInstance());
        float m1 = sr.getScaledWidth() * 1.05f;
        float h1 = sr.getScaledHeight() * 1.05f;
        float m = 1.0f;
        float addM = m1 / 2f - sr.getScaledWidth() / 2F;
        float addH = h1 / 2f - sr.getScaledHeight() / 2F;


        float blurAlpha = this.blurAlpha.getOutput().floatValue();
        blurAlpha = !isClosing ? 欫좯콵甐鶲㥇(blurAlpha, 0.17, 1.0, 0.51, 1.0) : (1 - 欫좯콵甐鶲㥇(1 - blurAlpha, 0.17, 1.0, 0.51, 1.0));


        blurAlphaValue = blurAlpha;

        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND);
        drawTexture(-addM + -(animatedMouseX / sr.getScaledWidth() - 0.5f) * 2 * addM, -addH + -(animatedMouseY / sr.getScaledHeight() - 0.5f) * 2 * addH, m1 * m, h1 * m, blurAlpha);

        if(anim > 0) {
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("sigma/select/spl.png"));
            drawTexture(0 , 0, sr.getScaledWidth() , sr.getScaledHeight(),anim/255);
            anim -= 8;
            GL11.glPopMatrix();
            draw(anim > 0,mouseX,mouseY);
            return;
        }
        draw(false,mouseX,mouseY);

        animatedMouseX += (float) (((mouseX - animatedMouseX) / 1.8) + 0.1);
        animatedMouseY += (float) (((mouseY - animatedMouseY) / 1.8) + 0.1);
        final int n3 = 455;
        final int n4 = 78;
        final int n5 = (sr.getScaledWidth() - n3) / 2;
        final int round = Math.round((sr.getScaledHeight() - n4) / 2f - 14.0f);

    }


    private void draw(boolean done,float m_x,float m_y){

        if(done){
            LogoAnim.animTo(Custom5AnimationUtil.AnimState.ANIMING);
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getInstance());
        final int n3 = 455;
        final int n4 = 78;
        final int n5 = (sr.getScaledWidth() - n3) / 2;
        final int round = Math.round((sr.getScaledHeight() - n4) / 2f - 14.0f);
        //绘制按钮
        {
            //No Addon
            float a_x = (float) n5 + n3 * 0.25f - 20,a_y = (float) round + n4 * 0.25f + 20 - NoAddonAnim.getValueNoTrans(),
                    a_w = 537/2f,a_h = 93/2f;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GlStateManager.resetColor();

            float a_alpha = (NoAddonAnim.getAnim() > 1) ? 1 : ((NoAddonAnim.getAnim() < 0) ? 0 : NoAddonAnim.getAnim());
            RenderUtils.drawRect(a_x, a_y, a_x + a_w, a_y + a_h, new Color(75, 0, 105, (int) (165 * a_alpha)).getRGB());
            RenderUtils.drawRect(a_x,a_y,a_x + a_w,a_y + a_h, new Color(255, 255, 255, 35).getRGB());//alpha 35
            for (int i = 0; i < 15; i++) {
                JelloBlur.renderRectBlur(a_x,a_y, a_w, a_h, mc.getFramebuffer().framebufferTexture, 4, 10);
            }
            RenderUtils.drawRect(a_x, a_y, a_x + a_w, a_y + a_h, new Color(75, 0, 105, (int) (20 * a_alpha)).getRGB());
            RenderUtils.drawRect(a_x,a_y,a_x + a_w,a_y + a_h, new Color(255, 255, 255, 10).getRGB());
            RenderUtils.drawTextureLocationZoom(a_x,a_y, a_w, a_h, "sigma/select/noaddons.png", new Color(255, 255, 255, 255),true);
            GL11.glPopMatrix();
            //Sigma
            float s_x = (float) n5 + n3 * 0.25f - 20,s_y = (float) round + n4 * 0.25f + 72.5f - SigmaAnim.getValueNoTrans(),
                    s_w = 264/2f,s_h = 61/2f;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GlStateManager.resetColor();
            float s_alpha = (SigmaAnim.getAnim() > 1) ? 1 : ((SigmaAnim.getAnim() < 0) ? 0 : SigmaAnim.getAnim());
            RenderUtils.drawRect(s_x,s_y, s_x + s_w, s_y + s_h, new Color(75, 0, 105, (int) (165 * s_alpha)).getRGB());
            RenderUtils.drawRect(s_x,s_y, s_x + s_w, s_y + s_h, new Color(255, 255, 255, 25).getRGB());
            for (int i = 0; i < 15; i++) {
                JelloBlur.renderRectBlur(s_x,s_y, s_w,  s_h, mc.getFramebuffer().framebufferTexture, 4, 10);
            }
            RenderUtils.drawRect(s_x,s_y, s_x + s_w, s_y + s_h, new Color(75, 0, 105, (int) (20 * s_alpha)).getRGB());
            RenderUtils.drawRect(s_x,s_y, s_x + s_w, s_y + s_h, new Color(255, 255, 255, 10).getRGB());
            RenderUtils.drawTextureLocationZoom(s_x,s_y, s_w,  s_h, "sigma/select/classic.png", new Color(255, 255, 255, 255),true);
            GL11.glPopMatrix();
            //Jello
            float j_x = (float) n5 + n3 * 0.25f - 15 + s_w,j_y = (float) round + n4 * 0.25f + 72.5f - JelloAnim.getValueNoTrans(),
                    j_w = 264/2f,j_h = 61/2f;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GlStateManager.resetColor();
            float j_alpha = (JelloAnim.getAnim() > 1) ? 1 : ((JelloAnim.getAnim() < 0) ? 0 : JelloAnim.getAnim());
            RenderUtils.drawRect(j_x, j_y, j_x + j_w, j_y + j_h, new Color(75, 0, 105, (int) (165 * j_alpha)).getRGB());
            RenderUtils.drawRect(j_x,j_y, j_x + j_w, j_y + j_h, new Color(255, 255, 255, 25).getRGB());
            for (int i = 0; i < 15; i++) {
                JelloBlur.renderRectBlur(j_x,j_y, j_w,  j_h, mc.getFramebuffer().framebufferTexture, 4, 10);
            }
            RenderUtils.drawRect(j_x, j_y, j_x + j_w, j_y + j_h, new Color(75, 0, 105, (int) (20 * a_alpha)).getRGB());
            RenderUtils.drawRect(j_x,j_y, j_x + j_w, j_y + j_h, new Color(255, 255, 255, 10).getRGB());
            RenderUtils.drawTextureLocationZoom(j_x,j_y, j_w,  j_h, "sigma/select/jello.png", new Color(255, 255, 255, 255),true);
            GL11.glPopMatrix();

        }

        //绘制LOGO
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        RenderUtils.drawTextureLocationZoom((float)n5 + n3 * 0.25f, (float)round + n4 * 0.25f - LogoAnim.getValue(), (float)n3 * 0.5f, (float)n4 * 0.5f, "logo2", new Color(霥瀳놣㠠釒(-65794, 1)));
        GL11.glPopMatrix();
    }
    public static int 霥瀳놣㠠釒(final int n, final float n2) {
        return (int)(n2 * 255.0f) << 24 | (n & 0xFFFFFF);
    }

    @Override
    public void mouseMoved(double m_x, double m_y) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getInstance());
        final int n3 = 455;
        final int n4 = 78;
        final int n5 = (sr.getScaledWidth() - n3) / 2;
        final int round = Math.round((sr.getScaledHeight() - n4) / 2f - 14.0f);
        //No Addon
        float a_x = (float) n5 + n3 * 0.25f - 20,a_y = (float) round + n4 * 0.25f + 20 - NoAddonAnim.getValueNoTrans(),
                a_w = 537/2f,a_h = 93/2f;
        //Sigma
        float s_x = (float) n5 + n3 * 0.25f - 20,s_y = (float) round + n4 * 0.25f + 72.5f - SigmaAnim.getValueNoTrans(),
                s_w = 264/2f,s_h = 61/2f;
        //Jello
        float j_x = (float) n5 + n3 * 0.25f - 15 + s_w,j_y = (float) round + n4 * 0.25f + 72.5f - JelloAnim.getValueNoTrans(),
                j_w = 264/2f,j_h = 61/2f;
        NoAddonAnim.setAnim(ClickUtils.isClickableWithRect(a_x,a_y,a_w,a_h,m_x,m_y)? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        SigmaAnim.setAnim(ClickUtils.isClickableWithRect(s_x,s_y,s_w,s_h,m_x,m_y)? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);
        JelloAnim.setAnim(ClickUtils.isClickableWithRect(j_x,j_y,j_w,j_h,m_x,m_y)? Custom5AnimationUtil.AnimState.ANIMING: Custom5AnimationUtil.AnimState.BACKING);

    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
        ScaledResolution sr = new ScaledResolution(Minecraft.getInstance());
        final int n3 = 455;
        final int n4 = 78;
        final int n5 = (sr.getScaledWidth() - n3) / 2;
        final int round = Math.round((sr.getScaledHeight() - n4) / 2f - 14.0f);
        //No Addon
        float a_x = (float) n5 + n3 * 0.25f - 20,a_y = (float) round + n4 * 0.25f + 20 - NoAddonAnim.getValueNoTrans(),
                a_w = 537/2f,a_h = 93/2f;
        //Sigma
        float s_x = (float) n5 + n3 * 0.25f - 20,s_y = (float) round + n4 * 0.25f + 72.5f - SigmaAnim.getValueNoTrans(),
                s_w = 264/2f,s_h = 61/2f;
        //Jello
        float j_x = (float) n5 + n3 * 0.25f - 15 + s_w,j_y = (float) round + n4 * 0.25f + 72.5f - JelloAnim.getValueNoTrans(),
                j_w = 264/2f,j_h = 61/2f;
        if(mouseButton == 0){
            if (NoAddonAnim.getAnim() >= 0.3f && ClickUtils.isClickableWithRect(a_x,a_y,a_w,a_h,mouseX,mouseY)){
                System.out.println("No Addon");
                SigmaNG.gameMode = SigmaNG.GAME_MODE.SAFEMODE;
                initMain = true;
            }else if (SigmaAnim.getAnim() >= 0.3f && ClickUtils.isClickableWithRect(s_x,s_y,s_w,s_h,mouseX,mouseY)){
                System.out.println("Sigma");
                SigmaNG.gameMode = SigmaNG.GAME_MODE.SIGMA;
                initMain = true;
            }else if (JelloAnim.getAnim() >= 0.3f && ClickUtils.isClickableWithRect(j_x,j_y,j_w,j_h,mouseX,mouseY)){
                AntiCrack.isPremium = PremiumManager.isPremium = true;
                PremiumManager.password = "1145";
                PremiumManager.userName = "Dev";
                PremiumManager.serverBack = "Sb";
                PremiumManager.token = "AYUS";
                System.out.println("Jello");
                SigmaNG.gameMode = SigmaNG.GAME_MODE.JELLO;
                initMain = true;
            }
        }
        if(this.initMain){
            this.initMain = false;
            if (!SigmaNG.init) {
                switch (SigmaNG.gameMode) {
                    case SIGMA: {
                        break;
                    }
                    case JELLO: {
                        SigmaNG.staticInitTitle();
                        SigmaNG.init = true;
                        Minecraft.getInstance().displayGuiScreen(new SigmaGuiMainMenu());
                        break;
                    }
                    default: {
                        SigmaNG.staticInitTitle();
                        SigmaNG.init = true;
                        Minecraft.getInstance().displayGuiScreen(new MainMenuScreen());
                        break;
                    }
                }
            }
        }
        return false;
    }


    private void drawTexture(float x, float y, float w, float h, float alpha) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        RenderUtils.drawTextureCustom(x, y, w, h, alpha);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
