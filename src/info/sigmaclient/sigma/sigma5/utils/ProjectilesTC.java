package info.sigmaclient.sigma.sigma5.utils;

//ProjectilesTC——————>Projectile Trajectory Calculation

public class ProjectilesTC {
    private static String[] trajectoryData;
    public float velocityX;
    public float velocityY;
    public float velocityZ;
    public float resistance;

    public ProjectilesTC(final float velocityX, final float velocityY, final float velocityZ) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.resistance = 0.1f;
    }

    public ProjectilesTC(final float n, final float n2, final float n3, final float resistance) {
        this(n, n2, n3);
        this.resistance = resistance;
    }

    public float windResistance(final float n) {
        if (n != this.velocityX) {
            this.velocityX += (n - this.velocityX) * this.resistance;
        }
        return this.velocityX;
    }

    public float rotateY(final float n) {
        if (n != this.velocityY) {
            this.velocityY += (n - this.velocityY) * this.resistance;
        }
        return this.velocityY;
    }

    public float rotateZ(final float n) {
        if (n != this.velocityZ) {
            this.velocityZ += (n - this.velocityZ) * this.resistance;
        }
        return this.velocityZ;
    }
}
    