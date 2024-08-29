package info.sigmaclient.sigma.event;



import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.commands.impl.GPSCommand;
import info.sigmaclient.sigma.event.annotations.EventPriority;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.*;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.event.impl.render.RenderShaderEvent;
import info.sigmaclient.sigma.gui.clickgui.JelloClickGui;
import info.sigmaclient.sigma.gui.clickgui.musicplayer.MusicWaveRender;
import info.sigmaclient.sigma.gui.othergui.anticrack.AntiCrack;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.gui.hide.ClickGUI;
import info.sigmaclient.sigma.modules.movement.BlockFly;
import info.sigmaclient.sigma.modules.movement.TargetStrafe;
import info.sigmaclient.sigma.modules.world.Timer;
import info.sigmaclient.sigma.sigma5.SelfDestructManager;
import info.sigmaclient.sigma.utils.player.MovementUtils;
import info.sigmaclient.sigma.utils.player.RotationUtils;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import org.lwjgl.opengl.GL11;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static info.sigmaclient.sigma.modules.Module.mc;
import static info.sigmaclient.sigma.modules.combat.Killaura.unBlock;

public class EventManager {
    public boolean nextLegitUnblock = false;
    private boolean init = false;
    public void init(){
        init = true;
    }
    private final Map<Method, Class<?>> registeredMethodMap;
    private final Map<Method, Object> methodObjectMap;
    private final Map<Class<? extends Event>, List<Method>> priorityMethodMap;

    public EventManager() {
        registeredMethodMap = new ConcurrentHashMap<>();
        methodObjectMap = new ConcurrentHashMap<>();
        priorityMethodMap = new ConcurrentHashMap<>();
    }

    /**
     * Registers one or more objects to associate their methods with event annotations and stores them in the event handler.
     *
     * @param obj One or more objects to register.
     */
    public void register(Object... obj) {
        for(Object object : obj){
            register(object);
        }
    }

    /**
     * Registers an object to associate its methods with event annotations and stores them in the event handler.
     *
     * @param obj The object to register.
     */
    public void register(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if(methodObjectMap.containsValue(obj))return;
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == EventTarget.class && method.getParameterTypes().length == 1) {
                    registeredMethodMap.put(method, method.getParameterTypes()[0]);
                    methodObjectMap.put(method, obj);

                    Class<? extends Event> eventClass = method.getParameterTypes()[0].asSubclass(Event.class);
                    priorityMethodMap.computeIfAbsent(eventClass, k -> new CopyOnWriteArrayList<>()).add(method);
                }
            }
        }
    }

    /**
     * Unregisters an object, removing its associated methods from the event handler.
     *
     * @param obj The object to unregister.
     */
    public void unregister(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (registeredMethodMap.containsKey(method)) {
                registeredMethodMap.remove(method);
                methodObjectMap.remove(method);
                Class<? extends Event> eventClass = method.getParameterTypes()[0].asSubclass(Event.class);
                List<Method> priorityMethods = priorityMethodMap.get(eventClass);
                if (priorityMethods != null) {
                    priorityMethods.remove(method);
                }
            }
        }
    }

    /**
     * Calls the registered methods associated with the provided event, respecting their priorities.
     *
     * @param event The event to call the registered methods for.
     * @return The modified or processed event after calling the methods.
     */
    public Event call(Event event) {
        if(!init || SelfDestructManager.destruct) return event;
//        if(event instanceof WindowUpdateEvent){
//            SigmaNG.SigmaNG.antiAgent.doOneCheck();
//            if(!SigmaNG.SigmaNG.verify.verify){
//                if(!(Minecraft.getInstance().currentScreen instanceof AntiCrack)){
//                    Minecraft.getInstance().displayGuiScreen(new AntiCrack());
//                }
//            }
//        }
        if(Minecraft.getInstance().player == null || Minecraft.getInstance().world == null) return event;

        if(event instanceof UpdateEvent){
            if(((UpdateEvent) event).isPre()){
                if(RotationUtils.NEXT_SLOT != -1){
                    Minecraft.getInstance().getConnection().sendPacketNOEvent(new CHeldItemChangePacket(RotationUtils.NEXT_SLOT));
                    RotationUtils.NEXT_SLOT = -1;
                }
            }
        }

        if(event instanceof KeyEvent){
            for (Module module : SigmaNG.getSigmaNG().moduleManager.modules) {
                if(module.key != -1 && ((KeyEvent) event).key == module.key)
                    module.toggle();
            }
        }
        Class<? extends Event> eventClass = event.getClass();

        List<Method> methods = priorityMethodMap.get(eventClass);
        if (methods != null) {
            methods.sort(Comparator.comparingInt(method -> {
                EventPriority priority = method.getAnnotation(EventPriority.class);
                return (priority != null) ? priority.value() : 10;
            }));

            for (Method method : methods) {
                Object obj = methodObjectMap.get(method);
                method.setAccessible(true);
                try {
                    method.invoke(obj, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(event instanceof RenderEvent){
            GPSCommand.render();
            ((BlockFly)SigmaNG.SigmaNG.moduleManager.getModule(BlockFly.class)).renderBlockCounter();
            if(SigmaNG.gameMode == SigmaNG.GAME_MODE.JELLO) {
                SigmaNG.getSigmaNG().notificationManager.onRender();
            }
            MusicWaveRender.SELF.drawTexture();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            if(!(mc.currentScreen instanceof JelloClickGui) && mc.player != null && mc.world != null){
                if(JelloClickGui.leave.getValue() < 1.68 && ClickGUI.isEnableFirst){
                    GL11.glPushMatrix();
                    ClickGUI.clickGui.drawScreen(0,0, mc.timer.renderPartialTicks);
                    GL11.glPopMatrix();
                }
            }
        }
        if(event instanceof RenderShaderEvent){
            MusicWaveRender.SELF.drawWave();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        if(event instanceof Render3DEvent){
            //if(!shaders)
            //shadowESP.renderEvent2();
            GlStateManager.disableLighting();
            GlStateManager.enableTexture2D();
        }
        if(event instanceof WindowUpdateEvent){
            MusicWaveRender.SELF.onTick();
            if(!(mc.currentScreen instanceof JelloClickGui) && mc.player != null && mc.world != null) {
                JelloClickGui.leave.interpolate(1.7f, 3);
            }
        }
        if(event instanceof UpdateEvent){
            if(((UpdateEvent) event).isPre()) {
                ((BlockFly)SigmaNG.SigmaNG.moduleManager.getModule(BlockFly.class)).tickForAnim();
                SigmaNG.getSigmaNG().notificationManager.onTick();
            }else{
                if(!((UpdateEvent) event).send){
                    Timer.violation -= 1;
//                    ChatUtils.sendMessageWithPrefix("1 " + violation);
                    Timer.violation = Math.max(Timer.violation, 0);
//                    return;
                }
            }
        }
        if(event instanceof MoveEvent){
            MovementUtils.clacMotion((MoveEvent) event);
            TargetStrafe.onMove((MoveEvent) event);
        }



        return event;
    }
}