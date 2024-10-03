package info.sigmaclient.sigma.sigma5.killaura;


import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
public class EntityWrapper {
    private static String[] someStrings;
    private Entity entity;
    private CustomData customData;

    public EntityWrapper(final Entity entity) {
        this.entity = entity;
        this.customData = null;
    }

    public EntityWrapper(final Entity entity, final CustomData customData) {
        this.entity = entity;
        this.customData = customData;
    }

    public boolean hasCustomData() {
        return this.customData != null && this.customData.isValid();
    }

    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof EntityWrapper && ((EntityWrapper) o).getEntity() == this.getEntity());
    }
}
