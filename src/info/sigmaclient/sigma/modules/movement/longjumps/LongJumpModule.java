package info.sigmaclient.sigma.modules.movement.longjumps;

import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.modules.movement.LongJump;
import info.sigmaclient.sigma.modules.movement.Speed;

public class LongJumpModule extends Module {
    protected LongJump parent;
    public LongJumpModule(String name,  String desc,LongJump parent) {
        super(name, desc);
        this.parent = parent;
    }
}
