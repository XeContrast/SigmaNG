package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.RenderUtils;

import java.awt.*;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;

public class Particle {
    private static String[] 늦鶲鏟홵室;
    private float 쇽哺쬷欫卫;
    private float 浦頉㹔쥅韤;
    public float posX;
    public float posY;
    public float 䬾疂嶗鶲붛;
    private float 펊嘖硙樽괠;
    private float 홵뎫㥇湗웎;
    private float 浣玑泹핇䈔;
    private boolean 刃뎫敤娍鄡;
    private float ᜄࡅ堧嶗蛊;
    private RandomGenerator 掬붃㔢㦖頉;
    public float 亟挐픓钘钘;
    public Color 㼜훔뗴筕汌;
    public int 䴂竁啖酋ぶ;

    public Particle(final float n, final float n2, final float 䬾疂嶗鶲붛) {
        this.ᜄࡅ堧嶗蛊 = 1.0f;
        this.掬붃㔢㦖頉 = new RandomGenerator();
        this.㼜훔뗴筕汌 = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        this.䴂竁啖酋ぶ = 0;
        this.쇽哺쬷欫卫 = n;
        this.posX = n;
        this.浦頉㹔쥅韤 = n2;
        this.posY = n2;
        this.䬾疂嶗鶲붛 = 䬾疂嶗鶲붛;
        this.綋葫훔韤掬();
    }

    public Particle(final float n, final float n2) {
        this.ᜄࡅ堧嶗蛊 = 1.0f;
        this.掬붃㔢㦖頉 = new RandomGenerator();
        this.㼜훔뗴筕汌 = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        this.䴂竁啖酋ぶ = 0;
        this.쇽哺쬷欫卫 = n;
        this.posX = n;
        this.浦頉㹔쥅韤 = n2;
        this.posY = n2;
        this.䬾疂嶗鶲붛 = this.掬붃㔢㦖頉.nextInt(1, 3) + this.掬붃㔢㦖頉.nextFloat();
        this.綋葫훔韤掬();
    }

    private void 綋葫훔韤掬() {
        this.홵뎫㥇湗웎 = this.掬붃㔢㦖頉.nextFloat() % this.ᜄࡅ堧嶗蛊;
        this.浣玑泹핇䈔 = this.掬붃㔢㦖頉.nextFloat() % this.ᜄࡅ堧嶗蛊;
        this.펊嘖硙樽괠 = (this.掬붃㔢㦖頉.nextFloat() * this.䬾疂嶗鶲붛 + 15.0f) % 25.0f;
        this.刃뎫敤娍鄡 = this.掬붃㔢㦖頉.nextBoolean();
        this.亟挐픓钘钘 = this.掬붃㔢㦖頉.nextFloat() / 2.0f;
        if (this.掬붃㔢㦖頉.nextBoolean()) {
            this.亟挐픓钘钘 *= -1.0f;
        }
    }

    public void updatePosition(final float n) {
        RenderUtils.牰蓳躚唟捉璧(this.posX * 2f, this.posY * 2f, this.䬾疂嶗鶲붛 * 2f, applyColor(this.㼜훔뗴筕汌.getRGB(), n * 0.7f));
    }

    public void render(final Renderer Renderer) {
        this.posX += Renderer.ᢻ㮃亟㦖웎() + this.亟挐픓钘钘;
        this.쇽哺쬷欫卫 += Renderer.ᢻ㮃亟㦖웎() + this.亟挐픓钘钘;
        this.posY += this.浣玑泹핇䈔;
    }
}