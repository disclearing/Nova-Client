/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiManager {
	
	public static boolean windowIsOpen = false;
	private static final String __OBFID = "CL_00600000";
	
	public GuiManager() {
		
	}
	
	public static void show(GuiScreen gui) {
		Minecraft.getMinecraft().displayGuiScreen(gui);
		windowIsOpen = true;
	}
	
	public static void onKeyPressed(int key) {
		if (key ==  Minecraft.getMinecraft().gameSettings.keyBindMainGui.getKeyCode())
			if (!windowIsOpen)
				show(new HomeGui());
	}
	
	public static void closeGui() {
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);

        if (Minecraft.getMinecraft().currentScreen == null) {
        	Minecraft.getMinecraft().setIngameFocus();
        }
        windowIsOpen = false;
	}
}
