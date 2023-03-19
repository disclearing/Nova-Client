/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar;

public enum ChatColor {
	
	DARK_RED("§4"), RED("§c"), GOLD("§6"), YELLOW("§e"), 
	DARK_GREEN("§2"), GREEN("§a"), DARK_AQUA("§3"), AQUA("§b"), 
	DARK_BLUE("§1"), BLUE("§9"), LIGHT_PURPLE("§d"), DARK_PURPLE("§5"), 
	WHITE("§f"), BLACK("§0"), GRAY("§7"), DARK_GRAY("§8"), RESET("§r"), 
	BOLD("§l"), ITALIC("§o"), UNDERLINED("§n"), STRIKE("§m"), RANDOM("§k");
	
	private String value;

	ChatColor(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
