package info.sigmaclient.sigma.event.impl.render;

import info.sigmaclient.sigma.event.Event;
import net.minecraft.tileentity.TileEntity;

public class RenderChestEvent extends Event {
    public TileEntity tileEntity;
    private final Runnable modelRenderer;
    public boolean pre;
    public RenderChestEvent(Runnable modelRenderer, boolean pre, TileEntity tileEntity) {
        this.modelRenderer = modelRenderer;
        this.tileEntity = tileEntity;
        this.pre = pre;
    }
    public void drawModel() {
        this.modelRenderer.run();
    }
}
