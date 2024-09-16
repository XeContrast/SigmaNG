package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.utils.render.rendermanagers.DynamicTexture;
import info.sigmaclient.sigma.utils.render.rendermanagers.TextureObf;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage addBorder(final BufferedImage bufferedImage, final int n, final int rgb) {
        final int width = bufferedImage.getWidth() + n * 2;
        final int height = bufferedImage.getHeight() + n * 2;
        final BufferedImage bufferedImage2 = new BufferedImage(width, height, bufferedImage.getType());
        if (rgb != -16711423) {
            for (int i = 0; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    bufferedImage2.setRGB(i, j, rgb);
                }
            }
        }
        for (int k = 0; k < bufferedImage.getWidth(); ++k) {
            for (int l = 0; l < bufferedImage.getHeight(); ++l) {
                bufferedImage2.setRGB(n + k, n + l, bufferedImage.getRGB(k, l));
            }
        }
        return bufferedImage2;
    }

    public static BufferedImage addBorder(final BufferedImage bufferedImage, final int n) {
        final BufferedImage scaledImage = scaleImage(bufferedImage, (bufferedImage.getWidth() + n * 2) / (float)bufferedImage.getWidth(), (bufferedImage.getHeight() + n * 2) / (float)bufferedImage.getHeight());
        for (int i = 0; i < bufferedImage.getWidth(); ++i) {
            for (int j = 0; j < bufferedImage.getHeight(); ++j) {
                scaledImage.setRGB(n + i, n + j, bufferedImage.getRGB(i, j));
            }
        }
        return scaledImage;
    }

    public static BufferedImage scaleImage(final BufferedImage bufferedImage, final double sx, final double sy) {
        BufferedImage bufferedImage2 = null;
        if (bufferedImage != null) {
            bufferedImage2 = new BufferedImage((int)(bufferedImage.getWidth() * sx), (int)(bufferedImage.getHeight() * sy), bufferedImage.getType());
            bufferedImage2.createGraphics().drawRenderedImage(bufferedImage, AffineTransform.getScaleInstance(sx, sy));
        }
        return bufferedImage2;
    }

    public static BufferedImage adjustHSB(final BufferedImage bufferedImage, final float n, final float n2, final float n3) {
        final int width = bufferedImage.getWidth();
        for (int height = bufferedImage.getHeight(), i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int rgb = bufferedImage.getRGB(j, i);
                final float[] rgBtoHSB = Color.RGBtoHSB(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF, null);
                bufferedImage.setRGB(j, i, Color.HSBtoRGB(Math.max(0.0f, Math.min(1.0f, rgBtoHSB[0] + n)), Math.max(0.0f, Math.min(1.0f, rgBtoHSB[1] * n2)), Math.max(0.0f, Math.min(1.0f, rgBtoHSB[2] + n3))));
            }
        }
        return bufferedImage;
    }

    public static DynamicTexture blurImage(final String str, final float n, final int n2) {
        try {
            final BufferedImage read;
            if(!TextureObf.ENABLE) {
                read = ImageIO.read(ImageUtils.class.getResourceAsStream("/assets/minecraft/sigmang/images/back.png"));
            }else{
                read = ImageIO.read(TextureObf.mappings.get("sigmang/images/back.png").inputStream);
            }
            final BufferedImage bufferedImage = new BufferedImage((int)(n * read.getWidth(null)), (int)(n * read.getHeight(null)), 2);
            final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
            graphics2D.scale(n, n);
            graphics2D.drawImage(read, 0, 0, null);
            graphics2D.dispose();
            return new DynamicTexture(adjustHSB(applyGaussianBlur(addBorder(bufferedImage, n2), n2), 0.0f, 1.1f, 0.0f));
        }
        catch (final IOException ex) {
            throw new IllegalStateException("SB");
        }
    }

    public static BufferedImage rotateImage(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage bufferedImage2 = new BufferedImage(height, width, bufferedImage.getType());
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                bufferedImage2.setRGB(height - 1 - j, width - 1 - i, bufferedImage.getRGB(i, j));
            }
        }
        return bufferedImage2;
    }

    public static Kernel createGaussianKernel(final float n) {
        final int n2 = (int)Math.ceil(n);
        final int width = n2 * 2 + 1;
        final float[] data = new float[width];
        final float n3 = n / 3.0f;
        final float n4 = 2.0f * n3 * n3;
        final float n5 = (float)Math.sqrt((float)(6.283185307179586 * n3));
        final float n6 = n * n;
        float n7 = 0.0f;
        int n8 = 0;
        for (int i = -n2; i <= n2; ++i) {
            final float n9 = (float)(i * i);
            if (n9 <= n6) {
                data[n8] = (float)Math.exp(-n9 / n4) / n5;
            }
            else {
                data[n8] = 0.0f;
            }
            n7 += data[n8];
            ++n8;
        }
        for (int j = 0; j < width; ++j) {
            final float[] array = data;
            final int n10 = j;
            array[n10] /= n7;
        }
        return new Kernel(width, 1, data);
    }

    public static BufferedImage applyGaussianBlur(final BufferedImage bufferedImage, final int n) {
        if (bufferedImage == null) {
            return bufferedImage;
        }
        if (bufferedImage.getWidth() <= n + n) {
            return bufferedImage;
        }
        if (bufferedImage.getHeight() > n + n) {
            final ConvolveOp convolveOp = new ConvolveOp(createGaussianKernel((float)n));
            return rotateImage(convolveOp.filter(rotateImage(convolveOp.filter(bufferedImage, null)), null)).getSubimage(n, n, bufferedImage.getWidth() - n - n, bufferedImage.getHeight() - n - n);
        }
        return bufferedImage;
    }
}
