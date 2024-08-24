package info.sigmaclient.sigma.sigma5.jellomusic;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class 佉䂷㠠觯콵 implements 掬좯鼒蓳酭 {
    private static String[] 杭쥡璧ꦱಽ;
    private static final int 㠠樽螜唟㐈 = 16;
    private static final int[][] 汌웎ใ㱙䡸;
    private final int 쇼펊ኞ㨳㐖;
    private final int[] 鶊웎掬쟗钘;
    private float[] 湗웨敤韤햖;

    public 佉䂷㠠觯콵(int 쇼펊ኞ㨳㐖) {
        if (!揩䖼瀧䆧鄡(쇼펊ኞ㨳㐖)) {
            throw new IllegalArgumentException(佉䂷㠠觯콵.杭쥡璧ꦱಽ[0]);
        }
        if (쇼펊ኞ㨳㐖 > 0) {
            this.쇼펊ኞ㨳㐖 = 쇼펊ኞ㨳㐖;
            final int 甐곻钘ᜄ婯 = 甐곻钘ᜄ婯(쇼펊ኞ㨳㐖);
            this.鶊웎掬쟗钘 = new int[쇼펊ኞ㨳㐖];
            for (int i = 0; i < 쇼펊ኞ㨳㐖; ++i) {
                this.鶊웎掬쟗钘[i] = 츚㝛眓㐖웨(i, 甐곻钘ᜄ婯);
            }
            this.湗웨敤韤햖 = new float[쇼펊ኞ㨳㐖];
            for (int j = 0; j < 쇼펊ኞ㨳㐖; ++j) {
                if (j > 쇼펊ኞ㨳㐖 / 2) {
                    this.湗웨敤韤햖[j] = -((쇼펊ኞ㨳㐖 - j) / (float)쇼펊ኞ㨳㐖);
                }
                else {
                    this.湗웨敤韤햖[j] = (float)(j / 쇼펊ኞ㨳㐖);
                }
            }
            return;
        }
        throw new IllegalArgumentException(佉䂷㠠觯콵.杭쥡璧ꦱಽ[1]);
    }

    @Override
    public float[][] 刃塱蒕躚ၝ(final float[] array, final float[] array2) throws UnsupportedOperationException {
        final float[][] array3 = new float[2][array.length];
        this.錌浣䢶ᜄ㔢(true, array, array2, array3[0], array3[1]);
        return array3;
    }

    @Override
    public float[][] 錌浣䢶ᜄ㔢(final float[] array) throws UnsupportedOperationException {
        final float[][] array2 = new float[3][array.length];
        this.錌浣䢶ᜄ㔢(false, array, null, array2[0], array2[1]);
        array2[2] = this.湗웨敤韤햖.clone();
        return array2;
    }

    @Override
    public float[][] 錌浣䢶ᜄ㔢(final float[] array, final float[] array2) throws UnsupportedOperationException {
        final float[][] array3 = new float[3][array.length];
        this.錌浣䢶ᜄ㔢(false, array, array2, array3[0], array3[1]);
        array3[2] = this.湗웨敤韤햖.clone();
        return array3;
    }

    public void 錌浣䢶ᜄ㔢(final boolean b, final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        if (array.length == this.쇼펊ኞ㨳㐖) {
            for (int i = 0; i < this.쇼펊ኞ㨳㐖; ++i) {
                array3[this.鶊웎掬쟗钘[i]] = array[i];
            }
            if (array2 != null) {
                for (int j = 0; j < this.쇼펊ኞ㨳㐖; ++j) {
                    array4[this.鶊웎掬쟗钘[j]] = array2[j];
                }
            }
            int n = 1;
            double n2;
            if (!b) {
                n2 = 6.283185307179586;
            } else {
                n2 = -6.283185307179586;
            }
            for (int k = 2; k <= this.쇼펊ኞ㨳㐖; k <<= 1) {
                final double n3 = n2 / (float) k;
                final double n4 = -Math.sin(-2.0 * n3);
                final double n5 = -Math.sin(-n3);
                final double cos = Math.cos(-2.0 * n3);
                final double cos2 = Math.cos(-n3);
                final double n6 = 2.0 * cos2;
                for (int l = 0; l < this.쇼펊ኞ㨳㐖; l += k) {
                    double n7 = cos;
                    double n8 = cos2;
                    double n9 = n4;
                    double n10 = n5;
                    int n11 = l;
                    for (int n12 = 0; n12 < n; ++n12) {
                        final double n13 = n6 * n8 - n7;
                        n7 = n8;
                        n8 = n13;
                        final double n14 = n6 * n10 - n9;
                        n9 = n10;
                        n10 = n14;
                        final int n15 = n11 + n;
                        final double n16 = n13 * array3[n15] - n14 * array4[n15];
                        final double n17 = n13 * array4[n15] + n14 * array3[n15];
                        array3[n15] = (float) (array3[n11] - n16);
                        array4[n15] = (float) (array4[n11] - n17);
                        final int n18 = n11;
                        array3[n18] += (float) n16;
                        final int n19 = n11;
                        array4[n19] += (float) n17;
                        ++n11;
                    }
                }
                n = k;
            }
            if (b) {
                for (int n20 = 0; n20 < this.쇼펊ኞ㨳㐖; ++n20) {
                    final int n21 = n20;
                    array3[n21] /= this.쇼펊ኞ㨳㐖;
                    final int n22 = n20;
                    array4[n22] /= this.쇼펊ኞ㨳㐖;
                }
            }
            return;
        }
        throw new IllegalArgumentException(佉䂷㠠觯콵.杭쥡璧ꦱಽ[2] + this.쇼펊ኞ㨳㐖 + 佉䂷㠠觯콵.杭쥡璧ꦱಽ[3]);
    }


    private static int 甐곻钘ᜄ婯(int n) {
        int n3 = 0;
        while ((n & 1 << n3) == 0) {
            ++n3;
        }
        return n3;
    }

    private static int 콗䂷鏟Ꮺ㥇(int n, int n2) {
        int n3 = n;
        int n4 = 0;
        int n5 = 0;
        while (n5 < n2) {
            n4 = n4 << 1 | n3 & 1;
            n3 >>= 1;
            ++n5;
        }
        return n4;
    }

    private static int 츚㝛眓㐖웨(int n, int n2) {
        if (true) {
            return 佉䂷㠠觯콵.콗䂷鏟Ꮺ㥇(n, n2);
        }
        return 汌웎ใ㱙䡸[n2 - 1][n];
    }

    private static boolean 揩䖼瀧䆧鄡(int n) {
        return (n & n - 1) == 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        佉䂷㠠觯콵 佉䂷㠠觯콵2 = (佉䂷㠠觯콵)object;
        return this.쇼펊ኞ㨳㐖 == 佉䂷㠠觯콵2.쇼펊ኞ㨳㐖;
    }

    public int hashCode() {
        return this.쇼펊ኞ㨳㐖;
    }

    public String toString() {
        return 杭쥡璧ꦱಽ[4] + this.쇼펊ኞ㨳㐖 + '}';
    }

    public static int 卒쇽待曞啖(佉䂷㠠觯콵 佉䂷㠠觯콵2) {
        return 佉䂷㠠觯콵2.쇼펊ኞ㨳㐖;
    }
    static {
        佉䂷㠠觯콵.杭쥡璧ꦱಽ = new String[]{"N is not a power of 2", "N must be greater than 0", "Number of samples must be ", " for this instance of JavaFFT", "JavaFFT{N="};
        汌웎ใ㱙䡸 = new int[16][];
        final int n = 2;
        for (int i = 1; i <= 16; ++i) {
            佉䂷㠠觯콵.汌웎ใ㱙䡸[i - 1] = new int[n];
            int n2 = 0;
            while (n2 < n) {
                佉䂷㠠觯콵.汌웎ใ㱙䡸[i - 1][n2] = 콗䂷鏟Ꮺ㥇(n2, i);
                ++n2;
            }
        }
    }
}
