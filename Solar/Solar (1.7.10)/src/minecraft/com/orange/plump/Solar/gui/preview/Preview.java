/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class Preview {
	protected Minecraft mc = Minecraft.getMinecraft();
	private Module module = null;
	protected int x = 0, y = 0;
	protected float scale = 1f;
	protected boolean stayRight = false;
	protected boolean stayBottom = false;
	public boolean selected = false;
	
	public Preview(Module module) {
		this.module = module;
		setup();
	}
	
	private void setup() {
		for (String property : this.module.properties.split(">")) {
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
	public int getX() {
		if (stayRight) {
			return mc.currentScreen.width - this.x;
		} else
			return x;
	}
	
	public int getY() {
		if (stayBottom) {
			return mc.currentScreen.height - this.y;
		} else
			return y;
	}
	
	
	public void setLocation(int x, int y) {
		if (x > mc.currentScreen.width / 2) {
			stayRight = true;
			this.x = mc.currentScreen.width - x;
		} else {
			stayRight = false;
			this.x = x;
		}
		if (y > mc.currentScreen.height / 2) {
			stayBottom = true;
			this.y = mc.currentScreen.height - y;
		} else {
			stayBottom = false;
			this.y = y;
		}
	}
	
	public boolean isInArea(int x, int y) {
		if (stayRight) {
			if (stayBottom) {
				if (x >= mc.currentScreen.width - this.x && y >= mc.currentScreen.height - this.y && x <= mc.currentScreen.width - this.x + getWidth() && y <=mc.currentScreen.height -  this.y + getHeight())
					return true;
				else
					return false;
			} else {
				if (x >= mc.currentScreen.width - this.x && y >= this.y && x <= mc.currentScreen.width - this.x + getWidth() && y <= this.y + getHeight())
					return true;
				else
					return false;
			}
		} else {
			if (stayBottom) {
				if (x >= this.x && y >= mc.currentScreen.height - this.y && x <= this.x + getWidth() && y <= mc.currentScreen.height - this.y + getHeight())
					return true;
				else
					return false;
			} else {
				if (x >= this.x && y >= this.y && x <= this.x + getWidth() && y <= this.y + getHeight())
					return true;
				else
					return false;
			}
		}
	}
	
	public void renderPreview(GuiScreen screen) {
		float mscale = (float)Math.pow(scale,-1);
		GL11.glScalef(scale, scale, scale);
		if (stayRight) {
			if (stayBottom) {
				if (selected) mc.cFontRenderer.drawString(module.getName(), mc.currentScreen.width - x, mc.currentScreen.height - y - 9, 0xFFFFFF);
				screen.drawRect(mc.currentScreen.width - x-2, mc.currentScreen.height - y-2, mc.currentScreen.width - x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2, 0x30FFFFFF);
				
				screen.drawRect(mc.currentScreen.width - x-2, mc.currentScreen.height - y-2 + 1, mc.currentScreen.width - x + getWidth() + 2, mc.currentScreen.height - y-2, 0x20FFFFFF);
				screen.drawRect(mc.currentScreen.width - x-2, mc.currentScreen.height - y + getHeight() + 2 - 1, mc.currentScreen.width - x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2, 0x20FFFFFF);
		            
				screen.drawRect(mc.currentScreen.width - x-2 + 1, mc.currentScreen.height - y-2 + 1, mc.currentScreen.width - x-2, mc.currentScreen.height - y + getHeight() + 2 - 1, 0x20FFFFFF);
				screen.drawRect(mc.currentScreen.width - x + getWidth() + 2 - 1, mc.currentScreen.height - y-2 + 1, mc.currentScreen.width - x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2 - 1, 0x20FFFFFF);
			} else {
				if (selected) mc.cFontRenderer.drawString(module.getName(), mc.currentScreen.width - x, y - 9, 0xFFFFFF);
				screen.drawRect(mc.currentScreen.width - x-2, y-2, mc.currentScreen.width - x + getWidth() + 2, y + getHeight() + 2, 0x30FFFFFF);
				
				screen.drawRect(mc.currentScreen.width - x-2, y-2 + 1, mc.currentScreen.width - x + getWidth() + 2, y-2, 0x20FFFFFF);
				screen.drawRect(mc.currentScreen.width - x-2, y + getHeight() + 2 - 1, mc.currentScreen.width - x + getWidth() + 2, y + getHeight() + 2, 0x20FFFFFF);
	            
				screen.drawRect(mc.currentScreen.width - x-2 + 1, y-2 + 1, mc.currentScreen.width - x-2, y + getHeight() + 2 - 1, 0x20FFFFFF);
				screen.drawRect(mc.currentScreen.width - x + getWidth() + 2 - 1, y-2 + 1, mc.currentScreen.width - x + getWidth() + 2, y + getHeight() + 2 - 1, 0x20FFFFFF);
			}
		} else {
			if (stayBottom) {
				if (selected) mc.cFontRenderer.drawString(module.getName(), x, mc.currentScreen.height - y - 9, 0xFFFFFF);
				screen.drawRect(x-2, mc.currentScreen.height - y-2, x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2, 0x30FFFFFF);
				
				screen.drawRect(x-2, mc.currentScreen.height - y-2 + 1, x + getWidth() + 2, mc.currentScreen.height - y-2, 0x20FFFFFF);
				screen.drawRect(x-2, mc.currentScreen.height - y + getHeight() + 2 - 1, x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2, 0x20FFFFFF);
	            
				screen.drawRect(x-2 + 1, mc.currentScreen.height - y-2 + 1, x-2, mc.currentScreen.height - y + getHeight() + 2 - 1, 0x20FFFFFF);
				screen.drawRect(x + getWidth() + 2 - 1, mc.currentScreen.height - y-2 + 1, x + getWidth() + 2, mc.currentScreen.height - y + getHeight() + 2 - 1, 0x20FFFFFF);
			} else {
				if (selected) mc.cFontRenderer.drawString(module.getName(), x, y - 9, 0xFFFFFF);
				screen.drawRect(x-2, y-2, x + getWidth() + 2, y + getHeight() + 2, 0x30FFFFFF);
				
				screen.drawRect(x-2, y-2 + 1, x + getWidth() + 2, y-2, 0x20FFFFFF);
				screen.drawRect(x-2, y + getHeight() + 2 - 1, x + getWidth() + 2, y + getHeight() + 2, 0x20FFFFFF);
	            
				screen.drawRect(x-2 + 1, y-2 + 1, x-2, y + getHeight() + 2 - 1, 0x20FFFFFF);
				screen.drawRect(x + getWidth() + 2 - 1, y-2 + 1, x + getWidth() + 2, y + getHeight() + 2 - 1, 0x20FFFFFF);
			}
		}
		GL11.glScalef(mscale, mscale, mscale);
	}
	
	public void saveSettings() {
		Solar.setModuleProperties(module, "LOCATION", x + "/" + y);
		Solar.setModuleProperties(module, "SIDE", (stayRight ? "RIGHT" : "LEFT") + "/" + (stayBottom ? "BOTTOM" : "TOP"));
		Solar.setModuleProperties(module, "SCALE", String.valueOf(scale));
	}
	
	public int getWidth() {
		return 0;
	}
	
	public int getHeight() {
		return 0;
	}
	
}
