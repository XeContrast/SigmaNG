package info.sigmaclient.sigma.sigma5.utils;

public class NotiTimer
{
    private static String[] 钘㝛哝竁钘;
    private long 缰좯嘖ኞ轐;
    private long 陂蒕弻䢿躚;
    private boolean 酭顸셴䎰骰;

    public NotiTimer() {
        this.缰좯嘖ኞ轐 = 0L;
        this.陂蒕弻䢿躚 = 0L;
        this.酭顸셴䎰骰 = false;
    }

    public void 轐ᢻ佉뫤甐䖼() {
        this.酭顸셴䎰骰 = true;
        this.缰좯嘖ኞ轐 = System.currentTimeMillis();
    }

    public void 渺䩜ใ픓㨳亟() {
        this.酭顸셴䎰骰 = false;
    }

    public void 䎰랾핇ᢻ蒕ꈍ() {
        this.陂蒕弻䢿躚 = 0L;
        this.缰좯嘖ኞ轐 = System.currentTimeMillis();
    }

    public long 㱙㕠郝䡸ꦱ펊() {
        if (this.酭顸셴䎰骰) {
            this.陂蒕弻䢿躚 += System.currentTimeMillis() - this.缰좯嘖ኞ轐;
            this.缰좯嘖ኞ轐 = System.currentTimeMillis();
        }
        return this.陂蒕弻䢿躚;
    }

    public void 鞞콗뎫䖼㼜挐(final long 陂蒕弻䢿躚) {
        this.缰좯嘖ኞ轐 = System.currentTimeMillis() - 陂蒕弻䢿躚;
        this.陂蒕弻䢿躚 = 陂蒕弻䢿躚;
    }

    public boolean ࡅ붃眓햖哝汌() {
        return this.酭顸셴䎰骰;
    }

}