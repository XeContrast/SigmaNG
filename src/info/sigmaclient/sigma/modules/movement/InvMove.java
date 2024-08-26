package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.gui.clickgui.NursultanClickGui;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.gui.clickgui.JelloClickGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.network.play.server.SOpenWindowPacket;

import java.util.Arrays;
import java.util.List;

public final class InvMove extends Module {
    public BooleanValue aac = new BooleanValue("AACP", false);
    private Boolean open;
    private static final List<KeyBinding> keys = Arrays.asList(
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindJump
    );
    public InvMove() {
        super("InvMove", Category.Movement, "lets you move in your inventory");
     registerValue(aac);
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        IPacket<?> packet = event.getPacket();
        if(event.packet instanceof CEntityActionPacket){
            if(((CEntityActionPacket) event.packet).getAction() == CEntityActionPacket.Action.START_SPRINTING){
                if(aac.isEnable()) event.cancelable = true;
            }
        }
        if (packet instanceof SOpenWindowPacket || (packet instanceof CClientStatusPacket && ((CClientStatusPacket) packet).getStatus() == CClientStatusPacket.State.REQUEST_STATS)) {
            open = true;
        }
        if (packet instanceof SCloseWindowPacket || packet instanceof CCloseWindowPacket) {
            open = false;
        }
    }

    public static void updateStates() {
        if (mc.currentScreen != null) {
            keys.forEach(k -> KeyBinding.setKeyBindState(k.keyCode, InputMappings.isKeyDown(k)));
        }
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()) {
            if (mc.currentScreen instanceof ContainerScreen || mc.currentScreen instanceof JelloClickGui || mc.currentScreen instanceof NursultanClickGui) {
                updateStates();
            }
        }
       
    }
}
