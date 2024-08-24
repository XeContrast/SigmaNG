package info.sigmaclient.sigma.commands.impl;

import info.sigmaclient.sigma.commands.Command;
import info.sigmaclient.sigma.sigma5.SelfDestructManager;
import info.sigmaclient.sigma.utils.ChatUtils;

import java.util.Random;

public class SelfDestructCommand extends Command {
    @Override
    public void onChat(String[] args, String joinArgs) {
        if(args.length == 1 && args[0].equals("confirm")){
            SelfDestructManager.destruct();
            return;
        }
        SelfDestructManager.key = (new Random().nextInt(999)+1000) + "";
        ChatUtils.sendMessageWithPrefix("if you want to self-destruct, please do .selfdestruct confirm. You can input a number " + SelfDestructManager.key + " to cancel self-destruct.");
    }

    @Override
    public String usages() {
        return "";
    }

    @Override
    public String describe() {
        return "self-destruct.";
    }

    @Override
    public String[] getName() {
        return new String[]{"selfdestruct"};
    }
}
