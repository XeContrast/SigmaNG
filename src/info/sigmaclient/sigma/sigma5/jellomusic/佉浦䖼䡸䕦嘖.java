package info.sigmaclient.sigma.sigma5.jellomusic;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class 佉浦䖼䡸䕦嘖 {
    private static String[] 뗴藸䁞卒螜刃;
    private int 㐈阢堧䈔㞈뚔;
    private int 퉧佉渺樽㦖鱀;
    private int 뚔姮㱙웎刃綋;
    private double 弻낛쟗䈔뚔捉;
    private double 塱곻属頉㥇㝛;
    private double 缰홵ᜄಽ햖亟;
    private byte[] 粤殢웨䩉펊콗;
    private boolean 泹ၝᙽ쇼쇽䢿;

    public 佉浦䖼䡸䕦嘖() {
        this.粤殢웨䩉펊콗 = new byte[0];
        this.㐈阢堧䈔㞈뚔 = 0;
        this.퉧佉渺樽㦖鱀 = 0;
        this.뚔姮㱙웎刃綋 = 0;
        this.泹ၝᙽ쇼쇽䢿 = true;
    }

    public byte[] 곻洝韤䴂湗堍() {
        return this.粤殢웨䩉펊콗;
    }

    public int 鏟蒕掬䬾湗낛() {
        return this.㐈阢堧䈔㞈뚔;
    }

    public int 㐈褕㐖㥇Ꮺ괠() {
        return this.퉧佉渺樽㦖鱀;
    }

    public int 釒顸鼒髾褕㝛() {
        return this.뚔姮㱙웎刃綋;
    }

    public double 嘖낛埙鼒待聛() {
        return this.弻낛쟗䈔뚔捉;
    }

    public double 嘖呓瀳샱螜揩() {
        return this.塱곻属頉㥇㝛;
    }

    public double 쿨䢿ꈍศ頉괠() {
        return this.缰홵ᜄಽ햖亟;
    }

    public boolean 渺㨳鄡瀧묙䩉() {
        return this.泹ၝᙽ쇼쇽䢿;
    }

    public void 瀧葫褕㥇㝛浣(final boolean 泹ၝᙽ쇼쇽䢿) {
        if (泹ၝᙽ쇼쇽䢿 != this.泹ၝᙽ쇼쇽䢿) {
            for (int i = 0; i < this.粤殢웨䩉펊콗.length; i += 2) {
                final byte b = this.粤殢웨䩉펊콗[i];
                this.粤殢웨䩉펊콗[i] = this.粤殢웨䩉펊콗[i + 1];
                this.粤殢웨䩉펊콗[i + 1] = b;
            }
            this.泹ၝᙽ쇼쇽䢿 = 泹ၝᙽ쇼쇽䢿;
        }
    }

    public void 堧㻣葫璧꿩卒(final byte[] 粤殢웨䩉펊콗, final int 㐈阢堧䈔㞈뚔, final int 퉧佉渺樽㦖鱀, final int 뚔姮㱙웎刃綋, final int n) {
        this.粤殢웨䩉펊콗 = 粤殢웨䩉펊콗;
        this.㐈阢堧䈔㞈뚔 = 㐈阢堧䈔㞈뚔;
        this.퉧佉渺樽㦖鱀 = 퉧佉渺樽㦖鱀;
        this.뚔姮㱙웎刃綋 = 뚔姮㱙웎刃綋;
        if (㐈阢堧䈔㞈뚔 != 0) {
            final int n2 = 粤殢웨䩉펊콗.length / (뚔姮㱙웎刃綋 / 8 * 퉧佉渺樽㦖鱀);
            this.弻낛쟗䈔뚔捉 = n2 / (double) 㐈阢堧䈔㞈뚔;
            this.塱곻属頉㥇㝛 = n2 * 뚔姮㱙웎刃綋 * 퉧佉渺樽㦖鱀 / this.弻낛쟗䈔뚔捉;
            this.缰홵ᜄಽ햖亟 = n / this.弻낛쟗䈔뚔捉;
        } else {
            this.弻낛쟗䈔뚔捉 = 0.0;
            this.塱곻属頉㥇㝛 = 0.0;
            this.缰홵ᜄಽ햖亟 = 0.0;
        }
    }
}
