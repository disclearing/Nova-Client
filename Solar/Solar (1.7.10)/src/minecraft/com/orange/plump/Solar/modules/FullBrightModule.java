/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;

public class FullBrightModule extends Module {
	
	public static float original = 0f;
	private static String[] description = {"Sets gamma", "to its max"};
	
	public FullBrightModule() {
		super("FullBright", description);
	}
	public FullBrightModule(int index) {
		super("FullBright " + index, description);
	}
	
	@Override
	public void onUpdate() {
		if (this.isToggled())
			mc.gameSettings.gammaSetting = 150f;
		else
			mc.gameSettings.gammaSetting = original;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.gammaSetting = 0f;
	}
	
	@Override 
	public void onEnable() {
		super.onEnable();
		original = mc.gameSettings.gammaSetting;
	}

	@Override
	public void ready() {
		super.ready();
		this.icon = new ResourceLocation("textures/solar/modules/FullBright.png");
	}
}
