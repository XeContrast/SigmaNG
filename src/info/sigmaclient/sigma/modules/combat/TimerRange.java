package info.sigmaclient.sigma.modules.combat;

import com.viaversion.viaversion.libs.fastutil.Pair;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.MotionEvent;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.movement.BlockFly;
import info.sigmaclient.sigma.process.impl.player.RotationManager;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TimerRange extends Module {
    private final NumberValue lagTicks;
    private final NumberValue timerTicks;
    private final NumberValue minRange;
    private final NumberValue maxRange;
    private final NumberValue delay;
    private final NumberValue fov;
    private final BooleanValue ignoreTeammates;
    private final BooleanValue onlyOnGround;
    private final BooleanValue clearMotion;
    private final BooleanValue notWhileKB;
    private final BooleanValue notWhileScaffold;

    private State state = State.NONE;
    private int hasLag = 0;
    private long lastTimerTime = -1;
    private final Queue<IPacket<?>> delayedPackets = new ConcurrentLinkedQueue<>();
    private float yaw, pitch;
    private double motionX, motionY, motionZ;


    public TimerRange() {
        super("TimerRange", Category.Combat, "Use timer help you to beat opponent.");
        this.registerValue(lagTicks = new NumberValue("Lag ticks", 2, 0, 10, NumberValue.NUMBER_TYPE.INT));
        this.registerValue(timerTicks = new NumberValue("Timer ticks", 2, 0, 10, NumberValue.NUMBER_TYPE.INT));
        this.registerValue(minRange = new NumberValue("Min range", 3.6, 0, 8, NumberValue.NUMBER_TYPE.FLOAT));
        this.registerValue(maxRange = new NumberValue("Max range", 5, 0, 8, NumberValue.NUMBER_TYPE.FLOAT));
        this.registerValue(delay = new NumberValue("Delay", 500, 0, 4000, NumberValue.NUMBER_TYPE.INT));
        this.registerValue(fov = new NumberValue("Fov", 180, 0, 360, NumberValue.NUMBER_TYPE.INT));
        this.registerValue(ignoreTeammates = new BooleanValue("Ignore teammates", true));
        this.registerValue(onlyOnGround = new BooleanValue("Only onGround", false));
        this.registerValue(clearMotion = new BooleanValue("Clear motion", false));
        this.registerValue(notWhileKB = new BooleanValue("Not while kb", false));
        this.registerValue(notWhileScaffold = new BooleanValue("Not while scaffold", true));
    }

    @Override
    public void onUpdateEvent(MotionEvent event) {
        switch (state) {
            case IGNORED:
                return;
            case NONE:
                if (shouldStart())
                    state = State.TIMER;
                break;
            case TIMER:
                state = State.IGNORED;
                for (int i = 0; i < (int) timerTicks.getValue(); i++) {
                    mc.player.tick();
                }
                state = State.TIMER;
                yaw = RotationManager.getRotYaw();
                pitch = RotationManager.getRotPitch();
                Vector3d motion = mc.player.getMotion();
                motionX = motion.x;
                motionY = motion.y;
                motionZ = motion.z;
                hasLag = 0;
                state = State.LAG;
                break;
            case LAG:
                RotationManager.setRotYaw(yaw);
                RotationManager.setRotPitch(pitch);

                if (hasLag >= (int) lagTicks.getValue())
                    done();
                else
                    hasLag++;
                break;
        }
    }

    @Override
    public void onPacketEvent(PacketEvent event) {
        switch (state) {
            case TIMER:
                synchronized (delayedPackets) {
                    delayedPackets.add(event.getPacket());
                    event.setCancelled();
                }
                break;
            case LAG:
                if (event.getPacket() instanceof CPlayerPacket) {
                    event.setCancelled();
                } else {
                    synchronized (delayedPackets) {
                        delayedPackets.add(event.getPacket());
                        event.setCancelled();
                    }
                }
                break;
        }
    }

    @Override
    public void onMoveEvent(@NotNull MoveEvent event) {
        if (state == State.LAG) {
            event.setCancelled();
            mc.player.setMotion(motionX, motionY, motionZ);
        }
    }

    @Override
    public void onDisable() {
        done();
    }

    private void done() {
        state = State.NONE;
        hasLag = 0;
        lastTimerTime = System.currentTimeMillis();

        synchronized (delayedPackets) {
            for (IPacket<?> p : delayedPackets) {
                mc.player.connection.sendPacket(p);
            }
            delayedPackets.clear();
        }

        if (clearMotion.isEnable()) {
            mc.player.setMotion(0, 0, 0);
        } else {
            mc.player.setMotion(motionX, motionY, motionZ);
        }
    }

    private boolean shouldStart() {
        if (mc.player == null || mc.world == null) return false;
        if (onlyOnGround.isEnable() && !mc.player.onGround) return false;
        if (notWhileKB.isEnable() && mc.player.hurtTime > 0) return false;
        if (notWhileScaffold.isEnable() && SigmaNG.getSigmaNG().moduleManager.getModule(BlockFly.class).enabled) return false;
        if (!MovementUtils.isMoving()) return false;
        if ((int) fov.getValue() == 0) return false;
        if (System.currentTimeMillis() - lastTimerTime < (int) delay.getValue()) return false;

        PlayerEntity target = mc.world.getPlayers().parallelStream()
                .filter(p -> p != mc.player)
                .filter(p -> !ignoreTeammates.isEnable() || !Teams.isTeam(p))
                .filter(p -> !SigmaNG.getSigmaNG().friendsManager.isFriend(p))
                .filter(p -> AntiBot.isNotBot(p.getUniqueID()))
                .map(p -> Pair.of(p, mc.player.getDistanceSqToEntity(p)))
                .min(Comparator.comparing(Pair::second))
                .map(Pair::first)
                .orElse(null);

        if (target == null) return false;

        if ((int) fov.getValue() < 360 && !RotationUtils.isVisibleFOV(target, (float) fov.getValue())) return false;

        double distance = target.getDistance(mc.player);
        return distance >= (double) minRange.getValue() && distance <= (double) maxRange.getValue();
    }

    enum State {
        NONE,
        TIMER,
        LAG,
        IGNORED
    }
}
