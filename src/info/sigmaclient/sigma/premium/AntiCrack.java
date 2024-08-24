package info.sigmaclient.sigma.premium;

import info.sigmaclient.sigma.modules.movement.flys.impl.VulcanFly;
import info.sigmaclient.sigma.modules.movement.speeds.impl.GrimSpeed;
import net.minecraft.client.Minecraft;
import top.fl0wowp4rty.phantomshield.annotations.Native;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Native

public class AntiCrack extends Thread{
    public static boolean isPremium = false;

    @Override
    public void run() {
        int i = 0;
        while(!Thread.interrupted()){
            if(true){
                try {
                    if(isPremium != PremiumManager.isPremium && PremiumManager.isPremium){
                        if(Minecraft.getInstance().player != null){
                            i ++;
                            if(Minecraft.getInstance().timer.tickLength != 99999999 && i % 10 == 0) {
                                Minecraft.getInstance().timer.tickLength = 99999999;
                            }else{
                                Minecraft.getInstance().timer.setTimerSpeed(1);
                            }
                        }
                    }else if(isPremium){
                    }
                } catch (Exception e) {
                    isPremium = false;
                    e.printStackTrace();
                    PremiumManager.userName = "Log in";
                    PremiumManager.token = "";
                    try {
                        Method method = PremiumManager.class.getDeclaredMethod("reset");
                        method.invoke(null);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                        GrimSpeed.premium = null;
                        VulcanFly.premium1 = null;
                        VulcanFly.premium2 = null;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        super.run();
    }
}
