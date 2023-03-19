/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

public class TcpNoDelayModule extends Module {

	private static String[] description = {"Sets TcpNoDelay", "to True"};
	public static boolean toggled = true;
	
	public TcpNoDelayModule() {
		super("TcpNoDelay", description, true);
	}
	
	public TcpNoDelayModule(int index) {
		super("TcpNoDelay " + index, description, true);
	}
	
	public void onEnable() {
		super.onEnable();
		toggled = true;
	}
	
	public void onDisable() {
		super.onDisable();
		toggled = false;
	}
}
