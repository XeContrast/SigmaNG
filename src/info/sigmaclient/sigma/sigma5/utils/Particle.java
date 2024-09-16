package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.RenderUtils;

import java.awt.*;

import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;

public class Particle {
    private static String[] randomStrings;
    private float initialPosX;
    private float initialPosY;
    public float posX;
    public float posY;
    public float size;
    private float lifetime;
    private float velocityX;
    private float velocityY;
    private boolean isAlive;
    private float maxVelocity;
    private RandomGenerator randomGenerator;
    public float acceleration;
    public Color color;
    public int age;

    public Particle(final float n, final float n2, final float size) {
        this.maxVelocity = 1.0f;
        this.randomGenerator = new RandomGenerator();
        this.color = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        this.age = 0;
        this.initialPosX = n;
        this.posX = n;
        this.initialPosY = n2;
        this.posY = n2;
        this.size = size;
        this.initializeParticle();
    }

    public Particle(final float n, final float n2) {
        this.maxVelocity = 1.0f;
        this.randomGenerator = new RandomGenerator();
        this.color = new Color(1.0f, 1.0f, 1.0f, 0.5f);
        this.age = 0;
        this.initialPosX = n;
        this.posX = n;
        this.initialPosY = n2;
        this.posY = n2;
        this.size = this.randomGenerator.nextInt(1, 3) + this.randomGenerator.nextFloat();
        this.initializeParticle();
    }

    private void initializeParticle() {
        this.velocityX = this.randomGenerator.nextFloat() % this.maxVelocity;
        this.velocityY = this.randomGenerator.nextFloat() % this.maxVelocity;
        this.lifetime = (this.randomGenerator.nextFloat() * this.size + 15.0f) % 25.0f;
        this.isAlive = this.randomGenerator.nextBoolean();
        this.acceleration = this.randomGenerator.nextFloat() / 2.0f;
        if (this.randomGenerator.nextBoolean()) {
            this.acceleration *= -1.0f;
        }
    }

    public void updatePosition(final float n) {
        RenderUtils.drawRect(this.posX * 2f, this.posY * 2f, this.size * 2f, applyColor(this.color.getRGB(), n * 0.7f));
    }

    public void render(final Renderer Renderer) {
        this.posX += Renderer.getDeltaX() + this.acceleration;
        this.initialPosX += Renderer.getDeltaX() + this.acceleration;
        this.posY += this.velocityY;
    }
}