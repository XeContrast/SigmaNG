package info.sigmaclient.sigma.commands.impl;

import info.sigmaclient.sigma.commands.Command;
import info.sigmaclient.sigma.utils.ChatUtils;
import net.minecraft.util.math.MathHelper;

public class HClipCommand extends Command {
    @Override
    public void onChat(String[] args, String joinArgs) {
        if(args.length != 1){
            sendUsages();
            return;
        }
        float distance = 0;
        try{
            distance = Float.parseFloat(args[0]);
        } catch (Exception e){
            ChatUtils.sendMessageWithPrefix("Can't HClip: " + args[0]);
            return;
        }

        float yaw = mc.player.rotationYaw;

        float yawRadians = (float) Math.toRadians(yaw);

        double xOffset = -MathHelper.sin(yawRadians) * distance;
        double zOffset = MathHelper.cos(yawRadians) * distance;

        mc.player.setPosition(
                mc.player.getPosX() + xOffset,
                mc.player.getPosY(),
                mc.player.getPosZ() + zOffset
        );

        ChatUtils.sendMessageWithPrefix("HClip to " + mc.player.getPosX() + ", " + mc.player.getPosZ());
    }

    @Override
    public String usages() {
        return "[blocks]";
    }

    @Override
    public String describe() {
        return "Horizontal clip in the direction the player is facing.";
    }

    @Override
    public String[] getName() {
        return new String[]{"hclip"};
    }
}
