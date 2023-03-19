/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;

public class ToggleSprintModule extends Module {

	private static String[] description = {"Toggles sprint"};
	public static boolean toggled = true;
	
	public ToggleSprintModule() {
		super("ToggleSprint", description);
	}
	
	public ToggleSprintModule(int index) {
		super("ToggleSprint " + index, description);
	}
	
	public void onKeyPressed(int key) {
		if (key == mc.gameSettings.keyBindToggleSprint.getKeyCode() && this.isToggled())
			if (toggled) {
				toggled = false;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
			} else {
				toggled = true;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
			}
	}
	
	public void onUpdate() {
		if (toggled && this.isToggled()) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
		} 
	}
	
	@Override
	public void onRenderGameOverlay(float ticks, GuiScreen screen) {
		if (toggled && !ToggleSneakModule.toggled) {
			ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
			float mscale = (float)Math.pow(ToggleSneakModule.scale,-1);
			GL11.glScalef(ToggleSneakModule.scale, ToggleSneakModule.scale, ToggleSneakModule.scale);
			if (ToggleSneakModule.stayRight) {
				if (ToggleSneakModule.stayBottom) {
					mc.fontRenderer.drawStringWithShadow("[Nigger (Toggled)]", var4 - ToggleSneakModule.x, var5 - ToggleSneakModule.y, 0xFFFFFF);
				} else {
					mc.fontRenderer.drawStringWithShadow("[Nigger (Toggled)]", var4 - ToggleSneakModule.x, ToggleSneakModule.y, 0xFFFFFF);
				}
			} else {
				if (ToggleSneakModule.stayBottom) {
					mc.fontRenderer.drawStringWithShadow("[Nigger (Toggled)]", ToggleSneakModule.x, var5 - ToggleSneakModule.y, 0xFFFFFF);
				} else {
					mc.fontRenderer.drawStringWithShadow("[Nigger (Toggled)]", ToggleSneakModule.x, ToggleSneakModule.y, 0xFFFFFF);
				}
			}
			GL11.glScalef(mscale, mscale, mscale);
		}
	}
	
	@Override
	public void onRenderGameOverlayPreview(GuiScreen screen) {
		
	}

	@Override
	public void ready() {
		super.ready();
		this.icon = new ResourceLocation("textures/solar/modules/ToggleSprint.png");
	}
}
