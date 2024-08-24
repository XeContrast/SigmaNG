package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.entity.EntityType;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;


public class FPSBooster extends Module {
    BooleanValue particle = new BooleanValue("Particle", true);
    BooleanValue armorStand = new BooleanValue("ArmorStand", false);
    public FPSBooster() {
        super("FPSBooster", Category.Render, "Boost your fps.");
     registerValue(particle);
     registerValue(armorStand);
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof SSpawnParticlePacket){
            if(particle.isEnable())
                event.cancelable = true;
        }
        if(event.packet instanceof SSpawnObjectPacket){
            if(((SSpawnObjectPacket) event.packet).getType() == EntityType.ARMOR_STAND){
                if(armorStand.isEnable()){
                    event.cancelable = true;
                }
            }
        }
        
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        
    }
}
