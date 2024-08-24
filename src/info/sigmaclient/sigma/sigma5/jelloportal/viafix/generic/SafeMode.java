package info.sigmaclient.sigma.sigma5.jelloportal.viafix.generic;

import info.sigmaclient.sigma.gui.hud.notification.NotificationManager;

public class SafeMode {
    public boolean isInSafeMode = false;
    public void notifySafeMode(){
        if(!isInSafeMode){
            NotificationManager.notify("Warning", "Jello portal is running in safe mode!", 10000);
        }
        isInSafeMode = true;
    }
}
