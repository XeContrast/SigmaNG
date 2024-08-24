/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2020-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.viamcp.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import info.sigmaclient.sigma.sigma5.jelloportal.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collections;
import java.util.LinkedList;

public class AsyncVersionSlider extends SliderPercentageOption {
    private int x, y, width, height;

    public AsyncVersionSlider(int x, int y , int widthIn, int heightIn)
    {
        super("", 0, ViaLoadingBase.getProtocols().size() - 1, 1, (sett) -> {
            return (double) (reverseProtocolIndex(ViaLoadingBase.getInstance().getTargetVersion().getIndex()));
        }, (sett, d) -> {
            int i = reverseProtocolIndex((int)(double)d);
            ViaLoadingBase.getInstance().reload(ViaLoadingBase.getProtocols().get(i));
        }, (a, b) -> {
            ProtocolVersion version = ViaLoadingBase.getProtocols().get(reverseProtocolIndex((int)b.get(a)));
            return new StringTextComponent(version.getName());
        });

        Collections.reverse(ViaLoadingBase.getProtocols());
        this.maxValue = ViaLoadingBase.getProtocols().size() - 1;
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
    }

    public Widget getButton() {
        return this.createWidget(Minecraft.getInstance().gameSettings, x, y, width);
    }

    public static LinkedList<ProtocolVersion> getReverseProtocols() {
        LinkedList<ProtocolVersion> protocols = new LinkedList<>(ViaLoadingBase.getProtocols());
        Collections.reverse(protocols);
        return protocols;
    }

    public static int reverseProtocolIndex(int index) {
        return getReverseProtocols().size() - 1 - index;
    }
}
