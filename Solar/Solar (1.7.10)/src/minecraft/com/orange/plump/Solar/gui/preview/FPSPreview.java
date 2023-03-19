/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import com.orange.plump.Solar.modules.FPSModule;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.gui.GuiScreen;

public class FPSPreview extends Preview {

	public FPSPreview(Module module) {
		super(module);
	}
	
	@Override
	public void renderPreview(GuiScreen screen) {
		super.renderPreview(screen);
		float mscale = (float)Math.pow(scale,-1);
		GL11.glScalef(scale, scale, scale);
		if (stayRight) {
			if (stayBottom) {
				if (FPSModule.doBackground) screen.drawRect(mc.currentScreen.width - x, mc.currentScreen.height - y, mc.currentScreen.width - x + 76, mc.currentScreen.height - y + 16, 0x80000000);
				screen.drawCenteredString(mc.fontRenderer, "999 FPS", mc.currentScreen.width - x + 38, mc.currentScreen.height - y + 4, 0xFFFFFF);
			} else {
				if (FPSModule.doBackground) screen.drawRect(mc.currentScreen.width - x, y, mc.currentScreen.width - x + 76, y + 16, 0x80000000);
				screen.drawCenteredString(mc.fontRenderer, "999 FPS", mc.currentScreen.width - x + 38, y + 4, 0xFFFFFF);
			}
		} else {
			if (stayBottom) {
				if (FPSModule.doBackground) screen.drawRect(x, mc.currentScreen.height - y,  x + 76, mc.currentScreen.height - y + 16, 0x80000000);
				screen.drawCenteredString(mc.fontRenderer, "999 FPS", x + 38, mc.currentScreen.height - y + 4, 0xFFFFFF);
			} else {
				if (FPSModule.doBackground) screen.drawRect(x, y,  x + 76, y + 16, 0x80000000);
				screen.drawCenteredString(mc.fontRenderer, "999 FPS", x + 38, y + 4, 0xFFFFFF);
			}
		}
		GL11.glScalef(mscale, mscale, mscale);
	}
	
	@Override
	public int getWidth() {
		return Math.round(76 * scale);
	}
	
	@Override
	public int getHeight() {
		return Math.round(16 * scale);
	}
}
