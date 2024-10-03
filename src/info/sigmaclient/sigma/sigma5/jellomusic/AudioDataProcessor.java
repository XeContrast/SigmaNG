package info.sigmaclient.sigma.sigma5.jellomusic;


public class AudioDataProcessor {
    private static String[] audioData;
    private int sampleRate;
    private int channels;
    private int bitDepth;
    private double duration;
    private double fileSize;
    private double bitrate;
    private byte[] audioBytes;
    private boolean isProcessed;

    public AudioDataProcessor() {
        this.audioBytes = new byte[0];
        this.sampleRate = 0;
        this.channels = 0;
        this.bitDepth = 0;
        this.isProcessed = true;
    }

    public byte[] getAudioBytes() {
        return this.audioBytes;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public int getChannels() {
        return this.channels;
    }

    public int getBitDepth() {
        return this.bitDepth;
    }

    public double getDuration() {
        return this.duration;
    }

    public double getFileSize() {
        return this.fileSize;
    }

    public double getBitrate() {
        return this.bitrate;
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }

    public void setProcessed(final boolean isProcessed) {
        if (isProcessed != this.isProcessed) {
            for (int i = 0; i < this.audioBytes.length; i += 2) {
                final byte b = this.audioBytes[i];
                this.audioBytes[i] = this.audioBytes[i + 1];
                this.audioBytes[i + 1] = b;
            }
            this.isProcessed = isProcessed;
        }
    }

    public void updateAudioData(final byte[] audioBytes, final int sampleRate, final int channels, final int bitDepth, final int n) {
        this.audioBytes = audioBytes;
        this.sampleRate = sampleRate;
        this.channels = channels;
        this.bitDepth = bitDepth;
        if (sampleRate != 0) {
            final int n2 = audioBytes.length / (bitDepth / 8 * channels);
            this.duration = n2 / (double) sampleRate;
            this.fileSize = n2 * bitDepth * channels / this.duration;
            this.bitrate = n / this.duration;
        } else {
            this.duration = 0.0;
            this.fileSize = 0.0;
            this.bitrate = 0.0;
        }
    }
}
