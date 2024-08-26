package info.sigmaclient.sigma.modules.combat;

import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.AttackEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.world.Explosion;

public class SuperKnockBack extends Module {
    public ModeValue mode = new ModeValue("Mode","Normal",new String[]{
            "Normal",
            "SprintReset",
    });
    public NumberValue hurttimr = new NumberValue("HurtTime",10,0,10, NumberValue.NUMBER_TYPE.INT);
    public int tick;
    public SuperKnockBack() {
        super("SuperKnockBack", Category.Combat, "Add Attack KnockBack");
        registerValue(mode);
        registerValue(hurttimr);
    }

    @EventTarget
    public void onAttackEvent(AttackEvent event) {
        if (event.LivingEntity instanceof LivingEntity && ((LivingEntity) event.LivingEntity).hurtTime <= hurttimr.getValue().intValue()) {
            switch (mode.getValue()) {
                case "SprintReset":
                    mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                    System.out.println("C0B Reset");
                    break;
                case "Normal":
                    if (mc.player.isSprinting()) {
                        mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                    }
                    mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                    mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                    mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                    mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                    System.out.println("C0B Silent");

                    mc.player.setSprinting(true);
                    break;
            }
        }
    }
}
