package info.sigmaclient.sigma.sigma5.utils;

import lombok.Getter;

public class Renderer {
    private static String[] RENDERER_NAMES;
    @Getter
    private float deltaX;
    private RandomGenerator randomGenerator;
    private timer timerInstance;
    private long nextUpdate;
    private boolean updateRequired;
    private float targetDeltaX;

    public Renderer() {
        this.randomGenerator = new RandomGenerator();
        this.timerInstance = new timer(this);
        this.updateRequired = false;
        this.targetDeltaX = -1.0f;
        this.timerInstance.start();
        this.nextUpdate = this.randomGenerator.nextInt(8000, 10000);
        this.deltaX = this.randomGenerator.nextFloat();
    }

    public void updateor() {
        if (this.timerInstance.getElapsedTime() > this.nextUpdate) {
            this.nextUpdate = this.randomGenerator.nextInt(8000, 10000);
            this.updateRequired = true;
            this.targetDeltaX = this.randomGenerator.nextFloat() + 0.75f;
            if (this.randomGenerator.nextBoolean()) {
                this.targetDeltaX *= -1.0f;
            }
            this.timerInstance.reset();
        }
        if (this.updateRequired) {
            if (this.targetDeltaX != -1.0f) {
                if (this.timerInstance.getElapsedTime() % 10L == 0L) {
                    if (this.targetDeltaX <= this.deltaX) {
                        this.deltaX -= 0.02f;
                        if (this.targetDeltaX > this.deltaX) {
                            this.deltaX = this.targetDeltaX;
                            this.updateRequired = false;
                            this.targetDeltaX = -1.0f;
                        }
                    } else {
                        this.deltaX += 0.02f;
                        if (this.targetDeltaX < this.deltaX) {
                            this.deltaX = this.targetDeltaX;
                            this.updateRequired = false;
                            this.targetDeltaX = -1.0f;
                        }
                    }
                }
            }
        }
    }

}
