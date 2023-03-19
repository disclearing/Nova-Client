/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.button;

import java.awt.Color;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiButton {
	
	public int topX, topY, bottomX, bottomY, buffer, color, textColor;
	public Color theColor = null;
	public GuiScreen guiScreen;
	public String name, data;
	private boolean doBuffer = true;
	private boolean doColor = false;
	private int hoverState = 0;
	private int scroll = 0;
	private static final String __OBFID = "CL_00500000";
	
	public GuiButton(String name, int topX, int topY, int bottomX, int bottomY, int color, GuiScreen guiScreen) {
		this.name = name;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = color;
		this.guiScreen = guiScreen;
	}
	
	public GuiButton(String name, int topX, int topY, int bottomX, int bottomY, Color color, GuiScreen guiScreen) {
		this.name = name;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = 0x00000000;
		this.theColor = color;
		this.doColor = true;
		this.guiScreen = guiScreen;
	}
	
	public GuiButton(String name, String data, int topX, int topY, int bottomX, int bottomY, int buffer, int color, int textColor, GuiScreen guiScreen) {
		this.name = name;
		this.data = data;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.buffer = buffer;
		this.color = color;
		this.textColor = textColor;
		this.guiScreen = guiScreen;
	}
	public GuiButton(String name, String data, int topX, int topY, int bottomX, int bottomY, int color, int textColor, GuiScreen guiScreen) {
		this.name = name;
		this.data = data;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = color;
		this.textColor = textColor;
		this.guiScreen = guiScreen;
		this.doBuffer = false;
	}
	
	public void draw(int mouseX, int mouseY) {
		GL11.glTranslatef(0, -scroll, 0);
		boolean isHovered = isOnButton(mouseX, mouseY);
		if (isHovered) {
			if (hoverState < 100)  hoverState+=10;
		} else {
			if (hoverState > 0)  hoverState-=10;
		}
		if (doColor) {
			guiScreen.drawRoundedRect(topX, topY, bottomX, bottomY, 5, new Color(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), theColor.getAlpha() + hoverState).hashCode());
		} else {
			guiScreen.drawRoundedRect(topX, topY, bottomX, bottomY, 5, color);
		}
		if (data != null)
			if (doBuffer) guiScreen.mc.cFontRenderer.drawString(data, topX + buffer, topY + buffer, textColor);
			else guiScreen.mc.cFontRenderer.drawString(data, topX, topY, textColor);
		GL11.glTranslatef(0, scroll, 0);
	}
	
	public boolean isOnButton(int x, int y) {
		x = x - (guiScreen.width/2);
		y = y - (guiScreen.height/2);
		y += scroll;
		if (x > topX && y > topY && x < bottomX && y < bottomY)
			return true;
		else return false;
	}

	public boolean isOnButton(int x, int y, boolean isNot) {
		y += scroll;
		if (x > topX && y > topY && x < bottomX && y < bottomY)
			return true;
		else return false;
	}

	public void setScroll(int scroll) {
		this.scroll = scroll;
	}

}
