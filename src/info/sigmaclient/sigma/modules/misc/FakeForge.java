package info.sigmaclient.sigma.modules.misc;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;


public class FakeForge extends Module {
    public FakeForge() {
        super("FakeForge", Category.Misc, "Fakes client mod type on connection");
    }
    public static String getC17Send() {
        return (SigmaNG.getSigmaNG().moduleManager.getModule(FakeForge.class).enabled) ? (
                "fml, forge"
        ) : "vanilla";
    }
}
