package info.sigmaclient.sigma.utils.render.anims.extended;

import info.sigmaclient.sigma.utils.TimerUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Animation {

    public TimerUtil timerUtil = new TimerUtil();

    @Setter
    int duration;
    final double endPoint;
    Direction direction;

    public Animation(int ms, double endPoint) {
        this(ms, endPoint, Direction.FORWARDS);
    }

    public Animation(int duration, double endPoint, Direction direction) {
        this.duration = duration;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public Animation setDirection(Direction direction) {
        if (this.direction != direction) {
            if (!this.direction.stops()) {
                this.direction = direction;
                timerUtil.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timerUtil.getTime())));
            }else {
                this.direction = direction;
                timerUtil.reset();
            }
        }
        return this;
    }
    public Animation setDirection(boolean forwards) {
        Direction direction = forwards ? Direction.FORWARDS : Direction.BACKWARDS;
        if (this.direction != direction) {
            this.direction = direction;
            timerUtil.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timerUtil.getTime())));
        }
        return this;
    }

    public Number getOutput() {
        if (direction.forwards()) {
            if (isDone())
                return endPoint;
            return getEquation(timerUtil.getTime() / (double) duration) * endPoint;
        } else if (direction.backwards()) {
            if (isDone()) {
                return 0.0;
            }
            if (correctOutput()) {
                double revTime = Math.min(duration, Math.max(0, duration - timerUtil.getTime()));
                return getEquation(revTime / (double) duration) * endPoint;
            }
            return (1 - getEquation(timerUtil.getTime() / (double) duration)) * endPoint;
        }else {
            timerUtil.reset();
            return 0;
        }
    }

    protected boolean correctOutput() {
        return false;
    }

    public boolean finished(Direction direction) {
        return isDone() && this.direction.equals(direction);
    }

    public void reset() {
        timerUtil.reset();
    }

    public boolean isDone() {
        return timerUtil.hasTimeElapsed(duration);
    }

    protected abstract double getEquation(double x);

}
