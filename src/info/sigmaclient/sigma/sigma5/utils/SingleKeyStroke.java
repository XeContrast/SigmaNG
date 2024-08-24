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

    public float 曞뫤螜䎰㠠;
    public float 퉧湗哺陂䢶;
    public float 竬鄡햖ꪕ놣;
    public float offsetY;
    public int 쿨댠Ꮤ뚔鱀;
    public int 眓좯곻弻洝;
    public KeyBinding 睬卒Ꮺ딨ᜄ;
    public KeyStrokes.CircleManager circleManager = new KeyStrokes.CircleManager();

    SingleKeyStroke(final float xM, final float yM, final KeyBinding 睬卒Ꮺ딨ᜄ) {
        this.竬鄡햖ꪕ놣 = 24;
        this.쿨댠Ꮤ뚔鱀 = 24;
        this.眓좯곻弻洝 = 1;
        this.曞뫤螜䎰㠠 = xM;
        this.퉧湗哺陂䢶 = yM;
        this.睬卒Ꮺ딨ᜄ = 睬卒Ꮺ딨ᜄ;
    }

    SingleKeyStroke(final float xMulti, final float yMulti, final float 竬鄡햖ꪕ놣, final KeyBinding 睬卒Ꮺ딨ᜄ) {
        this.쿨댠Ꮤ뚔鱀 = 24;
        this.眓좯곻弻洝 = 1;
        this.曞뫤螜䎰㠠 = xMulti;
        this.퉧湗哺陂䢶 = yMulti;
        this.睬卒Ꮺ딨ᜄ = 睬卒Ꮺ딨ᜄ;
        this.竬鄡햖ꪕ놣 = 竬鄡햖ꪕ놣;
    }
    public float getCX(){
        return getX() + (getX2() - getX()) / 2;
    }
    public float getCY(){
        return getY() + (getY2() - getY()) / 2;
    }
    public float getX(){
        return 5 + 䣓붃髾놣㠠().泹霥ኞ䬹鶊;
    }
    public float getY(){
        return offsetY + 䣓붃髾놣㠠().롤堧쥦Ꮤ掬;
    }
    public float getX2(){
        return getX() + 竬鄡햖ꪕ놣;
    }
    public float getY2(){
        return getY() + 쿨댠Ꮤ뚔鱀;
    }

    public 뎫竬蚳啖釒 䣓붃髾놣㠠() {
        float f = 1.5f, f2 = 0;
        if(this == R){
            f2 = -0.5f;
        }
        return new 뎫竬蚳啖釒(this, (float) (this.曞뫤螜䎰㠠 * (this.竬鄡햖ꪕ놣 + f) + f2), (float) (this.퉧湗哺陂䢶 * (this.쿨댠Ꮤ뚔鱀 + 1.5f)));
    }

    public 뎫竬蚳啖釒 娍唟埙ᔎ㻣() {
        return new 뎫竬蚳啖釒(this, this.竬鄡햖ꪕ놣, this.쿨댠Ꮤ뚔鱀);
    }

    public static class 뎫竬蚳啖釒 {
        public float 泹霥ኞ䬹鶊;
        public float 롤堧쥦Ꮤ掬;
        public final /* synthetic */ SingleKeyStroke 醧樽햖婯䢿;

        public 뎫竬蚳啖釒(final SingleKeyStroke 醧樽햖婯䢿, final float 泹霥ኞ䬹鶊, final float 롤堧쥦Ꮤ掬) {
            this.醧樽햖婯䢿 = 醧樽햖婯䢿;
            this.泹霥ኞ䬹鶊 = 泹霥ኞ䬹鶊;
            this.롤堧쥦Ꮤ掬 = 롤堧쥦Ꮤ掬;
        }

        public float 䢶㥇酭놣䈔() {
            return this.泹霥ኞ䬹鶊;
        }
    }
}