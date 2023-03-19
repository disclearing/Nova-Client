/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;

public class ScoreboardModule extends Module {
	
	private static String[] description = {"Toggles the", "scoreboard"};
	public static boolean isEnabled = true;
	public static boolean doScores = true;
	
	public ScoreboardModule() {
		super("Scoreboard", description, "TOGGLED:TRUE>DOSCORE:TRUE");
	}
	public ScoreboardModule(int index) {
		super("Scoreboard " + index, description, "TOGGLED:TRUE>DOSCORE:TRUE");
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
	public void onPropertiesUpdate() {
		super.onPropertiesUpdate();
		for (String property : this.properties.split(">")) {
			if (property.startsWith("DOSCORE")) {
				String value = property.replace("DOSCORE:", "");
				if (value.equalsIgnoreCase("TRUE"))
					doScores = true;
				else
					doScores = false;
			}
		}
	}

	@Override
	public void ready() {
		super.ready();
		this.icon = new ResourceLocation("textures/solar/modules/Scoreboard.png");
	}
	
}
