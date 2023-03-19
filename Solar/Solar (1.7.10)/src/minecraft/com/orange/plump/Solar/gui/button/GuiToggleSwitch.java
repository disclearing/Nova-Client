/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.button;

import java.awt.Color;

import net.minecraft.client.gui.GuiScreen;

public class GuiToggleSwitch {
	
	public int topX, topY, bottomX, bottomY, buffer, color, textColor;
	public Color theColor = null;
	public GuiScreen guiScreen;
	public String name, data;
	private boolean doBuffer = true;
	private boolean doColor = false;
	private boolean isToggled = false;
	private boolean animate = false;
	private boolean lastIsToggled = false;
	private int hoverState = 0;
	private int scroll = 0;
	private static final String __OBFID = "CL_00500000";
	
	public GuiToggleSwitch(String name, int topX, int topY, int bottomX, int bottomY, GuiScreen guiScreen, boolean isToggled) {
		this.name = name;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = 0x00000000;
		if (isToggled) this.theColor = new Color(0, 255, 0, 255);
		else this.theColor = new Color(255, 0, 0, 255);
		this.doColor = true;
		this.guiScreen = guiScreen;
		this.isToggled = isToggled;
		this.lastIsToggled = isToggled;
	}
	
	
	public void draw(int mouseX, int mouseY) {
		
		if (hoverState < 0) hoverState = 0;
		guiScreen.drawRoundedRect(topX, topY, bottomX, bottomY, 9, new Color(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), theColor.getAlpha()).hashCode());

		if (!animate && this.lastIsToggled == this.isToggled) {
			if (isToggled) {
				double x = (bottomX - 4.25);
				double y = (topY + 4.25);
				guiScreen.setColor(0xFFFFFFFF);
				guiScreen.drawCircle(x, y, 3.5);
			} else {
				double x = (topX + 4.25);
				double y = (topY + 4.25);
				guiScreen.setColor(0xFFFFFFFF);
				guiScreen.drawCircle(x, y, 3.5);
			}
		} else {
			hoverState++; 
			if (isToggled) {
				double x = (topX + hoverState / 2) + (4.25);
				double y = (topY + 4.25);
				guiScreen.setColor(0xFFFFFFFF);
				guiScreen.drawCircle(x, y, 3.5);
			} else {
				double x = (bottomX - hoverState / 2) - (4.25);
				double y = (topY + 4.25);
				guiScreen.setColor(0xFFFFFFFF);
				guiScreen.drawCircle(x, y, 3.5);
			}
			if (hoverState == 18) {
				if (this.lastIsToggled != this.isToggled) lastIsToggled = isToggled;
				animate = false;
				hoverState = 0;
			}
		}
		
	}
	
	public boolean isOnButton(int x, int y) {
		x = x - (guiScreen.width/2);
		y = y - (guiScreen.height/2);
		y += scroll;
		if (x > topX && y > topY && x < bottomX && y < bottomY)
			return true;
		else return false;
	}
	
	public void setToggled(boolean isToggled) {
		if (this.lastIsToggled != this.isToggled) {
			this.lastIsToggled = this.isToggled;
			 animate = !animate;
		}
		this.isToggled = isToggled;
		if (isToggled) this.theColor = new Color(0, 255, 0, 100);
		else this.theColor = new Color(255, 0, 0, 100);
	}

	public boolean isToggled() {
		return isToggled;
	}

	public void setScroll(int scroll) {
		this.scroll = scroll;
	}

}
