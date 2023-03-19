/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.gui.preview.KeystrokesPreview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class KeystrokesModule extends Module {
	private static String[] description = {"Shows keystrokes"};
	public static int x, y;
	public static boolean stayRight = false;
	public static boolean stayBottom = false;
	public static boolean doSpacebar = true;
	public static boolean doMouseButtons = true;
	public static float scale;
	private static Integer[] fades = new Integer[] {0, 0, 0, 0, 0, 0, 0};
	
	public KeystrokesModule() {
		super("Keystrokes", description, "TOGGLED:FALSE>LOCATION:80/30>SIDE:RIGHT/TOP>SCALE:1>DOSPACE:TRUE>DOMOUSE:TRUE");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	public KeystrokesModule(int index) {
		super("Keystrokes " + index, description, "TOGGLED:FALSE>LOCATION:80/30>SIDE:RIGHT/TOP>SCALE:1>DOSPACE:TRUE>DOMOUSE:TRUE");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	@Override
	public void ready() {
		super.ready();
		this.preview = new KeystrokesPreview(this);
	}
	
	@Override
	public void onRender() {
		if (isToggled()) {
			int increment = 20;
			int max = 200;
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
				if (!(fades[0] + increment > max)) fades[0] = fades[0] + increment;
			} else 
				if (!(fades[0] - increment < 0)) fades[0] = fades[0] - increment;
			
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
				if (!(fades[1] + increment > max)) fades[1] = fades[1] + increment;
			} else 
				if (!(fades[1] - increment < 0)) fades[1] = fades[1] - increment;
			
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
				if (!(fades[2] + increment > max)) fades[2] = fades[2] + increment;
			} else 
				if (!(fades[2] - increment < 0)) fades[2] = fades[2] - increment;
			
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
				if (!(fades[3] + increment > max)) fades[3] = fades[3] + increment;
			} else 
				if (!(fades[3] - increment < 0)) fades[3] = fades[3] - increment;
			
			if (mc.gameSettings.keyBindAttack.getIsKeyPressed()) {
				if (!(fades[4] + increment + 20 > max)) fades[4] = fades[4] + increment  + 20;
			} else 
				if (!(fades[4] - (increment + 20) < 0)) fades[4] = fades[4] - (increment  + 20);
			
			if (mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
				if (!(fades[5] + increment + 20 > max)) fades[5] = fades[5] + increment + 20;
			} else 
				if (!(fades[5] - (increment + 20) < 0)) fades[5] = fades[5] - (increment + 20);

			if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
				if (!(fades[6] + increment > max)) fades[6] = fades[6] + increment;
			} else 
				if (!(fades[6] - increment < 0)) fades[6] = fades[6] - increment;
		}
	}
	
	@Override
	public void onRenderGameOverlay(float ticks, GuiScreen screen) {
		if (isToggled()) {
			ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), mc.displayWidth, mc.displayHeight);
            int width = var3.getScaledWidth();
            int height = var3.getScaledHeight();
			float mscale = (float)Math.pow(scale,-1);
			GL11.glScalef(scale, scale, scale);
			if (stayRight) {
				if (stayBottom) {
					render(width - x, height - y, screen);
				} else {
					render(width - x, y, screen);
				}
			} else {
				if (stayBottom) {
					render(x, height - y, screen);
				} else {
					render(x, y, screen);
				}
			}
			GL11.glScalef(mscale, mscale, mscale);
		}
	}
	
	private void render(int x, int y, GuiScreen screen) {
		//W 24
		screen.drawRect(x + 26, y, x + 26 + 24, y + 24, new Color(0 + fades[0], 0 + fades[0], 0 + fades[0], 125).hashCode());
		mc.fontRenderer.drawCenteredString("W", x + 26 + 12, y + 10, 0xFFFFFF);
		//A 24
		screen.drawRect(x, y + 26, x + 24, y + 26 + 24, new Color(0 + fades[1], 0 + fades[1], 0 + fades[1], 125).hashCode());
		mc.fontRenderer.drawCenteredString("A", x + 12, y + 26 + 10, 0xFFFFFF);
		//S 24
		screen.drawRect(x + 26, y + 26, x + 26 + 24, y + 26 + 24, new Color(0 + fades[2], 0 + fades[2], 0 + fades[2], 125).hashCode());
		mc.fontRenderer.drawCenteredString("S", x + 26 + 12, y + 26 + 10, 0xFFFFFF);
		//D 24
		screen.drawRect(x + 52, y + 26, x + 52 + 24, y + 26 + 24, new Color(0 + fades[3], 0 + fades[3], 0 + fades[3], 125).hashCode());
		mc.fontRenderer.drawCenteredString("D", x + 52 + 12, y + 26 + 10, 0xFFFFFF);
		if (doMouseButtons) {
			//LMB 37
			screen.drawRect(x, y + 52, x + 37, y + 52 + 20, new Color(0 + fades[4], 0 + fades[4], 0 + fades[4], 125).hashCode());
			mc.fontRenderer.drawCenteredString("LMB", x + 19, y + 52 + 7, 0xFFFFFF);
			//RMB 37
			screen.drawRect(x + 39, y + 52, x + 39 + 37, y + 52 + 20, new Color(0 + fades[5], 0 + fades[5], 0 + fades[5], 125).hashCode());
			mc.fontRenderer.drawCenteredString("RMB", x + 39 + 19, y + 52 + 7, 0xFFFFFF);
			y+=22;
		}

		if (doSpacebar) {
			//BAR 76, 14
			screen.drawRect(x, y + 52, x + 76, y + 52 + 12, new Color(0 + fades[6], 0 + fades[6], 0 + fades[6], 125).hashCode());
			screen.drawRect(x + 25, y + 52 + 7, x + 25 + 26, y + 52 + 6, 0xFFFFFFFF);
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
			} else if (property.startsWith("DOSPACE")) {
				String value = property.replace("DOSPACE:", "");
				if (value.equalsIgnoreCase("TRUE"))
					doSpacebar = true;
				else
					doSpacebar = false;
			} else if (property.startsWith("DOMOUSE")) {
				String value = property.replace("DOMOUSE:", "");
				if (value.equalsIgnoreCase("TRUE"))
					doMouseButtons = true;
				else
					doMouseButtons = false;
			}
			
		}
	}
	
}
