package info.sigmaclient.sigma.sigma5;

import net.minecraft.client.Minecraft;

public class SelfDestructManager {
    public static boolean destruct = false;
    public static String key = "";
    public static void destruct(){
        Minecraft mc = Minecraft.getInstance();
        mc.ingameGUI.getChatGUI().clearChatMessages2();
        destruct = true;
    }
    public static boolean isInVaildMessage(String str){
        if(destruct) return false;
        if(str.contains("§f[§6Sigma§f] §7") || str.contains("[Sigma] ") || str.contains("Script Module: ")){
            return true;
        }
        if(str.startsWith(".")){
            return true;
        }
        return false;
    }
    public static boolean isInVaildSend(String str){
        if(str.startsWith(".")){
            return true;
        }
        return false;
    }
}
