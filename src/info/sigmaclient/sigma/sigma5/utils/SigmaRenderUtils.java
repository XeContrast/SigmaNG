package info.sigmaclient.sigma.sigma5.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

public class SigmaRenderUtils {
    public static void 牰蓳躚唟捉璧(final float n, final float n2, final float n3, final int n4) {
        GlStateManager.color4f(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
//        堧骰쥅ᙽ펊.聛ᜄ甐䡸啖ꪕ().轐붛픓딨ꈍ綋();
//        啖阢轐殢佉.ใ陂硙湗待ꦱ();
//        啖阢轐殢佉.甐ᜄ햠괠硙䈔(770, 771, 1, 0);

        RenderUtils.startBlend();
        GlStateManager.disableTexture();
        GlStateManager.color4f(n6, n7, n8, n5);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glPointSize(n3 * SigmaNG.lineWidth);
        GL11.glBegin(0);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glDisable(3042);
        GlStateManager.enableTexture();
//        GlStateManager.钘曞鏟曞䎰쇽();
    }

    public static void stopGlScissor() {
        GL11.glDisable(3089);
    }
    public static void 롤婯鷏붛浣弻() {
        StencilUtil.uninitStencilBuffer();
//        final IntBuffer intBuffer2 = BufferUtils.createIntBuffer(16);
//        GL11.glGetIntegerv(GL_SCISSOR_BOX, intBuffer2);
//        嘖랾值Ꮺ敤.push(intBuffer2);
        
//        if (嘖랾值Ꮺ敤.isEmpty()) {
//            GL11.glDisable(3089);
//        }
//        else {
//            final IntBuffer intBuffer = 嘖랾值Ꮺ敤.pop();
//            GL11.glScissor(intBuffer.get(0), intBuffer.get(1), intBuffer.get(2), intBuffer.get(3));
//        }
    }
    static Stack<IntBuffer> 嘖랾值Ꮺ敤 = new Stack<IntBuffer>();

    public static void 汌ꪕ蒕姮Ⱋ樽(final float n, final float n2, final float n3, final float n4, final int n5) {
        RenderUtils.汌ꪕ蒕姮Ⱋ樽(n, n2, n3, n4, n5, n, n2);
    }
    public static void 㠠Ꮤ曞佉䩜鱀(float n, float n2, float n3, float n4, final int n5) {
        if (n < n3) {
            final float n6 = (float)n;
            n = n3;
            n3 = (float)n6;
        }
        if (n2 < n4) {
            final float n7 = (float)n2;
            n2 = n4;
            n4 = (float)n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final Tessellator 聛ᜄ甐䡸啖ꪕ = Tessellator.getInstance();
        final BufferBuilder 轐붛픓딨ꈍ綋 = 聛ᜄ甐䡸啖ꪕ.getBuffer();
//        啖阢轐殢佉.펊眓䄟堍柿湗();
//        啖阢轐殢佉.ใ陂硙湗待ꦱ();
//        啖阢轐殢佉.甐ᜄ햠괠硙䈔(卒䕦醧쬷缰.ꦱ罡姮钘픓頉, 펊䂷綋ಽ䄟.蛊딨ศ蕃ぶ붛, 卒䕦醧쬷缰.햠蚳ኞ㥇늦㐖, 펊䂷綋ಽ䄟.阢酭늦쥡쿨螜);
        GlStateManager.disableTexture();
        RenderUtils.startBlend();
        GlStateManager.color4f(0,0,0,0);
        GlStateManager.color4f(n9, n10, n11, n8);
        轐붛픓딨ꈍ綋.begin(7, DefaultVertexFormats.POSITION);
        轐붛픓딨ꈍ綋.pos(n, n4, 0.0).endVertex();
        轐붛픓딨ꈍ綋.pos(n3, n4, 0.0).endVertex();
        轐붛픓딨ꈍ綋.pos(n3, n2, 0.0).endVertex();
        轐붛픓딨ꈍ綋.pos(n, n2, 0.0).endVertex();
        聛ᜄ甐䡸啖ꪕ.draw();
        GlStateManager.enableTexture();
//        啖阢轐殢佉.钘曞鏟曞䎰쇽();
    }
    public static void 퉧핇樽웨䈔属S(final float n, final float n2, final float n3, final float n4) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect((int)n, (int)n2, (int)n3, (int)n4,-1);
        StencilUtil.readStencilBuffer(1);
//        퉧핇樽웨䈔属((int)n, (int)n2, (int)n3, (int)n4, true);
    }
    public static void 퉧핇樽웨䈔属(final float n, final float n2, final float n3, final float n4) {
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect((float)n, (float)n2, (float)n3, (float)n4,-1);
        StencilUtil.readStencilBuffer(1);
//        퉧핇樽웨䈔属((int)n, (int)n2, (int)n3, (int)n4, true);
    }

    public static void ꦱ敤圭瀳䴂甐(final float n, final float n2, final float n3, final float n4) {
        퉧핇樽웨䈔属((int)n, (int)n2, (int)n + (int)n3, (int)n2 + (int)n4, true);
    }

    public static void 퉧핇樽웨䈔属(final int n, final int n2, final int n3, final int n4) {
        퉧핇樽웨䈔属(n, n2, n3, n4, false);
    }

    public static float 䡸웎汌샱䈔뗴() {
        return (float) Minecraft.getInstance().getMainWindow().getGuiScaleFactor();
    }

    public static float[] 좯觯퉧䖼呓䎰(final int n, final int n2) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloatv(2982, floatBuffer);
        final float n3 = floatBuffer.get(0) * n + floatBuffer.get(4) * n2 + floatBuffer.get(8) * 0.0f + floatBuffer.get(12);
        final float n4 = floatBuffer.get(1) * n + floatBuffer.get(5) * n2 + floatBuffer.get(9) * 0.0f + floatBuffer.get(13);
        final float n5 = floatBuffer.get(3) * n + floatBuffer.get(7) * n2 + floatBuffer.get(11) * 0.0f + floatBuffer.get(15);
        return new float[] { (float)Math.round(n3 / n5 * 䡸웎汌샱䈔뗴()), (float)Math.round(n4 / n5 * 䡸웎汌샱䈔뗴()) };
    }
    public static void 퉧핇樽웨䈔属(float n, float n2, float n3, float n4, final boolean b, float a) {
        if (true) {
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect((float) n, (float) n2, (float) n3, (float) n4, new Color(1,1,1,a).getRGB());
            StencilUtil.readStencilBuffer(1);
            return;
        }
    }
    public static void 퉧핇樽웨䈔属(int n, int n2, int n3, int n4, final boolean b) {
        if(true) {
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRect((float)n, (float)n2, (float)n3, (float)n4,new Color(255,255,255,255).getRGB());
            StencilUtil.readStencilBuffer(1);
            return;
        }
        if (!b) {
//            n *= (int)SigmaNG.lineWidth;
//            n2 *= (int)SigmaNG.lineWidth;
//            n3 *= (int)SigmaNG.lineWidth;
//            n4 *= (int)SigmaNG.lineWidth;
        }
        else {
            final float[] 좯觯퉧䖼呓䎰 = 좯觯퉧䖼呓䎰(n, n2);
            n = (int)좯觯퉧䖼呓䎰[0];
            n2 = (int)좯觯퉧䖼呓䎰[1];
            final float[] 좯觯퉧䖼呓䎰2 = 좯觯퉧䖼呓䎰(n3, n4);
            n3 = (int)좯觯퉧䖼呓䎰2[0];
            n4 = (int)좯觯퉧䖼呓䎰2[1];
        }
//        if (GL11.glIsEnabled(3089)) {
//            final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
//            GL11.glGetIntegerv(3088, intBuffer);
//            嘖랾值Ꮺ敤.push(intBuffer);
//            final int value = intBuffer.get(0);
//            final int n5 = Minecraft.getMinecraft().getMainWindow().getHeight() - intBuffer.get(1) - intBuffer.get(3);
//            final int n6 = value + intBuffer.get(2);
//            final int n7 = n5 + intBuffer.get(3);
//            if (n < value) {
//                n = value;
//            }
//            if (n2 < n5) {
//                n2 = n5;
//            }
//            if (n3 > n6) {
//                n3 = n6;
//            }
//            if (n4 > n7) {
//                n4 = n7;
//            }
//            if (n2 > n4) {
//                n4 = n2;
//            }
//            if (n > n3) {
//                n3 = n;
//            }
//        }
//        final int n8 = n;
//        final int n9 = Minecraft.getMinecraft().getMainWindow().getHeight() - n4;
//        final int n10 = n3 - n;
//        final int n11 = n4 - n2;
//        GL11.glEnable(3089);
//        if (n10 >= 0 && n11 >= 0) {
//            GL11.glScissor(n8, n9, n10, n11);
//        }
    }
    public static void glScissor(int n, int n2, int n3, int n4) {
        final float[] 좯觯퉧䖼呓䎰 = 좯觯퉧䖼呓䎰(n, n2);
        n = (int) 좯觯퉧䖼呓䎰[0];
        n2 = (int) 좯觯퉧䖼呓䎰[1];
        final float[] 좯觯퉧䖼呓䎰2 = 좯觯퉧䖼呓䎰(n3, n4);
        n3 = (int) 좯觯퉧䖼呓䎰2[0];
        n4 = (int) 좯觯퉧䖼呓䎰2[1];
        final int n8 = n;
        final int n9 = Minecraft.getInstance().getMainWindow().getHeight() - n4;
        final int n10 = n3 - n;
        final int n11 = n4 - n2;
        GL11.glEnable(3089);
        if (n10 >= 0 && n11 >= 0) {
            GL11.glScissor(n8, n9, n10, n11);
        }
    }
    public static void 䬹鞞葫Ꮀ待湗2(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        㠠Ꮤ曞佉䩜鱀(n + n5 / 2f, n2 + n5, n + n3 - n5 / 2f, n2 + n4 - n5, n6);
        㠠Ꮤ曞佉䩜鱀(n + n5, n2 + n5 - n5 / 2f, n + n3 - n5, n2 + n5, n6);
        㠠Ꮤ曞佉䩜鱀(n + n5, n2 + n4 - n5, n + n3 - n5, n2 + n4 - n5 / 2f, n6);

        퉧핇樽웨䈔属(n, n2, n + n5, n2 + n5);
        牰蓳躚唟捉璧(n + n5, n2 + n5, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属(n + n3 - n5, n2, n + n3, n2 + n5);
        牰蓳躚唟捉璧(n - n5 + n3, n2 + n5, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属(n, n2 + n4 - n5, n + n5, n2 + n4);
        牰蓳躚唟捉璧(n + n5, n2 - n5 + n4, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属(n + n3 - n5, n2 + n4 - n5, n + n3, n2 + n4);
        牰蓳躚唟捉璧(n - n5 + n3, n2 - n5 + n4, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
    }
    public static void 䬹鞞葫Ꮀ待湗(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        㠠Ꮤ曞佉䩜鱀(n, n2 + n5, n + n3, n2 + n4 - n5, n6);
        㠠Ꮤ曞佉䩜鱀(n + n5, n2, n + n3 - n5, n2 + n5, n6);
        㠠Ꮤ曞佉䩜鱀(n + n5, n2 + n4 - n5, n + n3 - n5, n2 + n4, n6);

        퉧핇樽웨䈔属S(n, n2, n + n5, n2 + n5);
        牰蓳躚唟捉璧(n + n5, n2 + n5, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属S(n + n3 - n5, n2, n + n3, n2 + n5);
        牰蓳躚唟捉璧(n - n5 + n3, n2 + n5, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属S(n, n2 + n4 - n5, n + n5, n2 + n4);
        牰蓳躚唟捉璧(n + n5, n2 - n5 + n4, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
        퉧핇樽웨䈔属S(n + n3 - n5, n2 + n4 - n5, n + n3, n2 + n4);
        牰蓳躚唟捉璧(n - n5 + n3, n2 - n5 + n4, n5 * 2.0f, n6);
        롤婯鷏붛浣弻();
    }
}
