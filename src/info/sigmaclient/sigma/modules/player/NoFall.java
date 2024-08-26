package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.player.PlayerUtil;
import net.minecraft.network.play.client.CPlayerPacket;


public class NoFall extends Module {
    public ModeValue type = new ModeValue("Type", "Delay", new String[]{"Delay", "Vulcan", "Spoof","NoGround","Extra","Blink","Sole","FastFall"});
    private final NumberValue FallDis = new NumberValue("fallDistance",3,2,6, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return !(type.is("Extra") ||type.is("Blink"));
        }
    };
    private boolean prevGround = false;

    public NoFall() {
        super("NoFall", Category.Player, "No fall damage.");
     registerValue(type);
     registerValue(FallDis);
    }
    private float fallDistance = 0;

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            if(mc.player.fallDistance >= 2){
                switch (type.getValue()){
                    case "Spoof":
                        event.onGround = true;
                        break;
                    case "Delay":
                        if(mc.player.ticksExisted % 5 == 0)
                            event.onGround = true;
                        break;
                    case "Vulcan":
                        if(mc.player.ticksExisted % 2 == 0) {
                            double y = mc.player.getPosY();
                            y = y - (y % (0.015625));
                            event.y = y;
                            event.onGround = true;
                        }
                        break;
                }
            }
            switch (type.getValue()) {
                case "Extra":
                    if(!mc.player.onGround) {
                        fallDistance += (float) Math.max(mc.player.fallDistance - mc.player.getMotion().y,0);
                        if (fallDistance >= FallDis.getValue().intValue()) {
                            mc.timer.setTimerSpeed(0.5f);
                            mc.getConnection().sendPacket(new CPlayerPacket(true));
                            fallDistance = 0;
                        }else {
                            mc.timer.setTimerSpeed(1);
                        }
                    }else {
                        mc.timer.setTimerSpeed(1);
                    }
                    break;
                case "Sole":
                    if(!mc.player.onGround && PlayerUtil.isBlockUnder() && mc.player.fallDistance > 3) {
                        fallDistance += (float) Math.max(mc.player.fallDistance - mc.player.getMotion().y,0);
                        if (fallDistance > 0 && PlayerUtil.getBlockUnderDist() > 4) {
                            mc.timer.setTimerSpeed(0);
                            mc.getConnection().sendPacketNOEvent(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() - PlayerUtil.getBlockUnderDist(), mc.player.getPosZ(), false));
                            event.y = mc.player.getPosY() - PlayerUtil.getBlockUnderDist();
                        }
                    } else {
                        fallDistance = 0;
                        mc.timer.setTimerSpeed(1);
                    }
                    break;
                case "FastFall":
                    if(!mc.player.onGround && PlayerUtil.isBlockUnder()) {
                        if (mc.player.fallDistance > 0 && mc.player.fallDistance % 3 == 0) {
                            event.y = mc.player.getPosY() - PlayerUtil.getBlockUnderDist() + 1;
                            event.onGround = true;
                        }
                    }
                    break;
                case "NoGround":
                    event.onGround = false;
                    break;
                case "Blink":
                    if(!mc.player.onGround && PlayerUtil.isBlockUnder()){
                        fallDistance += (float) Math.max(mc.player.fallDistance - mc.player.getMotion().y,0);
                    }else {
                        fallDistance = 0;
                    }
                    if(event.onGround){
                        //BlinkStop
                    }
                    else if(prevGround){
                        if(!mc.player.onGround && !PlayerUtil.isBlockUnder((int) (double) FallDis.getValue().intValue()) && PlayerUtil.isBlockUnder()) {
                            //BlinkStart
                        }
                    }
                    else if(fallDistance >= FallDis.getValue().intValue() && PlayerUtil.isBlockUnder()){
                        mc.player.getMotion().x = 0;
                        mc.player.getMotion().y = 0;
                        mc.player.getMotion().z = 0;
                        mc.getConnection().sendPacket(new CPlayerPacket(true));
                        fallDistance = 0;
                    }
                    prevGround = event.onGround;
                    break;
            }
        }
       
    }

    @Override
    public void onDisable() {
        super.onDisable();
        //if(type.is("Sole")){
            mc.timer.setTimerSpeed(1);
        //}
    }

    @Override
    public void onEnable() {
        prevGround = mc.player.onGround;
        super.onEnable();
    }
}
