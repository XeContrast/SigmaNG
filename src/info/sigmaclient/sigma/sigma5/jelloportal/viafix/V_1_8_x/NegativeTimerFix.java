package info.sigmaclient.sigma.sigma5.jelloportal.viafix.V_1_8_x;


import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.vialoadingbase.ViaLoadingBase;

public class NegativeTimerFix {
    public boolean isEnable(){
        return ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8);
    }
}
