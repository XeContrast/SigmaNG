package info.sigmaclient.sigma.modules.movement;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.values.BooleanValue;
import info.sigmaclient.sigma.config.values.ModeValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.net.PacketEvent;
import info.sigmaclient.sigma.event.impl.player.ClickEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.combat.Killaura;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.network.play.server.SWindowItemsPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Objects;


public class NoSlow extends Module {
    public static ModeValue mode = new ModeValue("Mode", "Intave", new String[]{
            "GrimAC",
            "Intave",
            "Vanilla",
            "OnlySword",
            "Vulcan",
            "LNCP",
            "ONCP",
            "Advance",
            "Blocksmc",
            "Hypixel",

    });
    public static ModeValue hypmode = new ModeValue("HypixelMode","Mode1",new String[] {
            "Mode1",
            "Mode2",
            "Mode3",
            "Mode4"
    }) {
        @Override
        public boolean isHidden() { return !mode.is("Hypixel"); }
    };
    public static ModeValue grimmode = new ModeValue("GrimMode","Mode1",new String[] {
            "Mode1",
            "Mode2",
            "Mode3"
    }) {
        @Override
        public boolean isHidden() { return !mode.is("GrimAC");}
    };
    public static BooleanValue sword = new BooleanValue("Sword", true);
    public static BooleanValue food = new BooleanValue("Food", true);
    public static BooleanValue bow = new BooleanValue("Bow", true);
    public static BooleanValue potion = new BooleanValue("Potion", true);
    public static BooleanValue shield = new BooleanValue("Shield", true);
    private int offGround = 0;
    public NoSlow() {
        super("NoSlow", Category.Movement, "No slow for use items");
        registerValue(mode);
        registerValue(hypmode);
        registerValue(grimmode);
        registerValue(sword);
        registerValue(food);
        registerValue(bow);
        registerValue(potion);
        registerValue(shield);
    }
    private boolean h4_change = false;
    private int h4_tick = 0;
    private int h4_ground = 0;
    public static boolean isNeedNoslow(){
        if(mode.is("ReallyWorld")){
            if(mc.player.onGround || mc.player.fallDistance == 0)
                return false;
        }
        return SigmaNG.SigmaNG.moduleManager.getModule(NoSlow.class).enabled &&
                ((!mode.is("OnlySword") && !mode.is("Advance")) || mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem) && (mc.player.isHandActive())
                && (!mode.is("Intave") || !(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PotionItem))
                && (sword.isEnable() || !(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem))
                && (food.isEnable() || !(mc.player.getHeldItem(Hand.MAIN_HAND).isFood()))
                && (bow.isEnable() || !(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem))
                && (shield.isEnable() || !(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShieldItem || mc.player.getHeldItem(Hand.OFF_HAND).getItem() instanceof ShieldItem))
                && (potion.isEnable() || !(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PotionItem));
    }
    boolean noSlowing = false;
    boolean reset = false, slow = false;
    boolean send = false;

