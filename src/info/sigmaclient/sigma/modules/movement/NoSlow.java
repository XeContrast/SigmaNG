package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.event.impl.player.SlowDownEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.item.SwordItem;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class NoSlow extends Module {
    public ModeValue mode = new ModeValue("Mode", "Vanilla", new String[]{
            "Vanilla",
            "Grim",
            "Intave"
    });
    public static NumberValue forward = new NumberValue("Forward", 1f, 0.2f, 1.0f, NumberValue.NUMBER_TYPE.FLOAT);
    public static NumberValue strafe = new NumberValue("Strafe", 1f, 0.2f, 1.0f, NumberValue.NUMBER_TYPE.FLOAT);
    public static BooleanValue sprint = new BooleanValue("Sprint", true);

    public NoSlow() {
        super("NoSlow", Category.Movement, "No slow for use items");
        registerValue(mode);
        registerValue(forward);
        registerValue(strafe);
        registerValue(sprint);
    }

    @EventTarget
    public void SlowDownEvent(SlowDownEvent event) {
        event.forward = forward.getValue().floatValue();
        event.strafe = strafe.getValue().floatValue();
        event.sprint = sprint.isEnable();
    }

    @EventTarget
    public void sendPacket(MotionEvent event) {
        if (mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem && mc.gameSettings.keyBindUseItem.pressed) {
            switch (mode.getValue()) {
                case "Grim":
                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 7 + 2));
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    break;
                case "Intave":
                    if (event.isPre())
                        Objects.requireNonNull(mc.getConnection()).sendPacketNOEvent(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    break;
            }
        }
        if (mc.player.getHeldItem(Hand.MAIN_HAND).isFood() && mc.gameSettings.keyBindUseItem.pressed) {
            switch (mode.getValue()) {
                case "Grim":
                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 7 + 2));
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    System.out.println("C09");
                    break;
                case "Intave":
                    mc.player.connection.getNetworkManager().sendPacketNOEvent(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
                    System.out.println("C07");
                    break;
            }
        }
    }
}