package info.sigmaclient.sigma.modules.player;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CPlayerPacket;

import java.util.ArrayList;


public class Blink extends Module {
    BooleanValue pulse = new BooleanValue("Pulse", false);
    NumberValue pulsePackets = new NumberValue("Pulse Count", 1, 1, 10, NumberValue.NUMBER_TYPE.INT);
    public Blink() {
        super("Blink", Category.Player, "WDF");
     registerValue(pulse);
     registerValue(pulsePackets);
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof CPlayerPacket || event.packet instanceof CConfirmTransactionPacket){
            packets.add(event.packet);
            event.cancelable = true;
        }
        
    }
    public ArrayList<IPacket<?>> packets = new ArrayList<>();
    @Override
    public void onEnable() {
        super.onEnable();
    }
    public void clear(){
        packets.forEach(mc.getConnection()::sendPacketNOEvent);
        packets.clear();
    }
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        if(event.isPre()){
            if(pulse.isEnable()){
                if(mc.player.ticksExisted % pulsePackets.getValue().intValue() == 0){
                    clear();
                }
            }
        }
       
    }

     @Override
    public void onDisable() {
        clear();
        super.onDisable();
    }
}
