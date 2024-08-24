package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.ClickEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.premium.PremiumManager;
import info.sigmaclient.sigma.utils.ChatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Irc extends Module {
    public Irc() {
        super("Irc", Category.Misc, "Chat");
    }
    public Socket socket = null;
    @Override
    public void onEnable() {
//        try {
//            socket = new Socket("154.23.241.134", 12345);
//            PrintWriter out = new PrintWriter(((Irc) SigmaNG.getSigmaNG().moduleManager.getModule(Irc.class)).socket.getOutputStream(), true);
//            out.println("/ligon "+ PremiumManager.userName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        try {
            if(socket != null) {
                socket.close();
                socket = null;
                ChatUtils.sendMessageWithIrc("IRC服务器已断开");
            }
        } catch (IOException ignored) {

        }

        super.onDisable();
    }

    @EventTarget
    public void onTick(ClickEvent event){
        if(mc.player == null || mc.world == null)return;
        if(mc.player.ticksExisted % 10 == 0) {
            new Thread(() -> {
                try {
                    if (socket == null) {
                        socket = new Socket("154.23.241.134", 10086);
                        PrintWriter out = new PrintWriter(((Irc) SigmaNG.getSigmaNG().moduleManager.getModule(Irc.class)).socket.getOutputStream(), true);
                        out.println("/ligon " + PremiumManager.userName);
                        return;
                    }
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        String response = in.readLine();
                        if (response != null) {
                            ChatUtils.sendMessageWithIrc(response);
                        }
                    } catch (IOException ignored) {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
