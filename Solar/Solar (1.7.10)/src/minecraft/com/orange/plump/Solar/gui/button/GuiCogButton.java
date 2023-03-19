/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.button;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiCogButton {
	
	public int topX, topY, bottomX, bottomY, buffer, color, textColor;
	public GuiScreen guiScreen;
	public Color theColor = null;
	public String name;
	private boolean doLarge = false;
	private boolean doColor = false;
	private static final ResourceLocation cog = new ResourceLocation("textures/solar/cog.png");
	private int hoverState = 0;
	private int scroll = 0;
	private static final String __OBFID = "CL_00500000";
	
	public GuiCogButton(String name, int topX, int topY, int bottomX, int bottomY, int color, GuiScreen guiScreen) {
		this.name = name;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = color;
		this.guiScreen = guiScreen;
	}
	
	public GuiCogButton(String name, int topX, int topY, int bottomX, int bottomY, Color color, GuiScreen guiScreen, boolean doLarge) {
		this.name = name;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
		this.color = 0x00000000;
		this.theColor = color;
		this.doColor = true;
		this.guiScreen = guiScreen;
		this.doLarge = doLarge;
	}

	
	public void draw(int mouseX, int mouseY) {
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
		float scale = 0.04125F;
        
        
        int cogX = topX + 11;
        int cogY = bottomY - 3;
        if (doLarge) {
        	cogX+=4;
        	cogY-=2;
        }
        float mscale = (float)Math.pow(scale,-1);
        cogX = (int) (cogX / scale) - 256 / 2 - 3;
        cogY = (int) (cogY / scale) - 256 / 2;
        guiScreen.mc.getTextureManager().bindTexture(cog);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glTranslatef(cogX, cogY, 0);
	        GL11.glRotatef(hoverState / 4, 0, 0, 1);
	        guiScreen.drawTexturedModalRect((int) (-5 * mscale),(int) (-5 * mscale), 0, 0, 256, 256);
	        GL11.glRotatef(-hoverState / 4, 0, 0, 1);
	        GL11.glTranslatef(-cogX, -cogY, 0);
	        GL11.glScalef(mscale, mscale, mscale);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	}

	public void draw(int mouseX, int mouseY, boolean isNot) {
		boolean isHovered = isOnButton(mouseX, mouseY, isNot);
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
		float scale = 0.04125F;


		int cogX = topX + 11;
		int cogY = bottomY - 3;
		if (doLarge) {
			cogX+=4;
			cogY-=2;
		}
		float mscale = (float)Math.pow(scale,-1);
		cogX = (int) (cogX / scale) - 256 / 2 - 3;
		cogY = (int) (cogY / scale) - 256 / 2;
		guiScreen.mc.getTextureManager().bindTexture(cog);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(cogX, cogY, 0);
		GL11.glRotatef(hoverState / 4, 0, 0, 1);
		guiScreen.drawTexturedModalRect((int) (-5 * mscale),(int) (-5 * mscale), 0, 0, 256, 256);
		GL11.glRotatef(-hoverState / 4, 0, 0, 1);
		GL11.glTranslatef(-cogX, -cogY, 0);
		GL11.glScalef(mscale, mscale, mscale);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
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
