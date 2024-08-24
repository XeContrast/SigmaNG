package info.sigmaclient.sigma.sigma5.utils;

public class ศ酋쿨핇弻 {
    private static String[] 姮甐葫阢䡸;
    private long 蕃ศ㞈뫤쇽;
    private long 葫霥蒕嶗䴂;
    private boolean 샱轐釒ࡅ鞞;
    public final /* synthetic */ 걾㼜䈔㢸鏟 汌醧挐늦댠;

    public ศ酋쿨핇弻(final 걾㼜䈔㢸鏟 汌醧挐늦댠) {
        this.汌醧挐늦댠 = 汌醧挐늦댠;
        this.蕃ศ㞈뫤쇽 = 0L;
        this.葫霥蒕嶗䴂 = 0L;
        this.샱轐釒ࡅ鞞 = false;
    }

    public void 䄟揩酭湗웨() {
        this.샱轐釒ࡅ鞞 = true;
        this.蕃ศ㞈뫤쇽 = System.currentTimeMillis();
    }

    public void 弻藸敤釒渺() {
        this.샱轐釒ࡅ鞞 = false;
    }

    public void 핇䖼醧걾䢶() {
        this.葫霥蒕嶗䴂 = 0L;
        this.蕃ศ㞈뫤쇽 = System.currentTimeMillis();
    }

    public long 㥇햖郝阢ᔎ() {
        if (this.샱轐釒ࡅ鞞) {
            this.葫霥蒕嶗䴂 += System.currentTimeMillis() - this.蕃ศ㞈뫤쇽;
            this.蕃ศ㞈뫤쇽 = System.currentTimeMillis();
        }
        return this.葫霥蒕嶗䴂;
    }

    public boolean 뵯늦ꦱ驋쬫() {
        return this.샱轐釒ࡅ鞞;
    }
}
    