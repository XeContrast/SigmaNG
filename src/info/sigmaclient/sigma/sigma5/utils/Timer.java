package info.sigmaclient.sigma.sigma5.utils;

public class Timer {
    private static String[] timerData;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;
    public final /* synthetic */ Renderer renderer;

    public Timer(final Renderer renderer) {
        this.renderer = renderer;
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

    public boolean 뵯늦ꦱ驋쬫() {
        return this.isRunning;
    }
}
    