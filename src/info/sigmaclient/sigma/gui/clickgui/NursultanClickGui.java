package info.sigmaclient.sigma.gui.clickgui;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.*;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.gui.ColorChanger;
import info.sigmaclient.sigma.sigma5.utils.Sigma5AnimationUtil;
import info.sigmaclient.sigma.utils.font.FontUtil;
import info.sigmaclient.sigma.utils.key.ClickUtils;
import info.sigmaclient.sigma.utils.render.ColorUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import info.sigmaclient.sigma.utils.render.anims.PartialTicksAnim;
import info.sigmaclient.sigma.utils.render.blurs.GradientGlowing;
import info.sigmaclient.sigma.utils.render.blurs.RoundRectShader;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static info.sigmaclient.sigma.gui.clickgui.buttons.ColorButton.rgbToHsv;
import static info.sigmaclient.sigma.modules.Module.mc;

public class NursultanClickGui extends Screen {
    public class SubGui {
        public class ModuleA {
            Module module;
            Sigma5AnimationUtil expandAnimation = new Sigma5AnimationUtil(150,150);
            boolean expand = false;
            Vector4f box = new Vector4f();
            public ModuleA(Module module){
                this.module = module;
            }
        }
        double lastClickX, lastClickY, startX, startY;
        public float x, y;
        public float width = 60, height = 120;
        public float scroll = 0;
        boolean dragging = false;
        PartialTicksAnim anim = new PartialTicksAnim(0);
        public String title;
        List<ModuleA> modules;
        Category category;
        public SubGui(List<Module> m, Category c){
            this.modules = new ArrayList<>();
            for(Module mm : m) this.modules.add(new ModuleA(mm));
            this.category = c;
            this.title = c.name();
        }
        public void release(){
            dragging = false;
        }
        public boolean scroll(double x, double y, double delta){
            if(!ClickUtils.isClickableWithRect(this.x, this.y + 23, this.width, this.height - 5, x, y)){
                return false;
            }
            if(delta > 0){
                anim.setValue(anim.getValueNoTrans() + 13);
                return true;
            }else if(delta < 0){
                anim.setValue(anim.getValueNoTrans() - 13);
                return true;
            }
            return false;
        }
        public void tick(){
            anim.interpolate(0, 6.5f);
        }
        public void draw(float alpha, int mouseX, int mouseY){
            if(anim.getValue() != 0)
                scroll += anim.getValue();
            if (dragging) {
                x = (float) (lastClickX + mouseX - startX);
                y = (float) (lastClickY + mouseY - startY);
            }
            width = 120;
            height = 300;
            Color panelColor = new Color(39,39,39);
            float titleHeight = 23;
            Color titlePanelColor = new Color(31, 31, 31);
            Color bold = new Color(21, 21, 21);
            Color bold2 = new Color(66, 66, 66);
            Color boldText = new Color(143, 143, 143);
            Color lightText = new Color(187, 187, 187);
            Color boldText2 = new Color(180, 180, 180);
            GradientGlowing.applyGradientCornerRL(x, y, width, height, 1, panelColor, panelColor, null);
            RoundRectShader.drawRoundRect(x, y, width, height, 5, panelColor);
            FontUtil.sfuiFontBold18.drawString(title, x + 5, y + 5, boldText2.getRGB());

            RenderUtils.drawRect(x, y + titleHeight, x + width, y + height - 5, titlePanelColor.getRGB());
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect(x, y + titleHeight, x + width, y + height - 5, -1);
            StencilUtil.readStencilBuffer(1);
            float moduleY = y + titleHeight + 3 + scroll, mHeight = 23;
            for(ModuleA moduleA : modules) {
                if(moduleA.expandAnimation.getAnim() == 0){
                    moduleA.expandAnimation = new Sigma5AnimationUtil(150, 150);
                }
                moduleA.expandAnimation.animTo(moduleA.expand);
                float moduleHeight = 0;
                for(Value v : moduleA.module.values){
                    if(v instanceof ColorValue){
                        moduleHeight += 30;
                    }else {
                        moduleHeight += 10;
                    }
                }
                float animation = moduleA.expandAnimation.getAnim() * moduleHeight;

                moduleA.box.set(x + 5, moduleY + 5, x + width - 5, moduleY + 21);
                RenderUtils.drawRoundedRectWithGlow(moduleA.box.getX(), moduleA.box.getY(), moduleA.box.getZ(), moduleA.box.getW() + animation, 4, 4, bold.getRGB());
                if (moduleA.module.enabled) {
                    FontUtil.sfuiFontBold18.drawStringChroma(moduleA.module.remapName, x + 9, moduleY + 10);
                } else {
                    FontUtil.sfuiFontBold18.drawString(moduleA.module.remapName, x + 9, moduleY + 10, boldText);
                }
                GL11.glPushMatrix();
                GL11.glTranslatef(x + width / 2f, moduleA.box.getY(), 0);
                GL11.glScalef(1,moduleA.expandAnimation.getAnim(),1);
                GL11.glTranslatef(-(x + width / 2f), -moduleA.box.getY(), 0);
                float valueY = moduleY + 18;
                for(Value<?> v : moduleA.module.values) {
                    if(v.isHidden()) continue;
                    v.box.set(x + 7, valueY + 1, x + width - 7, valueY + 9);
                    if (v instanceof BooleanValue) {
                        ((BooleanValue) v).animation.animTo(((BooleanValue) v).getValue());

                        FontUtil.sfuiFont14.drawString(v.name, x + 7, valueY + 2, lightText);
                        float boxSize = 8;
                        GradientGlowing.applyGradientCornerRL(v.box.getZ() - boxSize - 3, valueY + 1, boxSize, 8, 1,
                                ColorUtils.blend(ColorChanger.getColor(100, 10), bold2, ((BooleanValue) v).animation.getAnim()),
                                ColorUtils.blend(ColorChanger.getColor(200, 10), bold2, ((BooleanValue) v).animation.getAnim()),
                                3f, 3.5f);
                    }
                    if (v instanceof ModeValue) {
                        FontUtil.sfuiFont14.drawString(v.name, x + 7, valueY + 2, lightText);
                        FontUtil.sfuiFont14.drawString(((ModeValue) v).getValue(), x + width - 45, valueY + 2, lightText);
                    }
                    if (v instanceof NumberValue) {
                        NumberValue value = (NumberValue) v;
                        float myWidth = x + width - 5 - v.box.getX() - 48 - 7, start = v.box.getX() + 48 + 7;
                        if(ClickUtils.isClickable(v.box.getX(), v.box.getY(), v.box.getZ(), v.box.getW(),mouseX,mouseY) && mouseDown){
                            float pp = (mouseX - start) / myWidth;
                            pp = Math.max(0, pp);
                            pp = Math.min(1, pp);
                            float mmx = value.min.floatValue() + pp * (value.max.floatValue() - value.min.floatValue());
                            if(value.inc == NumberValue.NUMBER_TYPE.INT)
                                mmx = (int) mmx;
                            else if(value.inc == NumberValue.NUMBER_TYPE.FLOAT)
                                mmx = (float)(Math.round(mmx*100))/100;
                            else mmx = (float)(Math.round(mmx*10))/10;
                            value.setValue(mmx);
                        }
                        FontUtil.sfuiFont14.drawString(v.name, x + 7, valueY + 2, lightText);

                        FontUtil.sfuiFont12.drawString(String.format("%.2f", value.getValue().floatValue()), x + 38, valueY + 2, lightText);

                        GradientGlowing.applyGradientCornerRL(start, valueY + 4, myWidth, 2, 1,
                                ColorChanger.getColor(100, 10),
                                ColorChanger.getColor(200, 10),
                                3f, 3.5f);
                    }
                    if (v instanceof ColorValue) {
                        ColorValue value = (ColorValue) v;
                        float myWidth = width - 15 - 5 - 10, start = v.box.getX() + 35;
                        float heightColor = 20;
                        Vector4f boxColor = new Vector4f(x + 25, valueY + 1, myWidth, heightColor);
                        Vector4f boxColor2 = new Vector4f(x + 25, valueY + 1, myWidth, heightColor / 2f);
                        RenderUtils.drawRect(x + width - 20, valueY + 1, x + width - 8, valueY + 13, value.getColorInt());
                        FontUtil.sfuiFont14.drawString(v.name, x + 7, valueY + 2, lightText);
                        float[] rgbToHsv = rgbToHsv(new int[]{value.getColor().getRed(), value.getColor().getGreen(), value.getColor().getBlue()});

                        if (ClickUtils.isClickableWithRect(boxColor.getX(),boxColor.getY(),boxColor.getZ(),boxColor.getW(),mouseX,mouseY) && mouseDown) {
                            float cx = (mouseX - boxColor.getX()) / (boxColor.getZ());
                            cx = Math.min(1, cx);
                            cx = Math.max(0, cx);
                            float cy = (mouseY - boxColor.getY()) / boxColor.getW();
                            cy = Math.min(1, cy);
                            cy = Math.max(0, cy);
                            rgbToHsv[0] = cx;
                            rgbToHsv[1] = 1 - cy;
                            value.setValue(Color.HSBtoRGB(rgbToHsv[0], rgbToHsv[1], rgbToHsv[2]));
                        } else {
                            if (ClickUtils.isClickableWithRect(boxColor2.getX(),boxColor2.getY(),boxColor2.getZ(),boxColor2.getW(),mouseX,mouseY) && mouseDown) {
                                float cx = (mouseX - boxColor2.getX()) / (boxColor2.getZ());
                                cx = Math.min(1, cx);
                                cx = Math.max(0, cx);
                                rgbToHsv[2] = 1 - cx;
                                value.setValue(Color.HSBtoRGB(rgbToHsv[0], rgbToHsv[1], rgbToHsv[2]));
                            }
                        }
                        RenderUtils.drawTextureLocation(boxColor.getX(),boxColor.getY(),boxColor.getZ(),boxColor.getW(),
                                "clickgui/color",
                                new Color(1,1,1,alpha));

                        RenderUtils.drawTextureLocation(boxColor2.getX(),boxColor2.getY(),boxColor2.getZ(),boxColor2.getW(),
                                "clickgui/color2",
                                new Color(1,1,1,alpha));
                        valueY += 20;
                    }
                    valueY += 10;
                }
                GL11.glPopMatrix();
                moduleY += mHeight + animation;
            }
            StencilUtil.uninitStencilBuffer();
        }
        public boolean click(double x, double y, int b){
            if(!ClickUtils.isClickableWithRect(this.x, this.y, this.width, this.height, x, y)){
                return false;
            }
            if(ClickUtils.isClickableWithRect(this.x, this.y, this.width, 22, x, y) && b == 0) {
                startX = x;
                startY = y;
                lastClickX = this.x;
                lastClickY = this.y;
                dragging = true;
                return true;
            }

            for(ModuleA moduleA : modules) {
                for(Value<?> v : moduleA.module.values) {
                    if(v.isHidden()) continue;
                    if(ClickUtils.isClickable(v.box.getX(), v.box.getY(), v.box.getZ(), v.box.getW(), x, y)){
                        if(v instanceof BooleanValue){
                            ((BooleanValue) v).setValue(!((BooleanValue) v).isEnable());
                        }
                        if(v instanceof ModeValue){
                            String[] modes = ((ModeValue) v).values;
                            int current = 0, i = 0;
                            for(String s : modes){
                                if(s.equals(v.getValue())) {
                                    current = i;
                                    break;
                                }
                                i ++;
                            }
                            ((ModeValue) v).setValue(modes[(current + 1) >= modes.length ? 0 : (current + 1)]);
                        }
                        if(v instanceof NumberValue){

                        }
                    }
                }
                if(ClickUtils.isClickable(moduleA.box.getX(), moduleA.box.getY(), moduleA.box.getZ(), moduleA.box.getW(), x, y)){
                    if(b == 0) {
                        moduleA.module.toggle();
                    }else if(b == 1){
                        moduleA.expand = !moduleA.expand;
                    }
                    return true;
                }
            }
            return false;
        }
    }
    boolean mouseDown = false;
    public List<SubGui> dropGUIS = new ArrayList<>();
    public Sigma5AnimationUtil joinAnim = new Sigma5AnimationUtil(450, 125);
    public static PartialTicksAnim leave = new PartialTicksAnim(1.8f);
    public boolean startLeaving; // always 0 - 1
    public NursultanClickGui(){
        super(new StringTextComponent(""));
        // 初始化
        for(Category category : new Category[]{Category.Combat, Category.Render, Category.Misc, Category.Movement, Category.Player}){
            List<Module> a = SigmaNG.getSigmaNG().moduleManager.getModulesInType(category);
            if(category == Category.Render){
                a.addAll(SigmaNG.getSigmaNG().moduleManager.getModulesInType(Category.Gui));
            }
            if(category == Category.Player){
                a.addAll(SigmaNG.getSigmaNG().moduleManager.getModulesInType(Category.World));
            }
            SubGui dropGUI = new SubGui(a, category);
            dropGUIS.add(dropGUI);
        }
        float dx = 10, dy = 10;
        int stage = 0;
        for(SubGui subUI : dropGUIS){
            subUI.x = dx;
            subUI.y = dy;
            dx += 105;
            stage ++;
            if(stage == 4){
                dx = 10;
                dy += 165;
            }
        }
        joinAnim.reset();
        startLeaving = false;
    }

