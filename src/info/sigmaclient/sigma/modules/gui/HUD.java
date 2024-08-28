package info.sigmaclient.sigma.modules.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.font.FontUtil;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;
import net.optifine.util.MathUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HUD extends Module {
    public static ModeValue markpng = new ModeValue("WaterMark","Default",new String[] {
            "Default","Debug","FWMike"
    });
    public static BooleanValue speed = new BooleanValue("SpeedShow",false);
    public static BooleanValue eat = new BooleanValue("EatingTickShow",false);
    public static BooleanValue time = new BooleanValue("TimerNumber",false);
    public static BooleanValue gradient = new BooleanValue("Gradient", true);
    public double tick,idk = 0;
    public static float bps;
    public HUD() {
        super("Hud", Category.Gui, "new hud");
        registerValue(speed);
        registerValue(markpng);
        registerValue(eat);
        registerValue(time);
        registerValue(gradient);
    }

    @EventTarget
    public void onUpdateEvent(UpdateEvent event) {
        MovementUtils.updateBlocksPerSecond();
        bps = (float) Math.hypot(mc.player.getPosX() - mc.player.prevPosX,mc.player.getPosZ() - mc.player.prevPosZ);
        if (eat.isEnable()) {
            if (mc.player.getHeldItem(Hand.MAIN_HAND).isFood()  || mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PotionItem) {
                if (mc.gameSettings.keyBindUseItem.pressed) {
                    tick++;
                } else {
                    tick = 0;
                }
                if ((tick / (64 / mc.timer.getTimerSpeed())) >= 1) {
                    tick = 0;
                    idk = 0;
                }
            }
        }
    }

    @EventTarget
    public void onRenderEvent(RenderEvent event) {
        if (markpng.is("FWMike")) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String L = " §8- §rIamFrozenMilk §8- §r" + Minecraft.debugFPS + " fps" + " §8-§r " + formatter.format(new Date(System.currentTimeMillis()));
            String s1 = "Nursultan" + L;
            float width = FontUtil.sfuiFontBold17.getStringWidth(s1) + 2;
            int height = 14;
            float d = 7;
            Shader.drawRoundRectWithGlowing(10 - d, 10 - d, 10 + width + d, 10 + height + d, new Color(0.117647059f, 0.117647059f, 0.117647059f));
            float x = FontUtil.sfuiFontBold17.drawStringChroma("nursultan", 12, 15);
            FontUtil.sfuiFontBold17.drawString(L, 12 + x, 15, new Color(220, 220, 220).getRGB());
        }
        if (eat.isEnable()) {
            if (mc.player.getHeldItem(Hand.MAIN_HAND).isFood() || mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PotionItem) {
                double math;
                final double height = Minecraft.getInstance().getMainWindow().getScaledHeight();
                final double width = Minecraft.getInstance().getMainWindow().getScaledWidth();
                if (mc.gameSettings.keyBindUseItem.pressed) {
                    idk = (float) 1 - (tick / (64 / mc.timer.getTimerSpeed()));
                    math = 30 * idk;
                } else {
                    math = 0;
                }
                double left = (width / 2) - math;
                double right = (width / 2) + math;
                if (mc.gameSettings.keyBindUseItem.pressed) FontUtil.sfuiFontBold17.drawString(MathUtils.round((float) tick / (64 / mc.timer.getTimerSpeed())) * 100 + "%", (float) (width / 2) - 5, (float) (height / 2) + 10, new Color(220, 220, 220).getRGB());

                RenderUtils.drawRect(left, (height / 2) + 20, right, (height / 2) + 25, Color.WHITE.getRGB());
                GlStateManager.disableBlend();
            }
        }
        if (time.isEnable()) {
            final double height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            final double width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            FontUtil.sfuiFontBold17.drawString(mc.timer.getTimerSpeed() + " Timer", (float) (width / 2) - 10, (float) (height / 2) + 30, new Color(220, 220, 220).getRGB());
        }
        if (speed.isEnable()) {
            final double height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            final double width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            FontUtil.sfuiFontBold17.drawString(MathUtils.round(bps * (speed.isEnable() ? 20f : 72f) * mc.timer.getTimerSpeed()) + " Speed", (float) (width / 2) - 10, (float) (height / 2) + 40, new Color(220, 220, 220).getRGB());
        }
    }
    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
    }
}
