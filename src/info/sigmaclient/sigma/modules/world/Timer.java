package info.sigmaclient.sigma.modules.world;

import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.ClickEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.Variable;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Objects;


public class Timer extends Module {
    public static NumberValue timer = new NumberValue("Timer", 1.0, 0.1, 10.0, NumberValue.NUMBER_TYPE.FLOAT);
    public static BooleanValue watchdog = new BooleanValue("Watchdog Bypass", false);
    public static BooleanValue offGround = new BooleanValue("OffGround", false){
        @Override
        public boolean isHidden() {
            return !watchdog.isEnable();
        }
    };

    public static BooleanValue matrix = new BooleanValue("Matrix Bypass", false);
    public static NumberValue multi = new NumberValue("Store Ticks", 40, 10, 100, NumberValue.NUMBER_TYPE.FLOAT);
    public static BooleanValue debug = new BooleanValue("debug", true);

    public Timer() {
        super("Timer", Category.World, "Changes game speed.");
        registerValue(timer);
        registerValue(watchdog);
        registerValue(offGround);
        registerValue(matrix);
        registerValue(multi);
        registerValue(debug);
    }
    public ArrayList<IPacket<?>> packets = new ArrayList<>();
    private double prevY = 0;
    private int tick = 0;
    float lTimer = 0;
    private float timerV = 0;
    int packet = 0;
    public static float violation = 0;
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(event.packet instanceof CPlayerPacket){
            //packets.add(event.packet);
            packet++;
            //event.cancelable = true;
        }
        
    }

    public void clear(){
        packets.forEach(Objects.requireNonNull(mc.getConnection())::sendPacketNOEvent);
        packets.clear();
    }

  @EventTarget
    public void onUpdateEvent(MotionEvent event){
        if(Variable.stop_timer){
            Variable.stop_timer = false;
            mc.timer.setTimerSpeed(1);
            lTimer = mc.timer.getTimerSpeed();
            return;
        }
        if(event.isPre()){
            if(matrix.isEnable()){
                if (violation < multi.getValue().floatValue() / timer.getValue().floatValue()) {
                    violation += 1;
                    violation = MathHelper.clamp(violation, 0.0f, multi.getValue().floatValue() / timer.getValue().floatValue());
                } else {
                    flagDisable();
                }
            }
        }

        if (watchdog.isEnable()) {
            if (packet < 5 && ((prevY == mc.player.getPosY() && mc.player.onGround) || (offGround.isEnable() && mc.player.fallDistance > 0 && mc.player.fallDistance < 0.5f))) {
                if(mc.player.getMotion().x != 0 || mc.player.getMotion().z != 0) {
                    timerV = timerV + (timer.getValue().floatValue() - timerV) / 10;
                    if(debug.isEnable()) {
                        ChatUtils.sendMessageWithPrefix("Timer : " + timerV);
                    }
                    mc.timer.setTimerSpeed(timerV);
                    lTimer = mc.timer.getTimerSpeed();
                }else {
                    mc.timer.setTimerSpeed(1);
                    lTimer = mc.timer.getTimerSpeed();
                    timerV = 1;
                }
            } else {
                timerV = Math.max(timerV - 1,0)/20 + 1;
                mc.timer.setTimerSpeed(0.9f);
                lTimer = mc.timer.getTimerSpeed();
                clear();
                if (packet > 40) {
                    packet = 0;
                }
                packet++;
            }
        }
        if(timerV > timer.getValue().floatValue()){
            timerV = 1;
            mc.timer.setTimerSpeed(1);
            lTimer = mc.timer.getTimerSpeed();
            packet = 6;
        }
        prevY = mc.player.getPosY();
       
    }

    @EventTarget
    public void onClickEvent(ClickEvent event) {
        if(tick % 200 == 0){
            mc.timer.setTimerSpeed(0.01f);
        }else {
            mc.timer.setTimerSpeed(lTimer);
        }
        tick++;
        
    }

    @Override
    public void onEnable() {
        mc.timer.setTimerSpeed(timer.getValue().floatValue());
        lTimer = mc.timer.getTimerSpeed();
        tick = 0;
        timerV = 1;
        packet = 0;
        prevY = mc.player.getPosY();
        super.onEnable();
    }

     @Override
    public void onDisable() {
        clear();
        if(lTimer == mc.timer.getTimerSpeed()) {
            mc.timer.setTimerSpeed(1.0f);
        }
        tick = 0;
        super.onDisable();
    }
}
