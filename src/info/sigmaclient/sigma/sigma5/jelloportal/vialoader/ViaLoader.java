package info.sigmaclient.sigma.sigma5.jelloportal.vialoader;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.vialoadingbase.ViaLoadingBase;
import info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.viamcp.ViaMCP;

public class ViaLoader {
    public static void load() {
        try {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider();

            ViaLoadingBase.getInstance().reload(
                    ProtocolVersion.getProtocol(ViaLoadingBase.getInstance().getNativeVersion())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
