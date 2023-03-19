/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.gui.preview.Preview;

import com.orange.plump.Solar.gui.settings.SettingsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ResourceLocation;

public class Module {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private String[] description;
	private boolean toggled;
	public String properties;
	public Preview preview = null;
	public SettingsGui settingsGui = null;
	public ResourceLocation icon = null;
	
	public Module(String name, String[] description) {
		this.name = name;
		this.description = description;
		this.toggled = false;
		this.properties = "TOGGLED:FALSE";
	}
	
	public Module(String name, String[] description, String properties) {
		this.name = name;
		this.description = description;
		this.toggled = false;
		this.properties = properties;
	}
	
	public Module(String name, String[] description, boolean toggled) {
		this.name = name;
		this.description = description;
		this.toggled = toggled;
		if (toggled)
			this.properties = "TOGGLED:TRUE";
		else
			this.properties = "TOGGLED:FALSE";
	}
	
	public void ready() {
		if (properties.contains("TRUE"))
			this.setToggled(true);
		else
			this.setToggled(false);
	}
	
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		if (toggled)
			onEnable();
		else
			onDisable();
	}
	
	public void toggle() {
		toggled = !toggled;
		if (toggled)
			onEnable();
		else
			onDisable();
	}
	
	public void onEnable() {
		Solar.log("Enabling module " + name);
		Solar.setModuleProperties(this, "TOGGLED", "TRUE");
		//Orange.addChatMessage(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD + "Enabling Module " + ChatColor.GREEN + this.getName());
		
	}
	
	public void onDisable() {
		Solar.log("Disabling module " + name);
		Solar.setModuleProperties(this, "TOGGLED", "FALSE");
		//Orange.addChatMessage(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD + "Disabling Module " + ChatColor.RED + this.getName());
	}
			
	public void onUpdate() {
		
	}
	
	public void onRender() {
		
	}
	
	public void onLeftClick() {
		
	}
	
	public void onRenderGameOverlay(float partialTicks, GuiScreen screen) {
		
	}
	
    public void onRenderGameOverlayPreview(GuiScreen screen) {
		
	}
	
	public void onKeyPressed(int key) {
		
	}
	
	public String onSendChatMessage(String message) {
		return message;
	}
	
	public boolean onRecieveChatMessage(S02PacketChat packet) {
		return true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getDescription() {
		return this.description;
	}
	
	public boolean isToggled() {
		return this.toggled;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void onPropertiesUpdate() {
		
	}
	
	public void setProperties(String properties) {
		this.properties = properties;
		this.onPropertiesUpdate();
	}

}
