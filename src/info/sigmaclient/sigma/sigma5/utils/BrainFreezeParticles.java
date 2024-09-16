package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;

public class BrainFreezeParticles {
    private static String[] stringArray;
    private List<Particle> particles;
    private Renderer renderer;
    public RandomGenerator randomGenerator;

    public BrainFreezeParticles(final String s) {
//        ScaledResolution sr = new ScaledResolution(mc);
//        final int 䕦ᔎศ埙甐陬 = sr.getScaledWidth();
//        final int 瀧㠠㹔挐姮㕠 = sr.getScaledHeight();
//        super(甐쿨Ⱋ샱㼜, s, 0, 0, Minecraft.㝛卫쟗홵嘖蕃().娍Ꮤ婯玑鶊좯.getWidth(), Minecraft.㝛卫쟗홵嘖蕃().娍Ꮤ婯玑鶊좯.getHeight(), false);
        this.particles = new ArrayList<Particle>();
        this.renderer = new Renderer();
        this.randomGenerator = new RandomGenerator();
//        this.뗴葫䎰햖Ꮀ샱(false);
//        this.䣓웎属훔郝牰(false);
//        this.콗骰늦ࡅ鱀硙(false);
//        this.ࡅ刃䄟驋㔢圭(true);
//        this.㞈蛊鶲ಽ樽㥇(false);
    }
    public void render(final float n) {
        ScaledResolution sr = new ScaledResolution(mc);
        final int scaledWidth = sr.getScaledWidth() / 2;
        final int scaledHeight = sr.getScaledHeight() / 2;
        final int n2 = scaledWidth;
        int n3 = 0;
        while (this.particles.size() < n2) {
            this.particles.add(new Particle((float) this.randomGenerator.nextInt(scaledWidth), (float) this.randomGenerator.nextInt(scaledHeight)));
            n3 = 1;
        }
        while (this.particles.size() > n2) {
            this.particles.remove(0);
            n3 = 1;
        }
        if (n3 != 0) {
            for (int i = 0; i < this.particles.size(); ++i) {
                this.particles.get(i).posX = (float) this.randomGenerator.nextInt(scaledWidth);
                this.particles.get(i).posY = (float) this.randomGenerator.nextInt(scaledHeight);
            }
        }
        this.renderer.updateor();
        for (final Particle Particle : this.particles) {
            Particle.render(this.renderer);
            if (Particle.posX >= 0.0f) {
                if (Particle.posX > scaledWidth) {
                    Particle.posX = 0.0f;
                }
            } else {
                Particle.posX = (float) scaledWidth;
            }
            if (Particle.posY >= 0.0f) {
                if (Particle.posY > scaledHeight) {
                    Particle.posY = 0.0f;
                }
            } else {
                Particle.posY = (float) scaledHeight;
            }
            Particle.updatePosition(n);
        }
    }
}