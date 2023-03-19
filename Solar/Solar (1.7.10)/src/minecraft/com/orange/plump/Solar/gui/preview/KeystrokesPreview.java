/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import com.orange.plump.Solar.modules.KeystrokesModule;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.gui.GuiScreen;

public class KeystrokesPreview extends Preview {

	public KeystrokesPreview(Module module) {
		super(module);
	}
	
	@Override
	public void renderPreview(GuiScreen screen) {
		super.renderPreview(screen);
		float mscale = (float)Math.pow(scale,-1);
		GL11.glScalef(scale, scale, scale);
		if (stayRight) {
			if (stayBottom) {
				render(mc.currentScreen.width - x, mc.currentScreen.height - y, screen);
			} else {
				render(mc.currentScreen.width - x, y, screen);
			}
		} else {
			if (stayBottom) {
				render(x, mc.currentScreen.height - y, screen);
			} else {
				render(x, y, screen);
			}
		}
		GL11.glScalef(mscale, mscale, mscale);
	}
	
	private void render(int x, int y, GuiScreen screen) {
		//W 24
		screen.drawRect(x + 26, y, x + 26 + 24, y + 24, 0x80000000);
		mc.fontRenderer.drawCenteredString("W", x + 26 + 12, y + 10, 0xFFFFFF);
		//A 24
		screen.drawRect(x, y + 26, x + 24, y + 26 + 24, 0x80000000);
		mc.fontRenderer.drawCenteredString("A", x + 12, y + 26 + 10, 0xFFFFFF);
		//S 24
		screen.drawRect(x + 26, y + 26, x + 26 + 24, y + 26 + 24, 0x80000000);
		mc.fontRenderer.drawCenteredString("S", x + 26 + 12, y + 26 + 10, 0xFFFFFF);
		//D 24
		screen.drawRect(x + 52, y + 26, x + 52 + 24, y + 26 + 24, 0x80000000);
		mc.fontRenderer.drawCenteredString("D", x + 52 + 12, y + 26 + 10, 0xFFFFFF);
		if (KeystrokesModule.doMouseButtons) {
			//LMB 37
			screen.drawRect(x, y + 52, x + 37, y + 52 + 20, 0x80000000);
			mc.fontRenderer.drawCenteredString("LMB", x + 19, y + 52 + 7, 0xFFFFFF);
			//RMB 37
			screen.drawRect(x + 39, y + 52, x + 39 + 37, y + 52 + 20, 0x80000000);
			mc.fontRenderer.drawCenteredString("RMB", x + 39 + 19, y + 52 + 7, 0xFFFFFF);
			y+=22;
		}

		if (KeystrokesModule.doSpacebar) {
			//BAR 76, 14
			screen.drawRect(x, y + 52, x + 76, y + 52 + 16, 0x80000000);
			screen.drawRect(x + 25, y + 52 + 6, x + 25 + 26, y + 52 + 7, 0xFFFFFFFF);
		}
		
	}
	
	@Override
	public int getWidth() {
		return Math.round(76 * scale);
	}
	
	@Override
	public int getHeight() {
		int size = 50;
		if (KeystrokesModule.doMouseButtons) size+=22;
		if (KeystrokesModule.doSpacebar) size +=18;
		return Math.round(size * scale);
	}
}
