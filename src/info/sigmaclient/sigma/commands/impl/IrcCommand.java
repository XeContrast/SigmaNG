package info.sigmaclient.sigma.commands.impl;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.commands.Command;
import info.sigmaclient.sigma.modules.misc.Irc;
import info.sigmaclient.sigma.premium.PremiumManager;
import info.sigmaclient.sigma.utils.ChatUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class IrcCommand extends Command {
    @Override
    public void onChat(String[] args, String joinArgs) {
        if(!SigmaNG.getSigmaNG().moduleManager.getModule(Irc.class).enabled || ((Irc) SigmaNG.getSigmaNG().moduleManager.getModule(Irc.class)).socket == null)return;
        PrintWriter out = null;
        try {
            out = new PrintWriter(((Irc) SigmaNG.getSigmaNG().moduleManager.getModule(Irc.class)).socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(out == null)return;
        if(args.length < 2){
            sendUsages();
            return;
        }

        if(args.length == 2) {
            String message = args[1];
            if(!args[0].startsWith("chat")){
                sendUsages();
                return;
            }

            out.println(message);
            ChatUtils.sendMessageWithIrc(PremiumManager.userName + " : " + message);

        }else {
            if(!args[0].startsWith("chat")){
                sendUsages();
                return;
            }
            StringBuilder message = new StringBuilder();
            for(String str : args){
                if(str.startsWith("chat"))continue;
                message.append(" ").append(str);
            }
            out.println(message);
            ChatUtils.sendMessageWithIrc(PremiumManager.userName + " : " + message);
        }
    }

    @Override
    public String usages() {
        return "chat message/chat @someone message";
    }

    @Override
    public String describe() {
        return "irc Chat.";
    }

    @Override
    public String[] getName() {
        return new String[]{"irc"};
    }
}
