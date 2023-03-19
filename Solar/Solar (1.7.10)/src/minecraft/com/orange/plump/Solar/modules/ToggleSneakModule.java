/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.gui.preview.ToggleSneakPreview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;

public class ToggleSneakModule extends Module {
	
	private static String[] description = {"Toggles sneak"};
	public static boolean toggled = false;
	public static int x, y;
	public static boolean stayRight = false;
	public static boolean stayBottom = false;
	public static float scale;
	
	public ToggleSneakModule() {
		super("ToggleSneak", description, "TOGGLED:FALSE>LOCATION:2/2>SIDE:LEFT/TOP>SCALE:1");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	public ToggleSneakModule(int index) {
		super("ToggleSneak " + index, description, "TOGGLED:FALSE>LOCATION:2/2>SIDE:LEFT/TOP>SCALE:1");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	@Override
	public void ready() {
		super.ready();
		this.preview = new ToggleSneakPreview(this);
		this.icon = new ResourceLocation("textures/solar/modules/ToggleSneak.png");
	}

	@Override
	public void onKeyPressed(int key) {
		if (key == mc.gameSettings.keyBindToggleSneak.getKeyCode() && this.isToggled())
			if (toggled) {
				toggled = false;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
			} else {
				toggled = true;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
			}
	}
	
	@Override
	public void onUpdate() {
		if (toggled && this.isToggled()) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
		} 
	}
	
	@Override
	public void onRenderGameOverlay(float ticks, GuiScreen screen) {
		if (toggled) {
			ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
			float mscale = (float)Math.pow(scale,-1);
			GL11.glScalef(scale, scale, scale);
			if (stayRight) {
				if (stayBottom) {
					mc.fontRenderer.drawStringWithShadow("[Sneaking (Toggled)]", var4 - x, var5 - y, 0xFFFFFF);
				} else {
					mc.fontRenderer.drawStringWithShadow("[Sneaking (Toggled)]", var4 - x, y, 0xFFFFFF);					
				}
			} else {
				if (stayBottom) {
					mc.fontRenderer.drawStringWithShadow("[Sneaking (Toggled)]", x, var5 - y, 0xFFFFFF);
				} else {
					mc.fontRenderer.drawStringWithShadow("[Sneaking (Toggled)]", x, y, 0xFFFFFF);
				}
				
			}
			GL11.glScalef(mscale, mscale, mscale);
		}
	}
	
	@Override
	public void onPropertiesUpdate() {
		super.onPropertiesUpdate();
		for (String property : this.properties.split(">")) {
			if (property.startsWith("LOCATION")) {
				String location = property.replace("LOCATION:", "");
				x = Integer.valueOf(location.split("/")[0]);
				y = Integer.valueOf(location.split("/")[1]);
			} else if (property.startsWith("SIDE")) {
				String side = property.replace("SIDE:", ""); 
				String rl = side.split("/")[0];
				String tb = side.split("/")[1];
				if (rl.equals("RIGHT")) stayRight = true;
				else stayRight = false;
				if (tb.equals("BOTTOM")) stayBottom = true;
				else stayBottom = false;
			} else if (property.startsWith("SCALE")) {
				String value = property.replace("SCALE:", ""); 
				scale = Float.parseFloat(value);
			}
			
		}
	}
}
