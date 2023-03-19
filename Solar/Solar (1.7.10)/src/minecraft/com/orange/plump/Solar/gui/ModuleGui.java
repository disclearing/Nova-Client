/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.gui.button.GuiButton;
import com.orange.plump.Solar.gui.button.GuiCogButton;
import com.orange.plump.Solar.gui.button.GuiToggleSwitch;
import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class ModuleGui extends GuiScreen {

	private List<GuiButton> buttons;
	private List<GuiCogButton> cogs;
	private List<GuiToggleSwitch> switches;
	private int page = 0;
	private static final ResourceLocation cog = new ResourceLocation("textures/solar/cog.png");
	private int frame = 0;
	private int scroll = 0;
	private static final String __OBFID = "CL_00800000";
	
	public ModuleGui() {
		buttons = new ArrayList<GuiButton>();
		cogs = new ArrayList<GuiCogButton>();
		switches = new ArrayList<GuiToggleSwitch>();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
        return false;
    }
	
	@Override
	public void onGuiClosed() {
		mc.cFontRenderer.setFont(new Font("Century Gothic", Font.BOLD, 16));
	}

	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == mc.gameSettings.keyBindMainGui.getKeyCode())
			if (GuiManager.windowIsOpen)
				GuiManager.closeGui();
		
	}
	
	public void initGui() {
		mc.cFontRenderer.setDefaultFont();
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for(GuiButton button : buttons)
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.startsWith("close")) {
					GuiManager.show(new HomeGui(true));
				}
			}
		for(GuiCogButton button : cogs)
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.startsWith("settings")) {
					for (Module mod : Solar.getModules())
						if (mod.getName().equals(button.name.replace("settings.", ""))) {
							GuiManager.show(mod.settingsGui);
							break;
						}
				}
				
			}
		
		for (GuiToggleSwitch button : switches) 
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.startsWith("toggle.")) {
					Module module = null;
					for (Module mod : Solar.getModules())
						if (mod.getName().equals(button.name.replace("toggle.", ""))) {
							module = mod;
						}
					module.toggle();
				}
			}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		 for (; !this.mc.gameSettings.touchscreen && Mouse.next(); this.mc.currentScreen.handleMouseInput())
         {
             int var7 = Mouse.getEventDWheel();

             if (var7 != 0)
             {
                 var7 = -var7;

                 this.scroll += (float)(var7 / 5);
                 if (this.scroll < 0) scroll = 0;
                 if (this.scroll > (int) Math.ceil(Solar.getModules().size() / 4) * 104) scroll = (int) (Math.ceil(Solar.getModules().size() / 4) * 104);
             }
         }
		ScaledResolution scaled = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		final int boxWidth = 200, boxHeight = 100;
		mc.fontRenderer.setFancy(true);
		int buffer = 4, header = 15, footer = 15;
		this.drawBlurredBackgroundStationary();
		buttons.clear();
		Tessellator var4 = Tessellator.instance;
        float scale = 0.25F;
        float mscale = (float)Math.pow(scale,-1);
        short var5 = 256;
        int var6 = this.width / 2;
        int var7 = this.height / 2 - boxHeight - (var5 / 6) - 15;
        var6 = (int) (var6 / scale) - var5 / 2;
        var7 = (int) (var7 / scale) - var5 / 2;
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/solar/Solar.png"));
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        this.drawTexturedModalRect(var6 + 8, var7 + 0, 0, 0, var5, var5);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glScalef(mscale, mscale, mscale);
		
		GL11.glTranslatef((float) (this.width/2), (float) (this.height/2) + frame, 0f);
		//Main Box
		//drawRect(-boxWidth, -boxHeight, boxWidth + buffer, boxHeight, new Color(150, 150, 150, 90).hashCode());
		drawRect(-boxWidth, -boxHeight + 1, boxWidth + buffer, -boxHeight, 0x44C8C8C8);
		drawRect(-boxWidth, boxHeight - 1, boxWidth + buffer, boxHeight, 0x44C8C8C8);
        
		drawRect(-boxWidth + 1, -boxHeight + 1, -boxWidth, boxHeight - 1, 0x44C8C8C8);
		drawRect(boxWidth + buffer - 1, -boxHeight + 1, boxWidth + buffer, boxHeight - 1, 0x44C8C8C8);
		int row = 1;
		int collum = 1;
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(0, (this.mc.displayHeight / 2) - ((boxHeight) * scaled.getScaleFactor() - buffer) - (frame * scaled.getScaleFactor()), this.mc.displayWidth, ((boxHeight) * scaled.getScaleFactor()) * 2 - buffer * 2);
		GL11.glTranslatef(0, -scroll, 0);
		for (int i = 0; i < Solar.getModules().size(); i++) {
			Module module = Solar.getModules().get(i);
			//Main Box
			if (collum == 4)
				row++;
			collum = (i % 4) + 1;
			int topX = ((-300) - (-100 * collum)) + buffer;
			int bottomX = topX + (100 - buffer);
			int topY, bottomY;
			topY = -100 + header + buffer + (buffer * 2 + 100 + header) * (row - 1);
			bottomY = topY + 100;
			drawRoundedRect(topX, topY, bottomX, bottomY, 5, 0x44C8C8C8);
			String[] description = module.getDescription();
			if (module.icon != null) {
				var6 = (int) ((topX + ((bottomX - topX) / 2)) / scale) - var5 / 2;
				var7 = (int) ((topY + ((bottomY - topY) / 2) - 15) / scale) - var5 / 2;
				this.mc.getTextureManager().bindTexture(module.icon);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glScalef(scale, scale, scale);
				this.drawTexturedModalRect(var6, var7 + ((bottomY - topY) / 2), 0, 0, var5, var5);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glScalef(mscale, mscale, mscale);
			} else {
				for (int j = 0; j < description.length; j++)
					drawString(this.fontRendererObj, description[j], (topX + buffer/2), (topY + buffer/2) + (j * 10), new Color(200, 200, 200).hashCode());
			}
			//
			//Header
			drawRoundedRect(topX, topY - header, bottomX, topY  - buffer/2, 5, 0x44C8C8C8);
			if (module.isToggled())
				if (getSwitch("toggle." + module.getName()) == null) switches.add(new GuiToggleSwitch("toggle." + module.getName(), bottomX - 22, topY - header + 2, bottomX - 2, topY  - buffer/2 - 2, this, module.isToggled()));
				else {
					getSwitch("toggle." + module.getName()).setToggled(module.isToggled());
					getSwitch("toggle." + module.getName()).setScroll(scroll);
				}
			else
				if (getSwitch("toggle." + module.getName()) == null) switches.add(new GuiToggleSwitch("toggle." + module.getName(), bottomX - 22, topY - header + 2, bottomX - 2, topY  - buffer/2 - 2, this, module.isToggled()));
				else {
					getSwitch("toggle." + module.getName()).setToggled(module.isToggled());
					getSwitch("toggle." + module.getName()).setScroll(scroll);
				}
			this.mc.cFontRenderer.drawString(module.getName(), topX + buffer/2, topY + buffer/2 - header + 1, 0xFFFFFF);
			//
			//Footer
			//drawRect(topX, bottomY - footer, bottomX, bottomY, new Color(75, 75, 75, 75).hashCode());

	        
	        if (module.settingsGui != null) {
		        if (getCog("settings." + module.getName()) == null) cogs.add(new GuiCogButton("settings." + module.getName(), topX + 2, bottomY - footer, topX + 18, bottomY, 0x00000000, this));
		        else {
			        getCog("settings." + module.getName()).setScroll(scroll);
		        }
	        }
			//
		}
		for (GuiCogButton button : cogs)
			button.draw(mouseX, mouseY);
		for (GuiToggleSwitch button : switches)
			button.draw(mouseX, mouseY);
		GL11.glTranslatef(0, scroll, 0);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		//Header
		drawRoundedRect(-boxWidth, -boxHeight - 15, boxWidth + buffer, -boxHeight - 2, 5, 0x44C8C8C8);
		if (getButton("close") == null) buttons.add(new GuiButton("close", "X", boxWidth - 9, -boxHeight - 15, boxWidth + buffer, -boxHeight - 2, buffer, 0x00000000, 0xFFFFFF, this));
		this.mc.cFontRenderer.drawString("Modules", -boxWidth + 2, -boxHeight - 12, 0xFFFFFF);
		//
		for (GuiButton button : buttons)
			button.draw(mouseX, mouseY);
		mc.fontRenderer.setFancy(false);
		if (frame > 0) frame--;
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
	
	private GuiToggleSwitch getSwitch(String name) {
		for (GuiToggleSwitch butt : switches)
			if (butt.name.equals(name))
				return butt;
		return null;
	}

}
