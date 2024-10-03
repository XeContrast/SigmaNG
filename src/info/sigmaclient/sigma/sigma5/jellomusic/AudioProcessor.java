package info.sigmaclient.sigma.sigma5.jellomusic;


public class AudioProcessor implements AudioProcessorInterface {
    private static String[] errorMessages;
    private static final int MAX_SIZE = 16;
    private static final int[][] bitReversalTable;
    private final int sampleSize;
    private final int[] bitReversedIndices;
    private float[] windowFunction;

    public AudioProcessor(int sampleSize) {
        if (!isPowerOfTwo(sampleSize)) {
            throw new IllegalArgumentException(AudioProcessor.errorMessages[0]);
        }
        if (sampleSize > 0) {
            this.sampleSize = sampleSize;
            final int calculateLog2 = calculateLog2(sampleSize);
            this.bitReversedIndices = new int[sampleSize];
            for (int i = 0; i < sampleSize; ++i) {
                this.bitReversedIndices[i] = getBitReversedIndex(i, calculateLog2);
            }
            this.windowFunction = new float[sampleSize];
            for (int j = 0; j < sampleSize; ++j) {
                if (j > sampleSize / 2) {
                    this.windowFunction[j] = -((sampleSize - j) / (float) sampleSize);
                }
                else {
                    this.windowFunction[j] = (float)(j / sampleSize);
                }
            }
            return;
        }
        throw new IllegalArgumentException(AudioProcessor.errorMessages[1]);
    }

    @Override
    public float[][] processStereoData(final float[] array, final float[] array2) throws UnsupportedOperationException {
        final float[][] array3 = new float[2][array.length];
        this.processAudioData(true, array, array2, array3[0], array3[1]);
        return array3;
    }

    @Override
    public float[][] processAudioData(final float[] array) throws UnsupportedOperationException {
        final float[][] array2 = new float[3][array.length];
        this.processAudioData(false, array, null, array2[0], array2[1]);
        array2[2] = this.windowFunction.clone();
        return array2;
    }

    @Override
    public float[][] processAudioData(final float[] array, final float[] array2) throws UnsupportedOperationException {
        final float[][] array3 = new float[3][array.length];
        this.processAudioData(false, array, array2, array3[0], array3[1]);
        array3[2] = this.windowFunction.clone();
        return array3;
    }

    public void processAudioData(final boolean b, final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        if (array.length == this.sampleSize) {
            for (int i = 0; i < this.sampleSize; ++i) {
                array3[this.bitReversedIndices[i]] = array[i];
            }
            if (array2 != null) {
                for (int j = 0; j < this.sampleSize; ++j) {
                    array4[this.bitReversedIndices[j]] = array2[j];
                }
            }
            int n = 1;
            double n2;
            if (!b) {
                n2 = 6.283185307179586;
            } else {
                n2 = -6.283185307179586;
            }
            for (int k = 2; k <= this.sampleSize; k <<= 1) {
                final double n3 = n2 / (float) k;
                final double n4 = -Math.sin(-2.0 * n3);
                final double n5 = -Math.sin(-n3);
                final double cos = Math.cos(-2.0 * n3);
                final double cos2 = Math.cos(-n3);
                final double n6 = 2.0 * cos2;
                for (int l = 0; l < this.sampleSize; l += k) {
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
                for (int n20 = 0; n20 < this.sampleSize; ++n20) {
                    final int n21 = n20;
                    array3[n21] /= this.sampleSize;
                    final int n22 = n20;
                    array4[n22] /= this.sampleSize;
                }
            }
            return;
        }
        throw new IllegalArgumentException(AudioProcessor.errorMessages[2] + this.sampleSize + AudioProcessor.errorMessages[3]);
    }


    private static int calculateLog2(int n) {
        int n3 = 0;
        while ((n & 1 << n3) == 0) {
            ++n3;
        }
        return n3;
    }

    private static int reverseBits(int n, int n2) {
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

    private static int getBitReversedIndex(int n, int n2) {
        if (true) {
            return AudioProcessor.reverseBits(n, n2);
        }
        return bitReversalTable[n2 - 1][n];
    }

    private static boolean isPowerOfTwo(int n) {
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
        AudioProcessor AudioProcessor2 = (AudioProcessor)object;
        return this.sampleSize == AudioProcessor2.sampleSize;
    }

    public int hashCode() {
        return this.sampleSize;
    }

    public String toString() {
        return errorMessages[4] + this.sampleSize + '}';
    }

    public static int RenderUtils(AudioProcessor AudioProcessor2) {
        return AudioProcessor2.sampleSize;
    }
    static {
        AudioProcessor.errorMessages = new String[]{"N is not a power of 2", "N must be greater than 0", "Number of samples must be ", " for this instance of JavaFFT", "JavaFFT{N="};
        bitReversalTable = new int[16][];
        final int n = 2;
        for (int i = 1; i <= 16; ++i) {
            AudioProcessor.bitReversalTable[i - 1] = new int[n];
            int n2 = 0;
            while (n2 < n) {
                AudioProcessor.bitReversalTable[i - 1][n2] = reverseBits(n2, i);
                ++n2;
            }
        }
    }
}
