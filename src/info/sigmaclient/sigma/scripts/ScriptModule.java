package info.sigmaclient.sigma.scripts;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import info.sigmaclient.sigma.event.impl.player.MoveEvent;
import info.sigmaclient.sigma.event.impl.player.UpdateEvent;
import info.sigmaclient.sigma.event.impl.player.WorldEvent;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.utils.ChatUtils;

public class ScriptModule extends Module {
    String script;
    ExpressRunner runner;
    DefaultContext<String, Object> context;
    public ScriptModule(String name, Category category, String desc, String script) {
        super(name, category, desc);
    }

     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        try {
            runner.execute("onRenderEvent", context, null, true, false, 1000);
        } catch (Exception e) {
            ChatUtils.sendMessage("Script Module: " + name + " bad happen: " + e.getMessage());
        }
        
    }

    @EventTarget
    public void onWorldEvent(WorldEvent event) {
        try {
            runner.execute("onWorldEvent", context, null, true, false, 1000);
        } catch (Exception e) {
            ChatUtils.sendMessage("Script Module: " + name + " bad happen: " + e.getMessage());
        }
        
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        try {
            runner.execute("onRender3DEvent", context, null, true, false, 1000);
        } catch (Exception e) {
            ChatUtils.sendMessage("Script Module: " + name + " bad happen: " + e.getMessage());
        }
        
    }

    @EventTarget
    public void onMoveEvent(MoveEvent event){
        try {
            runner.execute("onMoveEvent", context, null, true, false, 1000);
        } catch (Exception e) {
            ChatUtils.sendMessage("Script Module: " + name + " bad happen: " + e.getMessage());
        }
        
    }

  @EventTarget
    public void onUpdateEvent(UpdateEvent event){
        try {
            runner.execute("onUpdateEvent", context, null, true, false, 1000);
        } catch (Exception e) {
            ChatUtils.sendMessage("Script Module: " + name + " bad happen: " + e.getMessage());
        }
       
    }
}
