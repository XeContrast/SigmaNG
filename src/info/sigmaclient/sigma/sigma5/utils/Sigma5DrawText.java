package info.sigmaclient.sigma.sigma5.utils;

import info.sigmaclient.sigma.gui.font.JelloFontRenderer;

public class Sigma5DrawText {
    public static void drawString(JelloFontRenderer fontRenderer, float x, float y, String str, int color){
        fontRenderer.drawNoBSString(
                str, x, y, color);
    }
}
