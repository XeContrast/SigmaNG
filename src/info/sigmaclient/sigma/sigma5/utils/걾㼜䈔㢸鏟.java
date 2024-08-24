package info.sigmaclient.sigma.sigma5.utils;

public class 걾㼜䈔㢸鏟 {
    private static String[] 璧Ꮺ岋渺竁;
    private float 捉杭室쬷陂;
    private 㱙蕃杭ᜄꈍ 弻硙㹔䩜䈔;
    private ศ酋쿨핇弻 펊㝛藸ศ筕;
    private long 阢卒待疂Ꮺ;
    private boolean ኞ筕蒕硙㐖;
    private float 洝䄟杭䆧埙;

    public 걾㼜䈔㢸鏟() {
        this.弻硙㹔䩜䈔 = new 㱙蕃杭ᜄꈍ();
        this.펊㝛藸ศ筕 = new ศ酋쿨핇弻(this);
        this.ኞ筕蒕硙㐖 = false;
        this.洝䄟杭䆧埙 = -1.0f;
        this.펊㝛藸ศ筕.䄟揩酭湗웨();
        this.阢卒待疂Ꮺ = this.弻硙㹔䩜䈔.nextInt(8000, 10000);
        this.捉杭室쬷陂 = this.弻硙㹔䩜䈔.nextFloat();
    }

    public void 哝쥦㥇㔢哺() {
        if (this.펊㝛藸ศ筕.㥇햖郝阢ᔎ() > this.阢卒待疂Ꮺ) {
            this.阢卒待疂Ꮺ = this.弻硙㹔䩜䈔.nextInt(8000, 10000);
            this.ኞ筕蒕硙㐖 = true;
            this.洝䄟杭䆧埙 = this.弻硙㹔䩜䈔.nextFloat() + 0.75f;
            if (this.弻硙㹔䩜䈔.nextBoolean()) {
                this.洝䄟杭䆧埙 *= -1.0f;
            }
            this.펊㝛藸ศ筕.핇䖼醧걾䢶();
        }
        if (this.ኞ筕蒕硙㐖) {
            if (this.洝䄟杭䆧埙 != -1.0f) {
                if (this.펊㝛藸ศ筕.㥇햖郝阢ᔎ() % 10L == 0L) {
                    if (this.洝䄟杭䆧埙 <= this.捉杭室쬷陂) {
                        this.捉杭室쬷陂 -= 0.02f;
                        if (this.洝䄟杭䆧埙 > this.捉杭室쬷陂) {
                            this.捉杭室쬷陂 = this.洝䄟杭䆧埙;
                            this.ኞ筕蒕硙㐖 = false;
                            this.洝䄟杭䆧埙 = -1.0f;
                        }
                    } else {
                        this.捉杭室쬷陂 += 0.02f;
                        if (this.洝䄟杭䆧埙 < this.捉杭室쬷陂) {
                            this.捉杭室쬷陂 = this.洝䄟杭䆧埙;
                            this.ኞ筕蒕硙㐖 = false;
                            this.洝䄟杭䆧埙 = -1.0f;
                        }
                    }
                }
            }
        }
    }

    public float ᢻ㮃亟㦖웎() {
        return this.捉杭室쬷陂;
    }
}
    