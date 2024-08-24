package info.sigmaclient.sigma.sigma5.killaura;


import net.minecraft.entity.Entity;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class 佉陂值釒䂷 {
    private static String[] 岋셴圭댠㼜;
    private Entity ꈍ곻랾쬷属;
    private 佉蚳ศ錌Ꮺ 哝刃缰웎ใ;

    public 佉陂值釒䂷(final Entity ꈍ곻랾쬷属) {
        this.ꈍ곻랾쬷属 = ꈍ곻랾쬷属;
        this.哝刃缰웎ใ = null;
    }

    public 佉陂值釒䂷(final Entity ꈍ곻랾쬷属, final 佉蚳ศ錌Ꮺ 哝刃缰웎ใ) {
        this.ꈍ곻랾쬷属 = ꈍ곻랾쬷属;
        this.哝刃缰웎ใ = 哝刃缰웎ใ;
    }

    public Entity 呓洝㼜괠殢() {
        return this.ꈍ곻랾쬷属;
    }

    public boolean 㕠㮃츚㦖頉() {
        return this.哝刃缰웎ใ != null && this.哝刃缰웎ใ.䢿㨳蕃刃쥡();
    }

    public 佉蚳ศ錌Ꮺ 洝㹔娍㥇殢() {
        return this.哝刃缰웎ใ;
    }

    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof 佉陂值釒䂷 && ((佉陂值釒䂷) o).呓洝㼜괠殢() == this.呓洝㼜괠殢());
    }
}
