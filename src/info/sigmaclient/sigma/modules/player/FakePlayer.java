package info.sigmaclient.sigma.modules.player;

import com.mojang.authlib.GameProfile;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;

import java.util.UUID;


public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", Category.Player, "WDF");
    }
    RemoteClientPlayerEntity e = null;
    @Override
    public void onEnable() {
        e = new RemoteClientPlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), "sb"));
        e.copyLocationAndAnglesFrom(mc.player);
        e.setEntityId(114514);
        mc.world.addPlayer(114514, e);
        super.onEnable();
    }
     @Override
    public void onDisable() {
        if(e != null)
            mc.world.removeEntityFromWorld(e.getEntityId());
        super.onDisable();
    }
}
