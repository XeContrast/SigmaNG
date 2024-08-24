package info.sigmaclient.sigma.commands;

import info.sigmaclient.sigma.commands.impl.*;
import info.sigmaclient.sigma.sigma5.SelfDestructManager;
import info.sigmaclient.sigma.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;

import java.util.ArrayList;

public class CommandManager {
    ArrayList<Command> commands = new ArrayList<>();
    public void init(){
        commands.add(new BindCommand());
        commands.add(new VClipCommand());
        commands.add(new HClipCommand());
        commands.add(new SendMessageCommand());
        commands.add(new CrashCommand());
        commands.add(new IrcCommand());
        commands.add(new ConfigCommand());
        commands.add(new GPSCommand());
        commands.add(new ToggleCommand());
        commands.add(new FriendCommand());
        commands.add(new SelfDestructCommand());
    }
    public void getTab(){
        if(Minecraft.getInstance().currentScreen instanceof ChatScreen chatScreen && chatScreen.inputField != null){
            String text = chatScreen.inputField.getText();
            if(SelfDestructManager.destruct) return;
            if(text.startsWith(".")){
//                ArgumentBuilder<ISuggestionProvider, ? > s = LiteralArgumentBuilder.literal("sb");
//                RootCommandNode<ISuggestionProvider> build = (RootCommandNode)s.build();
//                Minecraft.getInstance().getConnection().commandDispatcher = new CommandDispatcher<>(build);
            }
        }
    }
    public boolean onChat(String str){
        if(str.equals(SelfDestructManager.key)){
            SelfDestructManager.destruct = false;
            return true;
        }
        if(SelfDestructManager.destruct) return false;
        if(str.startsWith(".")){
            String[] sp = str.split(" ");
            if(sp[0].equals(".help")){
                String m = "The help list of Sigma:\n\n";
                for(Command command : commands){
                    m +=  "§7            ." + command.getName()[0] + " - " + command.describe() + "\n\n";
                }
                ChatUtils.sendMessageWithPrefix(m.substring(0, m.length() - 2));
                return true;
            }
            for(Command command : commands){
                if(("." + command.getName()[0]).equals(sp[0])){
                    String j = str.replaceFirst(sp[0]+" ", "");
                    String[] last = j.split(" ");
                    command.onChat(last, j);
                    return true;
                }
            }
            ChatUtils.sendMessageWithPrefix("Unknow command: " + sp[0] + ", use .help get helps.");
            return true;
        }
        return false;
    }
}
