/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.gui.preview.FPSPreview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class FPSModule extends Module {
	private static String[] description = {"Shows FPS"};
	public static int x, y;
	public static boolean stayRight = false;
	public static boolean stayBottom = false;
	public static boolean doBackground = true;
	public static float scale;
	
	public FPSModule() {
		super("FPS", description, "TOGGLED:FALSE>LOCATION:80/2>SIDE:RIGHT/TOP>SCALE:1>DOBACK:TRUE");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	public FPSModule(int index) {
		super("FPS " + index, description, "TOGGLED:FALSE>LOCATION:80/2>SIDE:RIGHT/TOP>SCALE:1>DOBACK:TRUE");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	@Override
	public void ready() {
		super.ready();
		this.preview = new FPSPreview(this);
		this.icon = new ResourceLocation("textures/solar/modules/FPS.png");
	}
	
	@Override
	public void onRenderGameOverlay(float ticks, GuiScreen screen) {
		if (isToggled()) {
			ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
			float mscale = (float)Math.pow(scale,-1);
			GL11.glScalef(scale, scale, scale);
			if (stayRight) {
				if (stayBottom) {
					if (doBackground) screen.drawRect(var4 - x, var5 - y, var4 - x + 76, var5 - y + 16, 0x80000000);
					mc.fontRenderer.drawCenteredString(mc.debug.split(",")[0].toUpperCase(), var4 - x + 38, var5 - y + 4, 0xFFFFFF);
				} else {
					if (doBackground) screen.drawRect(var4 - x, y, var4 - x + 76, y + 16, 0x80000000);
					mc.fontRenderer.drawCenteredString(mc.debug.split(",")[0].toUpperCase(), var4 - x + 38, y + 4, 0xFFFFFF);
				}
			} else {
				if (stayBottom) {
					if (doBackground) screen.drawRect(x, var5 - y,  x + 76, var5 - y + 16, 0x80000000);
					mc.fontRenderer.drawCenteredString(mc.debug.split(",")[0].toUpperCase(), x + 38, var5 - y + 4, 0xFFFFFF);
				} else {
					if (doBackground) screen.drawRect(x, y,  x + 76, y + 16, 0x80000000);
					mc.fontRenderer.drawCenteredString(mc.debug.split(",")[0].toUpperCase(), x + 38, y + 4, 0xFFFFFF);
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
			} else if (property.startsWith("DOBACK")) {
				String value = property.replace("DOBACK:", "");
				if (value.equalsIgnoreCase("TRUE"))
					doBackground = true;
				else
					doBackground = false;
			}
			
		}
	}
	
}