    boolean init = false;
    @Override
    public void initGui() {
        for (Module module : SigmaNG.getSigmaNG().moduleManager.modules) {
            module.hove = false;
        }
        for(SubGui subUI : dropGUIS){
            subUI.scroll = 0;
            subUI.anim.setValue(0);
        }
        joinAnim.reset();
        joinAnim = new Sigma5AnimationUtil(450, 125);
        startLeaving = false;
        leave.setValue(1.0f);
        init = true;
        super.initGui();
    }

    @Override
    public void tick() {
        for(SubGui subUI : dropGUIS){
            subUI.tick();
        }
        super.tick();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        startLeaving = mc.currentScreen == null;
        joinAnim.animTo(!startLeaving ? Sigma5AnimationUtil.AnimState.ANIMING : Sigma5AnimationUtil.AnimState.SLEEPING);
        float value = joinAnim.getAnim() * joinAnim.getAnim();
        value *= 1.1f;
        if(value > 1.05f){
            value = 1.05f - (value - 1.05f);
        }
        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(mc);
        if(value != 1){
            GlStateManager.translate(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0);
            GlStateManager.scale(value, value, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2f, -sr.getScaledHeight() / 2f, 0);
        }
        for(SubGui subUI : dropGUIS){
            subUI.draw(1, mouseX, mouseY);
        }
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        for(SubGui subUI : dropGUIS){
            if(subUI.scroll(mouseX, mouseY, delta)){
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseDown = false;
        for(SubGui subUI : dropGUIS){
            subUI.release();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0) mouseDown = true;
        for(SubGui subUI : dropGUIS){
            if(subUI.click(mouseX, mouseY, button)){
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onGuiClosed() {
        for (Module module : SigmaNG.getSigmaNG().moduleManager.modules) {
            module.hove = false;
        }
        new Thread(()-> SigmaNG.SigmaNG.configManager.saveDefaultConfig()).start();
        startLeaving = true;
    }
}
