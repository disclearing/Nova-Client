/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;

public class FancyPingModule extends Module {
	
	private static String[] description = {"Fancies the tab", "ping"};
	public static boolean isEnabled = true;
	
	public FancyPingModule() {
		super("FancyPing", description, true);
	}
	public FancyPingModule(int index) {
		super("FancyPing " + index, description, true);
	}
	
	public void onEnable() {
		super.onEnable();
		isEnabled = true;
	}
	
	public void onDisable() {
		super.onDisable();
		isEnabled = false;
	}

	@Override
	public void ready() {
		super.ready();
		this.icon = new ResourceLocation("textures/solar/modules/FancyPing.png");
	}
}
