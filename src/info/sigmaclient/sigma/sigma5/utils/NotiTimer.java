package info.sigmaclient.sigma.sigma5.utils;

public class NotiTimer
{
    private static String[] TIMER_NAMES;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    public NotiTimer() {
        this.startTime = 0L;
        this.elapsedTime = 0L;
        this.isRunning = false;
    }

    public void start() {
        this.isRunning = true;
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.isRunning = false;
    }

    public void reset() {
        this.elapsedTime = 0L;
        this.startTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        if (this.isRunning) {
            this.elapsedTime += System.currentTimeMillis() - this.startTime;
            this.startTime = System.currentTimeMillis();
        }
        return this.elapsedTime;
    }

    public void setElapsedTime(final long isRunning) {
        this.startTime = System.currentTimeMillis() - isRunning;
        this.elapsedTime = isRunning;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

}