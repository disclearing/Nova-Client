/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.gui.GuiScreen;

public class ToggleSneakPreview extends Preview {
	
	public ToggleSneakPreview(Module module) {
		super(module);
	}
	
	@Override
	public void renderPreview(GuiScreen screen) {
		super.renderPreview(screen);
		float mscale = (float)Math.pow(scale,-1);
		GL11.glScalef(scale, scale, scale);
		if (stayRight) {
			if (stayBottom) {
				screen.drawString(mc.fontRenderer, "[Sneaking (Toggled)]", mc.currentScreen.width - x, mc.currentScreen.height - y, 0xFFFFFF);
			} else {
				screen.drawString(mc.fontRenderer, "[Sneaking (Toggled)]", mc.currentScreen.width - x, y, 0xFFFFFF);
			}
		} else {
			if (stayBottom) {
				screen.drawString(mc.fontRenderer, "[Sneaking (Toggled)]", x, mc.currentScreen.height - y, 0xFFFFFF);
			} else {
				screen.drawString(mc.fontRenderer, "[Sneaking (Toggled)]", x, y, 0xFFFFFF);
			}
		}
		GL11.glScalef(mscale, mscale, mscale);
	}
	
	@Override
	public int getWidth() {
		return Math.round((mc.fontRenderer.getStringWidth("[Sneaking (Toggled)]")) * scale) - 1;
	}
	
	@Override
	public int getHeight() {
		return Math.round((mc.fontRenderer.FONT_HEIGHT ) * scale) - 1;
	}
}
