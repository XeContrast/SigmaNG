package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;

public class BrainFreezeParticles {
    private static String[] 醧쿨浣ᔎ甐;
    private List<酋㔢浦杭嘖> 鄡釒괠䕦睬;
    private 걾㼜䈔㢸鏟 洝樽鞞ꦱ셴;
    public 㱙蕃杭ᜄꈍ 韤竁䣓霥퉧;

    public BrainFreezeParticles(final String s) {
//        ScaledResolution sr = new ScaledResolution(mc);
//        final int 䕦ᔎศ埙甐陬 = sr.getScaledWidth();
//        final int 瀧㠠㹔挐姮㕠 = sr.getScaledHeight();
//        super(甐쿨Ⱋ샱㼜, s, 0, 0, Minecraft.㝛卫쟗홵嘖蕃().娍Ꮤ婯玑鶊좯.getWidth(), Minecraft.㝛卫쟗홵嘖蕃().娍Ꮤ婯玑鶊좯.getHeight(), false);
        this.鄡釒괠䕦睬 = new ArrayList<酋㔢浦杭嘖>();
        this.洝樽鞞ꦱ셴 = new 걾㼜䈔㢸鏟();
        this.韤竁䣓霥퉧 = new 㱙蕃杭ᜄꈍ();
//        this.뗴葫䎰햖Ꮀ샱(false);
//        this.䣓웎属훔郝牰(false);
//        this.콗骰늦ࡅ鱀硙(false);
//        this.ࡅ刃䄟驋㔢圭(true);
//        this.㞈蛊鶲ಽ樽㥇(false);
    }
    public void render(final float n) {
        ScaledResolution sr = new ScaledResolution(mc);
        final int 䕦ᔎศ埙甐陬 = sr.getScaledWidth() / 2;
        final int 瀧㠠㹔挐姮㕠 = sr.getScaledHeight() / 2;
        final int n2 = 䕦ᔎศ埙甐陬;
        int n3 = 0;
        while (this.鄡釒괠䕦睬.size() < n2) {
            this.鄡釒괠䕦睬.add(new 酋㔢浦杭嘖((float) this.韤竁䣓霥퉧.nextInt(䕦ᔎศ埙甐陬), (float) this.韤竁䣓霥퉧.nextInt(瀧㠠㹔挐姮㕠)));
            n3 = 1;
        }
        while (this.鄡釒괠䕦睬.size() > n2) {
            this.鄡釒괠䕦睬.remove(0);
            n3 = 1;
        }
        if (n3 != 0) {
            for (int i = 0; i < this.鄡釒괠䕦睬.size(); ++i) {
                this.鄡釒괠䕦睬.get(i).婯㥇㐖待䢿 = (float) this.韤竁䣓霥퉧.nextInt(䕦ᔎศ埙甐陬);
                this.鄡釒괠䕦睬.get(i).뼢ꦱ롤콗睬 = (float) this.韤竁䣓霥퉧.nextInt(瀧㠠㹔挐姮㕠);
            }
        }
        this.洝樽鞞ꦱ셴.哝쥦㥇㔢哺();
        for (final 酋㔢浦杭嘖 酋㔢浦杭嘖 : this.鄡釒괠䕦睬) {
            酋㔢浦杭嘖.㥇鶲샱塱瀧(this.洝樽鞞ꦱ셴);
            if (酋㔢浦杭嘖.婯㥇㐖待䢿 >= 0.0f) {
                if (酋㔢浦杭嘖.婯㥇㐖待䢿 > 䕦ᔎศ埙甐陬) {
                    酋㔢浦杭嘖.婯㥇㐖待䢿 = 0.0f;
                }
            } else {
                酋㔢浦杭嘖.婯㥇㐖待䢿 = (float) 䕦ᔎศ埙甐陬;
            }
            if (酋㔢浦杭嘖.뼢ꦱ롤콗睬 >= 0.0f) {
                if (酋㔢浦杭嘖.뼢ꦱ롤콗睬 > 瀧㠠㹔挐姮㕠) {
                    酋㔢浦杭嘖.뼢ꦱ롤콗睬 = 0.0f;
                }
            } else {
                酋㔢浦杭嘖.뼢ꦱ롤콗睬 = (float) 瀧㠠㹔挐姮㕠;
            }
            酋㔢浦杭嘖.汌쬫햠眓뵯(n);
        }
    }
}