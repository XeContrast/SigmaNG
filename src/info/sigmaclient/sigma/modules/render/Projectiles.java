package info.sigmaclient.sigma.modules.render;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.render.Render3DEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.Module;
import info.sigmaclient.sigma.sigma5.utils.ProjectilesTCLineRender;
import info.sigmaclient.sigma.sigma5.utils.ProjectilesTC;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.drawOutlinedBox;
import static info.sigmaclient.sigma.sigma5.utils.BoxOutlineESP.drawFilledBox;
import static info.sigmaclient.sigma.sigma5.utils.ProjectilesTCLineRender.calculateSin;
import static info.sigmaclient.sigma.sigma5.utils.ProjectilesTCLineRender.calculateCos;
import static info.sigmaclient.sigma.utils.render.RenderUtils.applyAlpha;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;


public class Projectiles extends Module {
    public ProjectilesTC initialVelocity;
    public ProjectilesTC gravity;
    public ProjectilesTC windResistance;
    public Projectiles() {
        super("Projectiles", Category.Render, "Predict the path of a projectile");
        this.initialVelocity = new ProjectilesTC(0.0f, 0.0f, 0.0f);
        this.gravity = new ProjectilesTC(0.0f, 0.0f, 0.0f);
        this.windResistance = new ProjectilesTC(0.0f, 0.0f, 0.0f);
    }

    @EventTarget
    public void onRender3DEvent(Render3DEvent event) {
        final ProjectilesTCLineRender lineRender = ProjectilesTCLineRender.lineRender(mc.player.getHeldItemMainhand().getItem());
        if (lineRender != null) {
            final float n = (float) Math.toRadians(mc.player.rotationPitch - 25.0f);
            final float n2 = (float) Math.toRadians(mc.player.rotationPitch);
            final double n3 = 0.20000000298023224;
            final double n4 = 0;
            final double n5 = calculateCos(n) * n4;
            final double n6 = calculateSin(n) * n4;
            final double n7 = mc.player.lastTickPosX + (mc.player.getPosX() - mc.player.lastTickPosX) * mc.timer.renderPartialTicks;
            final double n8 = mc.player.lastTickPosY + (mc.player.getPosY() - mc.player.lastTickPosY) * mc.timer.renderPartialTicks;
            final double n9 = mc.player.lastTickPosZ + (mc.player.getPosZ() - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks;
            GL11.glPushMatrix();
            GL11.glEnable(GL_LINE_SMOOTH);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
//            GL11.glEnable(32925);
            GL11.glDisable(2896);
            GL11.glShadeModel(7425);
            GL11.glDepthMask(false);
            GL11.glLineWidth(10.0f);
            GL11.glColor4d(0.0, 0.0, 0.0, 0.05000000074505806);
            GL11.glAlphaFunc(519, 0.0f);
            GL11.glBegin(3);
            final List<Vector3d> projectilePath = lineRender.projectilePath();
            RenderUtils.renderPos r = RenderUtils.getRenderPos();
            for (int i = 0; i < projectilePath.size(); ++i) {
                final Vector3d pathPoint = projectilePath.get(i);
                final double n10 = n5 - (i + 1) / (float) projectilePath.size() * n5;
                final double n11 = n6 - (i + 1) / (float) projectilePath.size() * n6;
                final double n12 = n3 - (i + 1) / (float) projectilePath.size() * n3;
                GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.05f * Math.min(1, i));
                GL11.glVertex3d(
                        pathPoint.x - r.renderPosX - n10,
                        pathPoint.y - r.renderPosY - n12,
                        pathPoint.z - r.renderPosZ - n11);
            }
            GL11.glEnd();
            GL11.glLineWidth(2.0f * SigmaNG.lineWidth);
            GL11.glColor4d(1.0, 1.0, 1.0, 0.75);
            GL11.glBegin(3);
            for (int j = 0; j < projectilePath.size(); ++j) {
                final Vector3d pathPoint2 = projectilePath.get(j);
                final double n13 = n5 - (j + 1) / (float) projectilePath.size() * n5;
                final double n14 = n6 - (j + 1) / (float) projectilePath.size() * n6;
                final double n15 = n3 - (j + 1) / (float) projectilePath.size() * n3;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f * Math.min(1, j));
                GL11.glVertex3d(
                        pathPoint2.x - r.renderPosX - n13,
                        pathPoint2.y - r.renderPosY - n15,
                        pathPoint2.z - r.renderPosZ - n14
                );
            }
            GL11.glEnd();
            GL11.glDisable(2929);
            if (lineRender.hitResult == null) {
                if (lineRender.hitEntity != null) {
                    final double n16 = lineRender.hitEntity.lastTickPosX +
                            (lineRender.hitEntity.getPosX() - lineRender.hitEntity.lastTickPosX) * mc.timer.renderPartialTicks -
                            r.renderPosX;
                    final double n17 = lineRender.hitEntity.lastTickPosY +
                            (lineRender.hitEntity.getPosY() - lineRender.hitEntity.lastTickPosY) * mc.timer.renderPartialTicks -
                            r.renderPosY;
                    final double n18 = lineRender.hitEntity.lastTickPosZ +
                            (lineRender.hitEntity.getPosZ() - lineRender.hitEntity.lastTickPosZ) * mc.timer.renderPartialTicks -
                            r.renderPosZ;
                    final double n19 = lineRender.hitEntity.getWidth() / 2.0f + 0.2f;
                    final AxisAlignedBB boundingBox = new AxisAlignedBB(n16 - n19, n17, n18 - n19, n16 + n19, n17 +
                            (lineRender.hitEntity.getHeight() + 0.1f), n18 + n19);
                    drawOutlinedBox(boundingBox, applyAlpha(-16723258, 0.1f));
                    drawFilledBox(boundingBox, 1.8f * SigmaNG.lineWidth, applyAlpha(-16723258, 0.1f));
                }
            } else {
                final double n20 = lineRender.hitX - r.renderPosX;
                final double n21 = lineRender.hitY - r.renderPosY;
                final double n22 = lineRender.hitZ - r.renderPosZ;
                GL11.glPushMatrix();
                GL11.glTranslated(n20, n21, n22);
                final BlockPos blockPos = new BlockPos(0, 0, 0).offset(((BlockRayTraceResult)lineRender.hitResult).getFace());
                GL11.glRotatef(45.0f, this.initialVelocity.windResistance((float) blockPos.getX()), this.initialVelocity.rotateY((float) (-blockPos.getY())), this.initialVelocity.rotateZ((float) blockPos.getZ()));
                GL11.glRotatef(90.0f, this.gravity.windResistance((float) blockPos.getZ()), this.gravity.rotateY((float) blockPos.getY()), this.gravity.rotateZ((float) (-blockPos.getX())));
                GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
                final AxisAlignedBB boundingBox2 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0, 1.0);
                drawOutlinedBox(boundingBox2, applyAlpha(-21931, 0.1f));
                drawFilledBox(boundingBox2, 1.8f * SigmaNG.lineWidth, applyAlpha(-21931, 0.1f));
                GL11.glPopMatrix();
            }
            GL11.glDisable(2896);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
//            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
//            GL11.glDisable(32925);
            GL11.glDisable(2848);
            GL11.glPopMatrix();

        }
        GlStateManager.disableLighting();
        RenderUtils.startBlend();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4d(1, 1, 1, 1);
        
    }
}
