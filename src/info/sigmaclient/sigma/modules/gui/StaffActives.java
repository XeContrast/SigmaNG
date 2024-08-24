package info.sigmaclient.sigma.modules.gui;

import info.sigmaclient.sigma.config.values.NumberValue;
import info.sigmaclient.sigma.event.annotations.EventTarget;
import info.sigmaclient.sigma.event.impl.player.WindowUpdateEvent;
import info.sigmaclient.sigma.event.impl.render.RenderEvent;
import info.sigmaclient.sigma.modules.Category;
import info.sigmaclient.sigma.modules.RenderModule;
import info.sigmaclient.sigma.utils.font.FontUtil;
import info.sigmaclient.sigma.utils.key.ClickUtils;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import info.sigmaclient.sigma.utils.render.StencilUtil;
import info.sigmaclient.sigma.utils.render.anims.PartialTicksAnim;
import net.minecraft.client.network.play.NetworkPlayerInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class StaffActives extends RenderModule {
    public NumberValue x = new NumberValue("X", 0, 0, 10000, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return true;
        }
    };
    public NumberValue y = new NumberValue("Y", 0, 0, 10000, NumberValue.NUMBER_TYPE.INT){
        @Override
        public boolean isHidden() {
            return true;
        }
    };
    public void setX(float v){
        x.setValue(v);
    }
    public void setY(float v){
        y.setValue(v);
    }
    public float getX(){
        return x.getValue().floatValue();
    }
    public float getY(){
        return y.getValue().floatValue();
    }
    PartialTicksAnim widthAnim = new PartialTicksAnim(0);
    PartialTicksAnim heightAnim = new PartialTicksAnim(0);
    ArrayList<String> staffs = new ArrayList<>();
    public StaffActives() {
        super("ActivesStaff", Category.Gui, "Show actives staff", true);
    }
    public ArrayList<String> getStaffs(){
        ArrayList<String> names = new ArrayList<>();
        if(mc.getConnection() == null) return names;

        for(Map.Entry<UUID, NetworkPlayerInfo> i : mc.getConnection().playerInfoMap.entrySet()){
            try {
                if (i.getValue() != null && i.getValue().getDisplayName() != null && i.getValue().getDisplayName().getString().toLowerCase().contains("staff ") || Objects.requireNonNull(i.getValue()).getDisplayName().getString().toLowerCase().contains("st.moder ") || i.getValue().getDisplayName().getString().toLowerCase().contains("admin ") || i.getValue().getDisplayName().getString().toLowerCase().contains("yt ") || i.getValue().getDisplayName().getString().toLowerCase().contains("helper ") || i.getValue().getDisplayName().getString().toLowerCase().contains("d.helper ") || i.getValue().getDisplayName().getString().toLowerCase().contains("st.helper ") || i.getValue().getDisplayName().getString().toLowerCase().contains("gl.moder ")
                ) {
                    names.add(i.getValue().getDisplayName().getString());
                }
            }catch (NullPointerException ignored){
            }
        }
        return names;
    }
    @EventTarget
    public void onWindowUpdateEvent(WindowUpdateEvent event) {
        staffs = getStaffs();
        float width = 80, height = 15, split = 12;
        for(String p : staffs){
            width = Math.max(FontUtil.sfuiFont16.getStringWidth(p) + 10, width);
            height += split;
        }
        widthAnim.interpolate(width, 5);
        heightAnim.interpolate(height + 3, 5);
       
    }
    public boolean isHover(double mx, double my){
        float width = widthAnim.getValue(), height = heightAnim.getValue();
        float x = this.x.getValue().floatValue(), y = this.y.getValue().floatValue();
        return ClickUtils.isClickable(x, y, x + width, y + height, mx, my);
    }
     @EventTarget
    public void onRenderEvent(RenderEvent event) {
        float width = widthAnim.getValue(), height = heightAnim.getValue();
        float x = this.x.getValue().floatValue(), y = this.y.getValue().floatValue();
        float finalY = y;
         Shader.drawRoundRectWithGlowing(x, finalY, x + width, finalY + height, new Color(0.117647059f,0.117647059f,0.117647059f));
        RenderUtils.drawRoundedRect(x, y, x + width, y + height, 4, new Color(0.117647059f,0.117647059f,0.117647059f).getRGB());
        FontUtil.sfuiFontBold17.drawCenteredString("Staff Statistics", x + width / 2, y + 6f, new Color(255, 255, 255), HUD.gradient.isEnable());
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedRect(x, y, x + width, y + height, 4, new Color(0.117647059f,0.117647059f,0.117647059f).getRGB());
        StencilUtil.readStencilBuffer(1);
        y += 15;
        for(String p : staffs){
            FontUtil.sfuiFont16.drawString(p, x + 5, y + 3, new Color(255, 255, 255, 255));
            y += 12;
        }
        StencilUtil.uninitStencilBuffer();
        
    }
}
