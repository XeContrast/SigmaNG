package info.sigmaclient.sigma.sigma5.jellomusic;

public interface AudioProcessorInterface
{
    float[][] processAudioData(final float[] p0) throws UnsupportedOperationException;
    
    float[][] processAudioData(final float[] p0, final float[] p1) throws UnsupportedOperationException;
    
    float[][] processStereoData(final float[] p0, final float[] p1) throws UnsupportedOperationException;
}