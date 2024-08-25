package info.sigmaclient.sigma.modules.combat;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.AttackEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.RayTraceResult;

import java.util.Objects;
import java.util.Random;


public class Criticals extends Module {
    public static ModeValue mode = new ModeValue("Mode", "Packet", new String[]{
            "Blocksmc",
            "OldNCP",
            "GrimAC",
            "Packet",
            "Hovered",
            "Vulcan",
            "Test",
            "Motion",
            "ColdPVP",
            "Hypixel",
            "Hypixel2",
            "Hypixel3"
    });
    public static NumberValue motion = new NumberValue("motion", 0.2, 0, 1, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !mode.is("Motion");
        }
    };
    public static NumberValue hurtTime = new NumberValue("HurtTime", 1, 1, 10, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return mode.is("Hypixel") ||mode.is("Hypixel2") || mode.is("Hypixel3");
        }
    };

    public Criticals() {
        super("Criticals", Category.Combat, "Critical on the ground.");
     registerValue(mode);
     registerValue(motion);
     registerValue(hurtTime);
    }
    public static Random random = new Random();
    public static int attacked = 0;
    public int stage = 0;
    int cri_t = 0;
    double y = 0;
    boolean lastGround;
    int ground = 0;
    float timer = mc.timer.getTimerSpeed();

    @EventTarget
    public void onAttackEvent(AttackEvent event) {
        if(!event.post){
            if(event.LivingEntity instanceof LivingEntity entity) {
                criticalsEntity(entity);
            }
        }
    }

    @EventPriority(20)
    @EventTarget
    public void onUpdateEvent(UpdateEvent e) {
        if (e.isPost()) {
            return;
        }
        if (e.onGround != lastGround && !e.onGround) {
            ground++;
        }
        lastGround = mc.player.onGround;
        suffix = mode.getValue();
        attacked++;
        switch (mode.getValue()) {
            case "Motion":
                if (Killaura.attackTarget != null && mc.player.onGround) {
                    mc.player.getMotion().y = motion.getValue().floatValue();
                }
                break;
            case "Blocksmc":
                if (attacked == 0) e.y += 0.012343;
                break;
            case "Hovered":
                double ypos = mc.player.getPosY();
                if (mc.player.onGround) {
                    e.onGround = false;
                    if (stage == 0) {
                        y = ypos + 1E-8;
                        e.onGround = true;
                    } else if (stage == 1)
                        y -= 5E-9;
                    else
                        y -= 4E-9;

                    if (y <= mc.player.getPosY()) {
                        stage = 0;
                        y = mc.player.getPosY();
                        e.onGround = true;
                    }
                    e.y = y;
                    stage++;
                } else
                    stage = 0;
                break;
            case "Test":
                if (attacked == 0) e.onGround = false;
                break;
            case "Hypixel":
                if (Objects.requireNonNull(mc.objectMouseOver).getType() == RayTraceResult.Type.ENTITY || Killaura.attackTarget != null) {
                    if (mc.player.fallDistance < 1.8) {
                        if(e.onGround) {
                            e.y += 0.00000000000000000000000000000001;
                        }
                        e.onGround = false;
                    }
                }
                break;
            case "Hypixel2":

                if (Objects.requireNonNull(mc.objectMouseOver).getType() == RayTraceResult.Type.ENTITY || Killaura.attackTarget != null) {


                    final int[] strafeTicks = new int[]{11,12,13};

                    if (!(mc.world.getBlockState(mc.player.getPosition().add(0, -0.25, 0)).getBlock() instanceof AirBlock)) {
                        for (final int allowedAirTick : strafeTicks) {
                            if (mc.player.offGroundTicks == allowedAirTick && mc.player.hurtTime == 0) {
                                mc.player.getMotion().y = 0;
                                break;
                            }
                        }
                    }
                }

                break;
            case "Hypixel3":
                if (Objects.requireNonNull(mc.objectMouseOver).getType() == RayTraceResult.Type.ENTITY || Killaura.attackTarget != null) {
                    if (e.onGround) {
                        e.onGround = false;
                        e.y += 0.00000000000000000001;
                        mc.player.getMotion().y = MovementUtils.getJumpBoostModifier(0.0000041);
                    }
                }
                break;
        }
    }
    static boolean attacking = false;
    public void criticalsEntity(LivingEntity LivingEntity){
        if(SigmaNG.SigmaNG.moduleManager.getModule(Criticals.class).enabled && (LivingEntity.hurtTime > hurtTime.getValue().longValue() || (mode.is("Hypixel") || mode.is("Hypixel2") || mode.is("Hypixel3")))){
            attacking = true;
            mc.player.onCriticalHit(LivingEntity);
            switch (mode.getValue()){
                case "Test", "Hypixel2", "Hypixel":
                    attacked = -1;
                    break;
                case "Dev2":
                    if(!mc.player.onGround) return;
                    addYPos(0.41999998688697815, false);
                    addYPos(0.0, false);
                    return;
                case "Dev3":
                    if(!mc.player.onGround) return;
                    addYPos(0.1, false);
                    addYPos(0.3, false);
                    addYPos(0.2, false);
                    addYPos(0.1, false);
                    return;
                case "Motion":
                    if(!mc.player.onGround) return;
                    return;
                case "OldNCP":
                    if(!mc.player.onGround) return;
                    double[] object = new double[]{
                            0.0625D,
                            0
                    };
                    for (double object3 : object) {
                        addYPos(object3, false);
                    }
                    return;
                case "Packet":
                    if(!mc.player.onGround) return;
                    addYPos(0.08, false);
                    addYPos(0.03, false);
                    return;
                case "Blocksmc":
                    if(!mc.player.onGround) return;
                    if (attacked > 6) {
                        addYPos(0.10749678457684, false);
                        addYPos(0.07348598467864, false);
                        attacked = -1;
                    }
                    return;
                case "ColdPVP":
                    if(!mc.player.onGround) return;
                    addYPos(0.41999998688697815, false);
                    addYPos(0.35457, false);
                    addYPos(0.14445, false);
                    break;
                case "Vulcan":
                    if(!mc.player.onGround) return;
                    if (attacked > 8) {
                        addYPos(0.16, false);
                        addYPos(0.083, false);
                        addYPos(0.003, false);
                        attacked = 0;
                    }
                    break;
                case "Hovered":
                    attacked = 1;
                    break;
                case "GrimAC":
                    if (!mc.player.onGround) {
                        addYPos(-0.0001,false);
                    }
            }
            cri_t++;
        }
    }

    @Override
    public void onEnable() {
        ground = 0;
        lastGround = mc.player.onGround;
        timer = mc.timer.getTimerSpeed();
        super.onEnable();
    }

     @Override
    public void onDisable() {
        cri_t = 0;
        mc.timer.setTimerSpeed(timer);
        super.onDisable();
    }

    public static void addYPos(double Y, boolean g){
        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() + Y, mc.player.getPosZ(), g));
    }
}
