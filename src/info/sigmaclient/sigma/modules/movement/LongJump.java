package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.CustomModeValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.movement.longjumps.impl.WatchDogLongjump;
import info.sigmaclient.sigma.modules.render.ESP;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class LongJump extends Module {
    public CustomModeValue type = new CustomModeValue("Type", "WatchDog", new Module[]{
            new WatchDogLongjump(this)
    });


    public LongJump() {
        super("LongJump", Category.Movement, "Jump but infinite blocks");
     registerValue(type);
    }

    @Override
    public void onEnable() {
        this.type.getCurrent().onEnable();
        super.onEnable();
    }

     @Override
    public void onDisable() {
         this.type.getCurrent().onDisable();
         super.onDisable();
    }

    @EventTarget
    public void onPacket(PacketEvent event){
        this.type.getCurrent().onPacketEvent(event);
    }
    @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        this.type.getCurrent().onUpdateEvent(event);
    }
}
