package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.modules.gui.KeyStrokes;
import net.minecraft.client.settings.KeyBinding;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;

public enum SingleKeyStroke {
    A(0.0f, 1.0f, mc.gameSettings.keyBindForward),
    D(2.0f, 1.0f, mc.gameSettings.keyBindLeft),
    W(1.0f, 0.0f, mc.gameSettings.keyBindBack),
    S(1.0f, 1.0f, mc.gameSettings.keyBindRight),
    L(0.0f, 2.0f, 37, null),
    R(1.02f, 2.0f, 36.5f, null);

    public float xMultiplier;
    public float yMultiplier;
    public float width;
    public float offsetY;
    public int height;
    public int scale;
    public KeyBinding keyBinding;
    public KeyStrokes.CircleManager circleManager = new KeyStrokes.CircleManager();

    SingleKeyStroke(final float xM, final float yM, final KeyBinding keyBinding) {
        this.width = 24;
        this.height = 24;
        this.scale = 1;
        this.xMultiplier = xM;
        this.yMultiplier = yM;
        this.keyBinding = keyBinding;
    }

    SingleKeyStroke(final float xMulti, final float yMulti, final float width, final KeyBinding keyBinding) {
        this.height = 24;
        this.scale = 1;
        this.xMultiplier = xMulti;
        this.yMultiplier = yMulti;
        this.keyBinding = keyBinding;
        this.width = width;
    }
    public float getCX(){
        return getX() + (getX2() - getX()) / 2;
    }
    public float getCY(){
        return getY() + (getY2() - getY()) / 2;
    }
    public float getX(){
        return 5 + getPosition().x;
    }
    public float getY(){
        return offsetY + getPosition().y;
    }
    public float getX2(){
        return getX() + width;
    }
    public float getY2(){
        return getY() + height;
    }

    public Position getPosition() {
        float f = 1.5f, f2 = 0;
        if(this == R){
            f2 = -0.5f;
        }
        return new Position(this, (float) (this.xMultiplier * (this.width + f) + f2), (float) (this.yMultiplier * (this.height + 1.5f)));
    }

    public Position getSize() {
        return new Position(this, this.width, this.height);
    }

    public static class Position {
        public float x;
        public float y;
        public final /* synthetic */ SingleKeyStroke keyStroke;

        public Position(final SingleKeyStroke keyStroke, final float x, final float y) {
            this.keyStroke = keyStroke;
            this.x = x;
            this.y = y;
        }

        public float 䢶㥇酭놣䈔() {
            return this.x;
        }
    }
}