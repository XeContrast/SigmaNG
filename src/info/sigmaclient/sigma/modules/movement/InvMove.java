package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ModeValue;
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
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SCloseWindowPacket;
import net.minecraft.network.play.server.SOpenWindowPacket;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class InvMove extends Module {
    private final CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList<>();
    public BooleanValue aac = new BooleanValue("AACP", false);
    public ModeValue mode = new ModeValue("Mode","None",new String[] {
            "None",
            "Blink",
            "NoMoveOnClick"
    });
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
        registerValue(mode);
        registerValue(aac);
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        IPacket<?> packet = event.getPacket();
        if (event.packet instanceof CEntityActionPacket) {
            if (((CEntityActionPacket) event.packet).getAction() == CEntityActionPacket.Action.START_SPRINTING) {
                if (aac.isEnable()) event.cancelable = true;
            }
        }
        if (packet instanceof SOpenWindowPacket || (packet instanceof CClientStatusPacket && ((CClientStatusPacket) packet).getStatus() == CClientStatusPacket.State.REQUEST_STATS)) {
            open = true;
        }
        if (packet instanceof SCloseWindowPacket || packet instanceof CCloseWindowPacket) {
            open = false;
        }
        switch (mode.getValue()) {
            case "Blink":
                if (packet instanceof CPlayerPacket) {
                    if (open) {
                        packets.add(packet);
                        event.setCancelled();
                    } else if (!packets.isEmpty()) {
                        packets.add(packet);
                        event.setCancelled();
                        packets.forEach(mc.getConnection()::sendPacketNOEvent);
                        packets.clear();
                    }
                }
                break;
            case "NoMoveOnClick":
                if (mc.currentScreen instanceof ContainerScreen<?>) {
                    if (packet instanceof CClickWindowPacket) {
                        mc.player.setMotion(0,0,0);
                    }
                }
                break;
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

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
