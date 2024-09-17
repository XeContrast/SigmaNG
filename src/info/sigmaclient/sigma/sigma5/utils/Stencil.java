package info.sigmaclient.sigma.sigma5.utils;

import org.lwjgl.opengl.GL11;

import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.resetFramebufferDepth;

public class Stencil {

    public static void startStencil() {
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

    public static void applyStencilOperation(final BoxOutlineESP.StencilOperation StencilOperation) {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0);
        GL11.glStencilFunc((StencilOperation != StencilOperation.REPLACE) ? 517 : 514, 1, 1);
    }

    public static void endStencil() {
        GL11.glStencilMask(-1);
        GL11.glDisable(2960);
        GL11.glPopMatrix();
//        㕠鄡呓ᢻ낛.鶲Ꮺ掬ಽ璧 = false;
    }
}
