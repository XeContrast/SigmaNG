/**
 * @Author Empty_0
 * @Date 2024/4/10
 */
package info.sigmaclient.sigma.modules.combat;


import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.ClickEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.movement.BlockFly;
import info.sigmaclient.sigma.modules.player.Blink;
import info.sigmaclient.sigma.utils.TimerUtil;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProjectileAura extends Module {
    public BooleanValue switchTarget = new BooleanValue("Switch Target", false);
    public NumberValue switchDelay = new NumberValue("Switch Delay", 600.0, 0.0, 1000.0, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return !switchTarget.getValue();
        }
    };

    public NumberValue delay = new NumberValue("Delay", 1000.0, 10.0, 2000.0, NumberValue.NUMBER_TYPE.INT);

    public NumberValue range = new NumberValue("Range", 5.0, 3.0, 8.0, NumberValue.NUMBER_TYPE.FLOAT);

    public NumberValue fov = new NumberValue("Fov", 360.0, 0.0, 360.0, NumberValue.NUMBER_TYPE.INT);

    public final BooleanValue
            players = new BooleanValue("Players", true),
            animals = new BooleanValue("Animals", false),
            mobs = new BooleanValue("Mobs", false),
            invisible = new BooleanValue("Invisible", false);

    public BooleanValue throw_egg = new BooleanValue("Eggs", true);

    public BooleanValue throw_snowball = new BooleanValue("Snow Ball", true);

    public BooleanValue silentSwitch = new BooleanValue("Item Silent", true);

    public BooleanValue movementFix = new BooleanValue("Movement Fix", true);

    public List<LivingEntity> targets = new ArrayList<>();
    public LivingEntity target;
    public Vector2f rotation;
    private int index;

    public TimerUtil timer = new TimerUtil();
    private final TimerUtil switchTimer = new TimerUtil();

    public ProjectileAura() {
        super("ProjectileAura",  Category.Combat,"Projectile Aura");
        registerValue(switchTarget);
        registerValue(switchDelay);
        registerValue(delay);
        registerValue(range);
        registerValue(fov);
        registerValue(players);
        registerValue(animals);
        registerValue(mobs);
        registerValue(invisible);
        registerValue(throw_egg);
        registerValue(throw_snowball);
        registerValue(silentSwitch);
        registerValue(movementFix);
    }

    @EventTarget
    private void onTick(ClickEvent event) {

        Killaura killAura = (Killaura) SigmaNG.getSigmaNG().moduleManager.getModule(Killaura.class);
        BlockFly scaffold = (BlockFly) SigmaNG.getSigmaNG().moduleManager.getModule(BlockFly.class);
        Blink blink = (Blink) SigmaNG.getSigmaNG().moduleManager.getModule(Blink.class);

        if (killAura.enabled && Killaura.attackTarget != null) {
            return;
        }

        if (scaffold.enabled || blink.enabled) {
            return;
        }

        getTargets();

        if (this.targets.size() > 1 && switchTarget.getValue()) {
            if (switchTimer.hasTimeElapsed(switchDelay.getValue().intValue())) {
                switchTimer.reset();
                ++this.index;
            }
        }
        if (this.index >= this.targets.size()) {
            this.index = 0;
        }

        if (!targets.isEmpty()) {
            if (switchTarget.getValue()) {
                target = targets.get(index);
            } else {
                target = targets.get(0);
            }
            if (!checkEntity(target)) {
                target = null;
            }
        } else target = null;
        int slot = -1;
        if (throw_egg.getValue() && getEggSlot() != -1) {
            slot = getEggSlot();
        } else if (throw_snowball.getValue() && getSnowballSlot() != -1) {
            slot = getSnowballSlot();
        }


        if (target != null && slot != -1) {
            double xDistance = target.getPosX() - mc.player.getPosX() + (target.getPosX() - target.lastTickPosX) * (double) (1 * 10.0f);
            double zDistance = target.getPosZ() - mc.player.getPosZ() + (target.getPosZ() - target.lastTickPosZ) * (double) (1 * 10.0f);
            float trajectoryTheta90 = (float) (Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
            float ve = 0.1F;
            ve = (ve * ve + ve * 2.0f) / 3.0f;
            ve = MathHelper.clamp(ve, 0.0f, 1.0f);
            double v = ve * 3.0f;
            float bowTrajectory = (float) ((double) ((float) (-Math.toDegrees(this.getLaunchAngle(target, v)))) - 3.8);
            if (trajectoryTheta90 <= 360.0f && bowTrajectory <= 360.0f) {
                RotationUtils.movementFixYaw = trajectoryTheta90;
                RotationUtils.movementFixPitch = bowTrajectory;
                RotationUtils.fixing = movementFix.getValue();
                RotationUtils.slient = true;

            }

            if (timer.hasTimeElapsed(delay.getValue().intValue())) {
                final int prevSlot = mc.player.inventory.currentItem;
                if (silentSwitch.getValue()) {
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(slot));
                    mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    mc.getConnection().sendPacket(new CHeldItemChangePacket(prevSlot));
                    mc.player.inventory.currentItem = prevSlot;
                } else {
                    mc.player.inventory.currentItem = slot;
                    KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getDefault());
                }
                timer.reset();
            }
        }
    }


    private float getLaunchAngle(LivingEntity targetEntity, double v) {
        double yDif = targetEntity.getPosY() + (double) (targetEntity.getEyeHeight() / 2.0f) - (mc.player.getPosY() + (double) mc.player.getEyeHeight());
        double xDif = targetEntity.getPosX() - mc.player.getPosX();
        double zDif = targetEntity.getPosZ() - mc.player.getPosZ();
        double xCoord = Math.sqrt(xDif * xDif + zDif * zDif);
        return this.theta(v + 2.0, xCoord, yDif);
    }

    private float theta(double v, double x, double y) {
        double yv = 2.0 * y * (v * v);
        double gx = 0.05000000074505806 * (x * x);
        double g2 = 0.05000000074505806 * (gx + yv);
        double insqrt = v * v * v * v - g2;
        double sqrt = Math.sqrt(insqrt);
        double numerator = v * v + sqrt;
        double numerator2 = v * v - sqrt;
        double atan1 = Math.atan2(numerator, 0.05000000074505806 * x);
        double atan2 = Math.atan2(numerator2, 0.05000000074505806 * x);
        return (float) Math.min(atan1, atan2);
    }

    public int getEggSlot() {

        for (int i = 0; i < 9; ++i) {
            if (!mc.player.inventory.hasItemStack(mc.player.inventory.getStackInSlot(i)) || !(mc.player.inventory.getStackInSlot(i).getItem() instanceof EggItem))
                continue;
            return i;
        }
        return -1;
    }

    public int getSnowballSlot() {
        for (int i = 0; i < 9; ++i) {
            if (!mc.player.inventory.hasItemStack(mc.player.inventory.getStackInSlot(i)) || !(mc.player.inventory.getStackInSlot(i).getItem() instanceof SnowballItem))
                continue;
            return i;
        }
        return -1;
    }


    private void getTargets() {
        targets.clear();

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof LivingEntity) {

                if (mc.player.getDistance(entity) <= range.getValue().floatValue() && checkEntity(entity)) {
                    targets.add((LivingEntity) entity);
                }
            }
        }
        targets.sort(Comparator.comparingDouble(RotationUtils::yawDist));
    }

    private boolean checkEntity(Entity entity) {
        Teams teams = (Teams) SigmaNG.getSigmaNG().moduleManager.getModule(Teams.class);
        if (entity == mc.player) {
            return false;
        }
        if (!mc.player.canEntityBeSeen(entity) && mc.player.getDistance(entity) >= range.getValue().floatValue()) {
            return false;
        }
        if (!(RotationUtils.isVisibleFOV(entity, fov.getValue().intValue()))) {
            return false;
        }


        if (entity.isInvisible() && invisible.getValue()) {
            return true;
        }

        if (entity instanceof PlayerEntity && players.getValue() && !Teams.isTeam((PlayerEntity) entity)) {
            return true;
        }

        if (entity instanceof MobEntity && mobs.getValue()) {
            return true;
        }

        if (entity instanceof AnimalEntity && animals.getValue()) {
            return true;
        }

        return false;
    }
}
