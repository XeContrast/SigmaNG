package info.sigmaclient.sigma.utils.render.anims.color;

import info.sigmaclient.sigma.utils.render.anims.compact.CompactAnimation;
import info.sigmaclient.sigma.utils.render.anims.compact.Easing;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RGBAnimation {

    RGBAnimationType type;

    CompactAnimation r;
    CompactAnimation g;
    CompactAnimation b;

    public RGBAnimation(RGBAnimationType type,int duration) {
        this.type = type;
        this.r = new CompactAnimation(Easing.EASE_OUT_CUBIC, duration);
        this.g = new CompactAnimation(Easing.EASE_OUT_CUBIC, duration);
        this.b = new CompactAnimation(Easing.EASE_OUT_CUBIC, duration);
    }

    public void run(Color color) {
        r.run(color.getRed());
        g.run(color.getRed());
        b.run(color.getRed());
    }

    public void setColor(Color color) {
        r.setValue(color.getRed());
        g.setValue(color.getRed());
        b.setValue(color.getRed());
    }

    public Color getColor() {
        if (type == RGBAnimationType.INTEGER) {
            return new Color(r.getNumberValue().intValue(), g.getNumberValue().intValue(), b.getNumberValue().intValue());
        } else return new Color(r.getNumberValue().floatValue(), g.getNumberValue().floatValue(), b.getNumberValue().floatValue());
    }

}
