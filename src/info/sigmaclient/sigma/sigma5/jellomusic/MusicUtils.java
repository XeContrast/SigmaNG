package info.sigmaclient.sigma.sigma5.jellomusic;

import javax.sound.sampled.*;
import top.fl0wowp4rty.phantomshield.annotations.Native;


public class MusicUtils {
    SourceDataLine ꁈ硙ၝ敤䢶;
    public int 鄡웎䬹卒웨 = 50; // 0 - 100
    public void 瀧揩뎫㦖婯(final SourceDataLine sourceDataLine, final int n) {
        try {
            final FloatControl floatControl = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            final BooleanControl booleanControl = (BooleanControl)sourceDataLine.getControl(BooleanControl.Type.MUTE);
            if (n == 0) {
                booleanControl.setValue(true);
            }
            else {
                booleanControl.setValue(false);
                floatControl.setValue((float)(Math.log(n / 100.0) / Math.log(10.0) * 20.0));
            }
        }
        catch (final Exception ex) {}
    }
//    public void playMusic() throws LineUnavailableException {
//        final AudioFormat format = new AudioFormat(
//                8000, 16, 1, true, true
//        );
//        (this.ꁈ硙ၝ敤䢶 = AudioSystem.getSourceDataLine(format)).open();
//        ꁈ硙ၝ敤䢶.start();
//        while (堧鶊ศ콗綋붛.쥡髾䬾쇽鄡霥()) {
//            while (!this.츚픓䬾㦖眓) {
//                Thread.sleep(300L);
//                final double[] array2 = new double[0];
//                this.펊瀳藸鶲픓.clear();
//                if (Thread.interrupted()) {
//                    this.ꁈ硙ၝ敤䢶.close();
//                    return;
//                }
//            }
//            䕦쥅蛊쿨쥅蚳.䬹핇䄟㢸㥇欫(堧鶊ศ콗綋붛.Ꮤ侃쬫亟值浦().挐唟浣䄟䈔阢(), 佉浦䖼䡸䕦嘖);
//            佉浦䖼䡸䕦嘖.곻洝韤䴂湗堍();
//            final byte[] array3;
//            this.ꁈ硙ၝ敤䢶.write(array3, 0, array3.length);;
//            final float[] array4;
//            new 佉䂷㠠觯콵(array4.length).錌浣䢶ᜄ㔢(array4);
//            final float[][] array5;
//            this.펊瀳藸鶲픓.add(㐖䆧쿨콵浦(array5[0], array5[1]));
//            if (this.펊瀳藸鶲픓.size() > 18) {
//                this.펊瀳藸鶲픓.remove(0);
//            }
//            this.瀧揩뎫㦖婯(this.ꁈ硙ၝ敤䢶, this.鄡웎䬹卒웨);
//            if (!Thread.interrupted()) {
//                this.哝놣㻣햠뗴 = Math.round(堧鶊ศ콗綋붛.뵯㝛웎ᢻ埙핇());
//                this.鞞眓亟䣓웎 = 堧鶊ศ콗綋붛.酭䈔햖曞哺霥();
//                if (this.콗哝ᢻ묙瀳) {
//                    堧鶊ศ콗綋붛.觯韤ᔎ挐卒霥(this.䡸묙甐卫䡸);
//                    this.哝놣㻣햠뗴 = (long)this.䡸묙甐卫䡸;
//                    this.콗哝ᢻ묙瀳 = false;
//                }
//            }
//            if (!堧鶊ศ콗綋붛.쥡髾䬾쇽鄡霥() && (this.杭牰쟗粤韤 == 蓳浣쥡渺疂.꿩鏟韤곻셴 || (this.杭牰쟗粤韤 == 蓳浣쥡渺疂.좯亟ใ洝䴂 && this.훔ၝ쇽㱙딨.㥇㝛W唟W.size() == 1))) {
//                堧鶊ศ콗綋붛.觯韤ᔎ挐卒霥(0.0);
//                this.哝놣㻣햠뗴 = 0L;
//            }
//            if (Thread.interrupted()) {
//                this.ꁈ硙ၝ敤䢶.close();
//                return;
//            }
//        }
//        ꁈ硙ၝ敤䢶.close();
//    }
    public double[] 㐖䆧쿨콵浦(final float[] array, final float[] array2) {
        final double[] array3 = new double[array.length / 2];
        for (int i = 0; i < array3.length; ++i) {
            array3[i] = Math.sqrt(array[i] * array[i] + array2[i] * array2[i]);
        }
        return array3;
    }

    private static int ꁈ쬷钘낛ಽ(final byte[] array, final int n, final int n2) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            n3 += (array[n + i] & 0xFF) << 8 * i;
        }
        return n3;
    }

    private static int ศ䩉㔢쇼홵(final byte[] array, final int n, final int n2) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            n3 += (array[n + i] & 0xFF) << 8 * (n2 - i - 1);
        }
        return n3;
    }
    public static float[] 䣓랾啖鄡䈔(final byte[] array, final AudioFormat audioFormat) {
        final float[] array2 = new float[array.length / audioFormat.getFrameSize()];
        for (int i = 0; i < array.length; i += audioFormat.getFrameSize()) {
            array2[i / audioFormat.getFrameSize()] = (audioFormat.isBigEndian() ? ศ䩉㔢쇼홵(array, i, audioFormat.getFrameSize()) : ꁈ쬷钘낛ಽ(array, i, audioFormat.getFrameSize())) / 32768.0f;
        }
        return array2;
    }
}
