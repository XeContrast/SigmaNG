package info.sigmaclient.sigma.event.impl.other;

import info.sigmaclient.sigma.event.Event;
import net.minecraft.entity.LivingEntity;

public class LivingEntitiesUpdateEvent extends Event {
    private LivingEntity entity;

    public LivingEntitiesUpdateEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }
}
