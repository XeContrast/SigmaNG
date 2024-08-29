package info.sigmaclient.sigma.process;


import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.process.impl.packet.BadPacketsProcess;
import info.sigmaclient.sigma.process.impl.player.BlinkProcess;
import info.sigmaclient.sigma.process.impl.player.PlayerProcess;
import info.sigmaclient.sigma.process.impl.player.RotationManager;
import info.sigmaclient.sigma.process.impl.player.StrafeFixManager;

import java.util.HashMap;


//后台进程
public final class BProcessManager {

    private final HashMap<Class<BProcess>, BProcess> componentList = new HashMap<>();

    public void init() {

        this.add(new BadPacketsProcess());
        this.add(new BlinkProcess());
        this.add(new PlayerProcess());
        this.add(new RotationManager());
        this.add(new StrafeFixManager());
        this.componentList.forEach((componentClass, process) -> SigmaNG.getSigmaNG().eventManager.register(process));
        this.componentList.forEach(((componentClass, process) -> process.onInit()));
    }

    public void add(final BProcess process) {
        this.componentList.put((Class<BProcess>) process.getClass(), process);
    }

    public <T extends BProcess> T get(final Class<T> clazz) {
        return (T) this.componentList.get(clazz);
    }
}