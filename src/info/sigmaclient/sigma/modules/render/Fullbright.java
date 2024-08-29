package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;


public class Fullbright extends Module {
    public BooleanValue booleanValue = new BooleanValue("No NightVersion", false);
    public Fullbright() {
        super("Fullbright", Category.Render, "I can see anything.");
     registerValue(booleanValue);
    }
    float old;

    @Override
    public void onEnable() {
        mc.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 99999999));
        super.onEnable();
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(event.isPre()){
            mc.player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 99999999));
        }
       
    }

     @Override
    public void onDisable() {
        mc.player.removePotionEffect(Effects.NIGHT_VISION);
        super.onDisable();
    }
}
