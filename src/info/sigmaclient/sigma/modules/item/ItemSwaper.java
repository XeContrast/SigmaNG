package info.sigmaclient.sigma.modules.item;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.world.Timer;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.TimerUtil;

public class ItemSwaper extends Module {
    private int slot = 0;
    private final TimerUtil timer = new TimerUtil();
    private final NumberValue delay = new NumberValue("Delay",500,100,5000, NumberValue.NUMBER_TYPE.INT);

    private final NumberValue Slot1 = new NumberValue("Slot-a",0,0,8, NumberValue.NUMBER_TYPE.INT);
    private final NumberValue Slot2 = new NumberValue("Slot-b",0,0,8, NumberValue.NUMBER_TYPE.INT);

    public ItemSwaper() {
        super("ItemSwaper", Category.Item, "Auto Swap Your current item");
        registerValue(Slot1);
        registerValue(Slot2);
        registerValue(delay);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        slot = Slot1.getValue().intValue();
    }

    @EventTarget
    private void onMotion(UpdateEvent event){

        if (event.isPost()) return;
        mc.player.inventory.currentItem = slot;
        if(timer.hasTimeElapsed(delay.getValue().intValue(),true)){
            ChatUtils.sendMessageWithPrefix(slot+"");
            if(slot == Slot1.getValue().intValue()) {
                slot = Slot2.getValue().intValue();
            }else {
                slot = Slot1.getValue().intValue();
            }
        }
    }

}
