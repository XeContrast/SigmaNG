package info.sigmaclient.sigma.sigma5.jelloportal.viafix;

import info.sigmaclient.sigma.sigma5.jelloportal.viafix.V_1_17.V1_17_PlaceFix;
import info.sigmaclient.sigma.sigma5.jelloportal.viafix.V_1_8_x.NegativeTimerFix;
import info.sigmaclient.sigma.sigma5.jelloportal.viafix.generic.SafeMode;

public class ViaFixManager {
    public static ViaFixManager viaFixManager = new ViaFixManager();
    public NegativeTimerFix negativeTimerFix = new NegativeTimerFix();
    public V1_17_PlaceFix v117PlaceFix = new V1_17_PlaceFix();
    public SafeMode safeMode = new SafeMode();
    public boolean isNegativeTimerFixEnable(){
        return negativeTimerFix.isEnable();
    }
}
