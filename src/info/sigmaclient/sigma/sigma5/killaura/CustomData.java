package info.sigmaclient.sigma.sigma5.killaura;


public class CustomData {
    private static String[] someStrings;
    private long expirationTime;

    public CustomData(final long n) {
        this.expirationTime = System.currentTimeMillis() + n;
    }

    public boolean isValid() {
        return this.expirationTime - System.currentTimeMillis() < 0L;
    }
}
