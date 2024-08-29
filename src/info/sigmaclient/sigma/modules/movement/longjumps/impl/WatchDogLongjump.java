package info.sigmaclient.sigma.modules.movement.longjumps.impl;

import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.movement.LongJump;
import info.sigmaclient.sigma.modules.movement.longjumps.LongJumpModule;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

import java.util.Objects;

public class WatchDogLongjump extends LongJumpModule {
    public WatchDogLongjump(LongJump longJump) {
        super("WatchDog", "WatchDog Fireball", longJump);
    }

    private int lastSlot = -1;
    private int ticks = -1;
    private boolean sentPlace;
    private int initTicks;


    @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(initTicks < 2){
            mc.player.getMotion().x = 0;
            mc.player.getMotion().z = 0;
        }
        if(event.isPost())return;
        if (initTicks == 0) {
            event.yaw = (mc.player.rotationYaw - 180);
            event.pitch = (89);
            int fireballSlot = getFireball();
            if (fireballSlot != -1 && fireballSlot != mc.player.inventory.currentItem) {
                lastSlot = mc.player.inventory.currentItem;
                mc.player.inventory.currentItem = fireballSlot;
            }
        }
        if (initTicks == 1) {
            if (!sentPlace) {
                Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                sentPlace = true;
            }
        } else if (initTicks == 2) {
            if (lastSlot != -1) {
                mc.player.inventory.currentItem = lastSlot;
                lastSlot = -1;
            }
            ticks = 0;
        }
        if (initTicks > 2 && mc.player.onGround) {
            this.parent.toggle();
            return;
        }
        if (initTicks > 2) {
            MovementUtils.strafing(1.5f);
            ticks++;
        }

        if (initTicks < 3) {
            initTicks++;
        }

        if (initTicks > 2) {
            if (ticks > 1) {
                ticks = 0;
                return;
            }
            ticks++;
            MovementUtils.strafing(1.5);
            ChatUtils.sendMessageWithPrefix("bbbbbbb");

        }
    }

    private int getFireball() {
        int a = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = mc.player.inventory.getStackInSlot(i);
            if (getStackInSlot.getItem() == Items.FIRE_CHARGE) {
                a = i;
                break;
            }
        }
        return a;
    }


    public void onDisable() {
        if (lastSlot != -1) {
            mc.player.inventory.currentItem = lastSlot;
        }
        ticks = lastSlot = -1;
        initTicks = 0;
    }

    public void onEnable() {
        if (getFireball() == -1) {
            ChatUtils.sendMessageWithPrefix("Could not find Fireball");
            this.enabled = false;
            return;
        }
        initTicks = 0;
    }
}
