package info.sigmaclient.sigma.utils.click;

import info.sigmaclient.sigma.utils.TimerUtil;
import net.minecraft.client.gui.widget.button.Button;

public class ClickHelper {
    private TimerUtil timer;
    private int[] cps;
    private int c;
    private int tick;
    private String type;
    public ClickHelper(int cps,String type) {
        timer = new TimerUtil();
        timer.reset();
        c = cps;
        this.type = type;
        this.cps = getCps(cps,type);
        this.tick = 0;
    }

    public int[] getCps(int cps,String type){
        switch (type){
            case "Butterfly":
                return ClickMode.butterflyClick(cps);
            case "Drag":
                return ClickMode.dragClick(cps);
            case "Stabilized":
                return ClickMode.stabilizedClick(cps);
            case "DoubleClick":
                return ClickMode.doubleClick(cps);
            case "NormalClick":
                return ClickMode.normalClick(cps);
            default:
                return null;
        }
    }

    public void reset(){
        tick = 0;
        timer.reset();
        cps = getCps(c,type);
    }

    public int getClick(){
        if(++tick < cps.length){
            return cps[tick];
        }else  {
            tick = 0;
            cps = getCps(c,type);
            getClick();
            return 2;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCPS(int c) {
        this.c = c;

    }
    public interface process
    {
        void run();
    }
    public void runClick(process process){
        if(timer.hasTimeElapsed(500 / cps.length)) {
            int tick = getClick();
            if (tick == 0) return;
            for (int i = 0; i < tick; i++) {
                process.run();
            }
            timer.reset();
        }
    }

    public int getCPS() {
        return c;
    }

    public String getType() {
        return type;
    }
}
