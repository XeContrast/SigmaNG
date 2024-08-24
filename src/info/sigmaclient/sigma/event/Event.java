package info.sigmaclient.sigma.event;

public class Event {
    public boolean cancelable = false;

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }
    public void setCancelled(){
        this.cancelable = true;
    }
}
