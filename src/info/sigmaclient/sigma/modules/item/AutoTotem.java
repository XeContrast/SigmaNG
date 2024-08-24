//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\1\Desktop\Minecraft-Deobfuscator3000-1.2.3\config"!

package info.sigmaclient.sigma.modules.item;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;


public class AutoTotem extends Module {
    private final NumberValue delay = new NumberValue("Delay", 0, 0, 20, NumberValue.NUMBER_TYPE.INT);
    private final NumberValue health = new NumberValue("Health", 5, 0, 20, NumberValue.NUMBER_TYPE.INT);

    public AutoTotem() {
        super("AutoTotem", Category.Item, "Auto use totem");
     registerValue(delay);
     registerValue(health);
    }
    private int nextTickSlot;
    private int timer;
    private boolean wasTotemInOffhand;
    public static boolean movingTotem = false;

     @Override
    public void onDisable() {
        movingTotem = false;
        super.onDisable();
    }

    @Override
    public void onEnable()
    {
        nextTickSlot = -1;
        timer = 0;
        wasTotemInOffhand = false;
    }

    @EventTarget
    public void onUpdateEvent(UpdateEvent e)
    {
        if(e.isPost()) return;
        movingTotem = false;
        if(mc.player.getHealth() > health.getValue().floatValue()) return;
        movingTotem = true;
        finishMovingTotem();

        PlayerInventory inventory = mc.player.inventory;
        int nextTotemSlot = searchForTotems(inventory);

        ItemStack offhandStack = inventory.getStackInSlot(40);
        if(isTotem(offhandStack))
        {
            wasTotemInOffhand = true;
            return;
        }

        if(wasTotemInOffhand)
        {
            timer = delay.getValue().intValue();
            wasTotemInOffhand = false;
        }
        if(nextTotemSlot == -1)
            return;

        if(timer > 0)
        {
            timer--;
            return;
        }

        moveTotem(nextTotemSlot, offhandStack);
    }

    private void moveTotem(int nextTotemSlot, ItemStack offhandStack)
    {
        boolean offhandEmpty = offhandStack == ItemStack.EMPTY;
        mc.playerController.windowClickFixed(
                mc.player.container.windowId,
                nextTotemSlot,0, ClickType.PICKUP,
                mc.player,
                false);
        mc.playerController.windowClickFixed(
                mc.player.container.windowId,
                45,0, ClickType.PICKUP,
                mc.player,
                false);
        mc.playerController.windowClickFixed(
                mc.player.container.windowId,
                nextTotemSlot,0, ClickType.PICKUP,
                mc.player,
                false);
        if(!offhandEmpty)
            nextTickSlot = nextTotemSlot;
    }

    private void finishMovingTotem()
    {
        if(nextTickSlot == -1)
            return;
        nextTickSlot = -1;
    }

    private int searchForTotems(PlayerInventory inventory)
    {
        int nextTotemSlot = -1;

        for(int slot = 0; slot <= 36; slot++)
        {
            if(!isTotem(inventory.getStackInSlot(slot)))
                continue;

            if(nextTotemSlot == -1)
                nextTotemSlot = slot < 9 ? slot + 36 : slot;
        }

        return nextTotemSlot;
    }

    private boolean isTotem(ItemStack stack)
    {
        return stack.getItem().getTranslationKey().contains("totem");
    }
}