    @EventTarget
    public void onTick(ClickEvent event){
        if(hypmode.is("Mode3") && mode.is("Hypixel")){
            if(mc.player.isHandActive()){
                Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND == mc.player.getActiveHand()?Hand.OFF_HAND:Hand.MAIN_HAND,new BlockRayTraceResult(new Vector3d(-1, -1, -1), Direction.DOWN, new BlockPos(0,0,0),false)));
            }
        }
    }
    @EventTarget
    public void onPacketEvent(PacketEvent event) {
        IPacket<?> packet = event.packet;
        //hypixel modez
        if (!mc.player.isRidingHorse() && mc.gameSettings.keyBindUseItem.pressed || Killaura.attackTarget != null) {
            if(packet instanceof SWindowItemsPacket) {
                if(!noSlowing) {
                    event.cancelable = true;
                }
            }
        }
        switch (mode.getValue()) {
            case "Advance":
                if (!mc.player.isRidingHorse() && mc.gameSettings.keyBindUseItem.pressed || Killaura.attackTarget != null) {
                    if (packet instanceof CUseEntityPacket &&
                            ((CUseEntityPacket) packet).getAction() ==
                                    CUseEntityPacket.Action.ATTACK &&
                            mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem) {
                        noSlowing = true;
                    }
                }
                break;
            case "GrimAC":
                if (grimmode.is("Mode3")) {
                    if (packet instanceof SWindowItemsPacket && ((SWindowItemsPacket) packet).getWindowId() == 0) {
                        if ((mc.player != null ? mc.player.getActiveItemStack() : ItemStack.EMPTY) != ItemStack.EMPTY) {
//                    packetBuf.add(packet);
//                    event.cancelEvent();
                            event.cancelable = true;
                        }
                        slow = false;
                    } else if (packet instanceof SSetSlotPacket && (((SSetSlotPacket) packet).getWindowId() == 0 || ((SSetSlotPacket) packet).getWindowId() == -1 || ((SSetSlotPacket) packet).getWindowId() == -2) && (mc.player != null ? mc.player.getActiveItemStack() : ItemStack.EMPTY) != ItemStack.EMPTY) {
//                packetBuf.add(packet);
//                event.cancelEvent();
                        event.cancelable = true;
                    } else if (packet instanceof CConfirmTransactionPacket && ((CConfirmTransactionPacket) packet).getWindowId() == 0 && ((CConfirmTransactionPacket) packet).getUid() != (short) 11451 && slow) {
                        Objects.requireNonNull(mc.getConnection()).sendPacketNOEvent(new CConfirmTransactionPacket(0, (short) 11451, true));
                    } else if (packet instanceof CPlayerTryUseItemPacket) {
                        Item stack = mc.player != null ? mc.player.getHeldItem(((CPlayerTryUseItemPacket) packet).getHand()).getItem() : null;
                        if (stack != null && (stack instanceof SwordItem || stack.isFood() ||
                                stack instanceof PotionItem || stack instanceof MilkBucketItem || stack instanceof BowItem || stack instanceof ShieldItem)) {
                            slow = true;
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CClickWindowPacket(0, 36, 0, ClickType.SWAP, new ItemStack(Blocks.CHEST), (short) 11451));
                        }
                    }
                }
                break;
            case "Hypixel":
                if (hypmode.is("Mode2")) {
                    ItemStack item = mc.player.getHeldItem(mc.player.getActiveHand());
                    if(packet instanceof CPlayerTryUseItemOnBlockPacket && !mc.player.isHandActive()){
                        BlockRayTraceResult blockRayTraceResult = ((CPlayerTryUseItemOnBlockPacket) packet).func_218794_c();
                        if(blockRayTraceResult.getFace() == Direction.UP) {
                            mc.player.getHeldItem(((CPlayerTryUseItemOnBlockPacket) packet).getHand());
                            if ((item.getItem().isFood() || item.getItem() instanceof BowItem) && offGround < 2) {
                                if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                                    mc.player.jump();
                                }
                                send = true;
                                event.cancelable = true;
                            }
                        }
                    } else if(packet instanceof CPlayerTryUseItemPacket && !mc.player.isHandActive()) {
                        mc.player.getHeldItem(mc.player.getActiveHand());
                        if ((item.getItem().isFood() || item.getItem() instanceof BowItem) && offGround < 2) {

                            if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                                mc.player.jump();
                            }
                            send = true;
                        }
                    }
                    break;
                }
                break;
        }
    }
  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        suffix = mode.getValue();
        if (mc.player.isHandActive() && !mc.player.isRidingHorse()) {
            if (!isNeedNoslow()) {
                if (reset) {
                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.RELEASE_SHIFT_KEY));
                }
                return;
            }
            switch (mode.getValue()) {
                case "Hypixel":
                    switch (hypmode.getValue()) {
                        case "Mode1":
                            if (event.isPre()) {
                                if (mc.gameSettings.keyBindUseItem.pressed && mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem) {
                                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.PRESS_SHIFT_KEY));
                                    reset = true;
                                }else if(reset){
                                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.RELEASE_SHIFT_KEY));
                                }
                            }
                            break;
                        case "Mode2":
                            if(event.isPre()){
                                if (mc.player.onGround) {
                                    offGround = 0;
                                } else {
                                    offGround++;
                                }
                                final ItemStack item = mc.player.getActiveItemStack();
                                if (MovementUtils.isMoving()) {
                                    if (offGround == 4 && send) {
                                        send = false;
                                        Objects.requireNonNull(mc.getConnection()).sendPacketNOEvent(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM,BlockPos.ZERO,Direction.DOWN));
                                    } else if (mc.player.isHandActive() && !mc.player.isPassenger() && (item.getItem().isFood() || item.getItem() instanceof BowItem)) {
                                        event.y = (event.y + 1E-14);
                                    }
                                }
                            }
                            break;
                        case "Mode3":
                            if (MovementUtils.isMoving()) {
                                if (event.isPre()) {
                                    if (!(mc.player.getActiveItemStack().isFood() && mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem) && mc.player.isHandActive() && mc.player.ticksExisted % 3 == 0 && !mc.player.onGround) {
                                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemPacket(mc.player.getActiveHand()));
                                        mc.getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM,BlockPos.ZERO,Direction.DOWN));
                                    }
                                }
                            }
                            break;
                        case "Mode4":
                            if (MovementUtils.isMoving() && mc.player.isHandActive() && !mc.player.isPassenger() && (mc.gameSettings.keyBindUseItem.pressed || Killaura.attackTarget!= null)) {
                                if (event.isPre()) {

                                    if(!h4_change && h4_tick % 7 == 0 && h4_tick > 0) {
                                        h4_change = true;
                                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                                        mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 7 + 2));
                                    }
                                    if(!event.onGround) {
                                        h4_ground = 0;
                                        h4_tick++;
                                    }else {
                                        if(h4_ground < 2){
                                            h4_tick++;
                                        }
                                        h4_ground++;
                                    }

                                }
                                if(h4_change){
                                    h4_change = false;
                                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                                    mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(mc.player.getActiveHand()));
                                    if(mc.player.getActiveItemStack().getItem() instanceof SwordItem){
                                        mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                                    }
                                }
                            }else {
                                h4_tick = 0;
                            }
                            break;
                    }
                    break;
                case "Advance":
                    if (mc.gameSettings.keyBindUseItem.pressed || Killaura.attackTarget!= null && mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem) {
                        if (noSlowing) {
                            noSlowing = false;
                            return;
                        }
                        if(event.isPre()) {
                            if (mc.player.ticksExisted % 3 == 0)
                                Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemOnBlockPacket(
                                        Hand.MAIN_HAND,
                                        new BlockRayTraceResult(
                                                new Vector3d(-1, -1, -1),
                                                Direction.UP,
                                                new BlockPos(-1, -1, -1),
                                                false
                                        )
                                ));
                        }
                    }
                    break;
                case "Blocksmc":
                    if (event.isPre()) {
                        if (!(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem)) {
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                            mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                            if (mc.player.ticksExisted % 3 == 0) {
                                mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                            }
                        }
                    }
                    break;
                case "Intave":
                    if (event.isPre()) {
                        if (mc.player.getHeldItem(Hand.MAIN_HAND).isFood() || mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PotionItem) {
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
                        } else if (mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShieldItem || mc.player.getHeldItem(Hand.OFF_HAND).getItem() instanceof  ShieldItem) {
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        }
                    }

                    break;
                case "Vulcan":
                    if (event.isPre()) {
                        if (mc.player.ticksExisted % 3 == 0) {
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        }
                    }
                    break;
                case "ReallyWorld":
                    if (!event.isPre()) {
                        if (mc.player.ticksExisted % 2 == 0) {
                            Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
//                            mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        }
                    }
                    break;
                case "GrimAC":
                    if (event.isPre()) {
                        switch (grimmode.getValue()) {
                            case "Mode1":
                                Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                                break;
                            case "Mode2":
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 7 + 2));
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                                break;
                        }
                    }
                    break;
                case "LNCP":
                    if (event.isPre()) {
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                        mc.getConnection().sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                        if (mc.player.ticksExisted % 3 == 0) {
                            mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        }
                    }
                    break;
                case "ONCP":
                    if (event.isPre()) {
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
                        mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    }
                    break;
            }
        }
       
    }

    @Override
    public void onEnable() {
        super.onEnable();
        h4_change = false;
        send = false;
        h4_tick = 0;
        h4_ground = 0;
        offGround = 0;
    }
}