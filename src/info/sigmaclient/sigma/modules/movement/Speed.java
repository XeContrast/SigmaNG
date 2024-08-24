package info.sigmaclient.sigma.modules.movement;

import baritone.api.event.events.TickEvent;
import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.CustomModeValue;
import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.*;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.modules.movement.speeds.impl.*;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.MathHelper;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class Speed extends Module {
    public CustomModeValue mode = new CustomModeValue("Type", "Strafe", new Module[]{
            new HypixelSpeed(this),
            new LegitSpeed(this),
            new NCPSpeed(this),
            new VerusSpeed(this),
            new VulcanSpeed(this),
            new StrafeSpeed(this),
            new VanillaSpeed(this),
            new CubecraftSpeed(this),
            new BlocksMCSpeed(this),
            new GrimSpeed(this),
            new Matrix7Speed(this)
    });
    public BooleanValue flagDisable = new BooleanValue("Lag back checker", false);
    public BooleanValue lowHop = new BooleanValue("Low Hop", false){
        @Override
        public boolean isHidden() {
            return !(mode.is("BlocksMC") || mode.is("Vulcan"));
        }
    };
    public BooleanValue timer = new BooleanValue("Timer", false){
        @Override
        public boolean isHidden() {
            return !(mode.is("BlocksMC") || mode.is("Vulcan"));
        }
    };
    public ModeValue hypixelMode = new ModeValue("Mode", "Ground", new String[]{"Ground", "FakeStrafe","SemiStrafe", "EternityGS", "EternityF", "Real","Autism","LessBHop","SemiBHop","BunnyHop"}){
        @Override
        public boolean isHidden() {
            return !mode.is("Hypixel");
        }
    };

    public BooleanValue sneak = new BooleanValue("Sneak",false){
        @Override
        public boolean isHidden() {
            return !mode.is("Hypixel") || !hypixelMode.is("BunnyHop") ;
        }
    };

    public NumberValue strafeTick = new NumberValue("StrafeTick", 1, 1, 25, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return !mode.is("Hypixel") || !hypixelMode.is("BunnyHop") ;
        }
    };

    public NumberValue speed = new NumberValue("Speed", 1, 0, 10, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !mode.is("Vanilla");
        }
    };
    public NumberValue cubeSpeed = new NumberValue("Multi", 1.5f, 1, 2, NumberValue.NUMBER_TYPE.FLOAT){
        @Override
        public boolean isHidden() {
            return !mode.is("Cubecraft");
        }
    };
    public BooleanValue Speed = new BooleanValue("Speed", false){
        @Override
        public boolean isHidden() {
            return !(mode.is("BSpeed"));
        }
    };
    public BooleanValue sprint = new BooleanValue("sprint", true){
        @Override
        public boolean isHidden() {
            return !(mode.is("BSpeed"));
        }
    };
    public BooleanValue blink = new BooleanValue("blink", true){
        @Override
        public boolean isHidden() {
            return !(mode.is("BSpeed"));
        }
    };
    public NumberValue gliding = new NumberValue("gliding",1,1,29, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return !(mode.is("Hypixel") && hypixelMode.is("Bhop"));
        }
    };
    public BooleanValue low = new BooleanValue("lowHop", false){
        @Override
        public boolean isHidden() {
            return !(mode.is("Hypixel"));
        }
    };
    public ModeValue lowMode = new ModeValue("Mode", "MoreTick", new String[]{
            "MoreTick",
            "LessTick"}){
        @Override
        public boolean isHidden() {
            return !mode.is("Hypixel") || !low.isEnable();
        }
    };
    public Speed() {
        super("Speed", Category.Movement, "Vroom Vroom");
     registerValue(mode);
     registerValue(flagDisable);
     registerValue(lowHop);
     registerValue(timer);
     registerValue(hypixelMode);
     registerValue(sneak);
     registerValue(strafeTick);
     registerValue(speed);
     registerValue(cubeSpeed);
     registerValue(gliding);
     registerValue(low);
     registerValue(lowMode);
        speed.id = "1";
        cubeSpeed.id = "2";
        mode.setPremiumModes(new String[]{
                "Grim"
        });
        hypixelMode.setPremiumModes(new String[]{
                "FakeStrafe"
        });
    }

    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        if(flagDisable.isEnable()){
            if(event.packet instanceof SPlayerPositionLookPacket){
                flagDisable();
                return;
            }
        }
        
    }

    @Override
    public void onEnable() {

        mode.getCurrent().onEnable();
        super.onEnable();
    }

     @Override
    public void onDisable() {
        mode.getCurrent().onDisable();
        mc.timer.setTimerSpeed(1.0f);
        super.onDisable();
    }

    @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        mode.getCurrent().onUpdateEvent(event);
       
    }

    @EventPriority(1)
    @EventTarget
    public void onStrafeEvent(StrafeEvent event) {
        mode.getCurrent().onStrafeEvent(event);
        
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        suffix = mode.getValue();
        mode.getCurrent().onMoveEvent(event);
    }
    @EventTarget
    public void onJump(JumpEvent event){
        mode.getCurrent().onJumpEvent(event);
    }

    @EventPriority(1)
    @EventTarget
    public void onPacket(PacketEvent event){
        mode.getCurrent().onPacketEvent(event);
    }
}
