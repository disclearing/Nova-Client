/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

public class FlyBoostModule extends Module {

	private static String[] description = {"Boosts your fly", "speed when", "sprinting"};
	private boolean toggled = false;
	private int speed = 5;
	
	public FlyBoostModule() {
		super("FlyBoost", description, "TOGGLED:TRUE>SPEED:5");
	}
	
	public FlyBoostModule(int index) {
		super("FlyBoost " + index, description, "TOGGLED:TRUE>SPEED:5");
	}

	@Override
	public void onUpdate() {
		if (mc.thePlayer.capabilities.isFlying && mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint))
			mc.thePlayer.capabilities.setFlySpeed(0.05f * speed);
		if (mc.thePlayer.capabilities.isFlying && mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint) && mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSneak))
			mc.thePlayer.motionY -= speed * 0.1;
		if (mc.thePlayer.capabilities.isFlying && mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint) && mc.gameSettings.isKeyDown(mc.gameSettings.keyBindJump))
			mc.thePlayer.motionY += speed * 0.1;
		if (mc.thePlayer.capabilities.isFlying && !mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint))
			mc.thePlayer.capabilities.setFlySpeed(0.05f);
	}

	@Override
	public void onPropertiesUpdate() {
		super.onPropertiesUpdate();
		for (String property : this.properties.split(">")) {
			if (property.startsWith("DOSCORE")) {
				String value = property.replace("DOSCORE:", "");
				int speed = Integer.valueOf(value);
				this.speed = speed;
			}
		}
	}
}
