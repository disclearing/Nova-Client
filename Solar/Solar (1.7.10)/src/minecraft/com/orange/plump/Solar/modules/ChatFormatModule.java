/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import net.minecraft.util.ResourceLocation;

public class ChatFormatModule extends Module {

	private static String[] description = {"Automatically", "formats chat"};
	
	public ChatFormatModule() {
		super("ChatFormat", description);
	}
	public ChatFormatModule(int index) {
		super("ChatFormat " + index, description);
	}
	
	@Override
	public String onSendChatMessage(String message) {
		if (this.isToggled())
			return format(message);
		else return message;
	}

	@Override
	public void ready() {
		super.ready();
		this.icon = new ResourceLocation("textures/solar/modules/ChatFormat.png");
	}

	private String format(String message) {
		if (!message.startsWith("/") && !message.equals("IGNORE}}}")) {
			if (!message.endsWith(".") && !message.endsWith("!") && !message.endsWith("?")) message += ".";
			String[] chars = message.split("");
			chars[0] = chars[0].toUpperCase();
			String buffer = "";
			for (String s : chars) {
				buffer+=s;
			}
			message = buffer;
		}
		return message;
	}
}
