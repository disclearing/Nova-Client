/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.gui.button.GuiButton;
import com.orange.plump.Solar.gui.button.GuiCogButton;
import com.orange.plump.Solar.gui.preview.Preview;
import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class HomeGui extends GuiScreen {

	private List<GuiButton> buttons;
	private List<GuiCogButton> cogs;
	private List<Preview> previews;
	private static final ResourceLocation cog = new ResourceLocation("textures/solar/cog.png");
	private boolean doStationary = false;
	public static Preview selected = null;
	private int xOffset = 0, yOffset = 0;
	private static final String __OBFID = "CL_00700000";
	
	public HomeGui() {
		buttons = new ArrayList<GuiButton>();
		previews = new ArrayList<Preview>();
		cogs = new ArrayList<GuiCogButton>();
	}
	
	public HomeGui(boolean doStationary) {
		buttons = new ArrayList<GuiButton>();
		previews = new ArrayList<Preview>();
		cogs = new ArrayList<GuiCogButton>();
		this.doStationary = doStationary;
	}
	@Override
	public void onGuiClosed() {
		if (selected != null) selected.selected = false;
		selected = null;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == mc.gameSettings.keyBindMainGui.getKeyCode())
			if (GuiManager.windowIsOpen) {
				GuiManager.closeGui();
				for (Preview preview : previews) {
					preview.saveSettings();
				}
			}
	}
	
	public void initGui() {
		mc.cFontRenderer.setDefaultFont();
		for (Module module : Solar.getModules()) {
        	if (module.isToggled()) {
        		Preview preview = module.preview;
        		if (preview != null) previews.add(preview);
        	}
        }
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (selected != null) selected.selected = false;
		selected = null;
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for(GuiButton button : buttons)
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.equals("mods")) {
					GuiManager.show(new ModuleGui());
					for (Preview preview : previews) {
						preview.saveSettings();
					}
				}
			}
		for(GuiCogButton button : cogs)
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.equals("settings")) {
					for (Preview preview : previews) {
						preview.saveSettings();
					}
				}
			}
	}
	
	private void doInputs() {
		int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int var3 = Mouse.getEventButton();
        if (Mouse.getEventButtonState())
        {
            //this.lastMouseEvent = Minecraft.getSystemTime();
            for (Preview preview : previews) {
    			if (preview.isInArea(var1, var2)) {
    				selected = preview;
    				preview.selected = true;
    				xOffset = var1 - preview.getX();
    				yOffset = var2 - preview.getY();
    			}
    		}
        }
        else if (eventButton != -1 && lastMouseEvent > 0L)
        {
            long var4 = System.currentTimeMillis() - this.lastMouseEvent;
            this.lastMouseEvent = System.currentTimeMillis();
            if (var4 > 100) for (Preview preview : previews) {
    			if (preview.isInArea(var1, var2)) {
    				selected = preview;
    				preview.selected = true;
    				xOffset = var1 - preview.getX();
    				yOffset = var2 - preview.getY();
    			}
    		}
            if (selected != null) selected.setLocation(var1 - xOffset, var2 - yOffset);
        }
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		if (selected != null) {

		}
		doInputs();
		if (doStationary) 
			this.drawBlurredBackgroundStationary();
		else 
			this.drawBlurredBackground();
		for (Preview preview : previews) {
        	if (preview != selected) preview.renderPreview(this);
        }
		if (selected != null) selected.renderPreview(this);
		mc.fontRenderer.setFancy(true);
		Tessellator var4 = Tessellator.instance;
        float scale = 0.25F;
        float mscale = (float)Math.pow(scale,-1);
        short var5 = 256;
        int var6 = this.width / 2;
        int var7 = this.height / 2 - 50;
        var6 = (int) (var6 / scale) - var5 / 2;
        var7 = (int) (var7 / scale) - var5 / 2;
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/solar/Solar.png"));
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        this.drawTexturedModalRect(var6 + 2, var7 + 0, 0, 0, var5, var5);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glScalef(mscale, mscale, mscale);
        GL11.glTranslatef((float) (this.width/2), (float) (this.height/2), 0f);
        
        float size = 1.001f;
        float msize = (float)Math.pow(scale,-1);
        GL11.glScalef(size, size, size);
        if (getButton("mods") == null) buttons.add(new GuiButton("mods", -28 - 12, -10, 28 - 12, 10, new Color(200, 200, 200, 40), this));
        if (getCog("settings") == null) cogs.add(new GuiCogButton("settings", 18, -10, 38, 10, new Color(200, 200, 200, 40), this, true));
        for (GuiButton button : buttons)
			button.draw(mouseX, mouseY);
        for (GuiCogButton button : cogs)
			button.draw(mouseX, mouseY);
        drawCenteredString(this.fontRendererObj, "MODS", -12, -3, 0xFFFFFF);
        scale = 0.05125F;
        mscale = (float)Math.pow(scale,-1);
        mc.fontRenderer.setFancy(false);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	private GuiButton getButton(String name) {
		for (GuiButton butt : buttons)
			if (butt.name.equals(name))
				return butt;
		return null;
	}
	
	private GuiCogButton getCog(String name) {
		for (GuiCogButton butt : cogs)
			if (butt.name.equals(name))
				return butt;
		return null;
	}
}
