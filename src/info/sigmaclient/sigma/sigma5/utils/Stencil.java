package info.sigmaclient.sigma.sigma5.utils;

import org.lwjgl.opengl.GL11;

import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.resetFramebufferDepth;

public class Stencil {

    public static void 䢶웎쥦걾醧딨() {
        GL11.glPushMatrix();
        resetFramebufferDepth();
        GL11.glEnable(2960);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
        GL11.glStencilFunc(512, 1, 1);
        GL11.glStencilOp(7681, 7680, 7680);
        GL11.glStencilMask(1);
        GL11.glClear(1024);
//        㕠鄡呓ᢻ낛.鶲Ꮺ掬ಽ璧 = true;
    }

    public static void ᙽ붛셴쥦㠠醧(final BoxOutlineESP.ࡅ醧콵揩㨳 ࡅ醧콵揩㨳) {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0);
        GL11.glStencilFunc((ࡅ醧콵揩㨳 != ࡅ醧콵揩㨳.㔢䬹ꁈ핇呓) ? 517 : 514, 1, 1);
    }

    public static void 츚堧堧䬾ศ㢸() {
        GL11.glStencilMask(-1);
        GL11.glDisable(2960);
        GL11.glPopMatrix();
//        㕠鄡呓ᢻ낛.鶲Ꮺ掬ಽ璧 = false;
    }
}
