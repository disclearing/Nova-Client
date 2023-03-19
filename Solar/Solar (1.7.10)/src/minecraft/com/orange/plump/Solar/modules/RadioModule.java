package com.orange.plump.Solar.modules;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.RadioThread;
import com.orange.plump.Solar.Station;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class RadioModule extends Module {

	public static boolean isToggled;
	private static  RadioModule module;
	public static float volume;
	private static String[] description = {"In game radio", "system"};

	public RadioModule() {
		super("Radio", description, "TOGGLED:FALSE>VOLUME:-30");
	}
	public RadioModule(int index) {
		super("Radio " + index, description, "TOGGLED:FALSE>VOLUME:-30");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		isToggled = false;
		RadioThread.pauseStation();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		isToggled = true;
	}

	@Override
	public void ready() {
		super.ready();
		isToggled = isToggled();
		module = this;
	}

	@Override
	public void onPropertiesUpdate() {
		super.onPropertiesUpdate();
		for (String property : this.properties.split(">")) {
			if (property.startsWith("VOLUME")) {
				String volumeStr = property.replace("VOLUME:", "");
				volume = Float.valueOf(volumeStr);
			}
		}
	}

	public static void drawRadio(GuiScreen screen, int x, int y) {

		ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int width = var3.getScaledWidth();
		int height = var3.getScaledHeight();
		screen.drawRoundedRect(width - 177, 2, width - 2, 67, 10, 0x80444444);
		screen.drawRect(width - 177 + 15, 67 - 7, width - 17, 67 - 8, 0x44000000);
		screen.drawRect(width - 177 + 15,  7, width - 17, 8, 0x44000000);
		Station currentStation = RadioThread.currentStation;
		if (currentStation != null) {
			Minecraft.getMinecraft().fontRenderer.setFancy(true);
			screen.drawCenteredString(Minecraft.getMinecraft().fontRenderer, currentStation.getName(), width - (((175 / 2)) + 2) + 15, (((65 / 2)) + 2) - 15, 0xFFFFFF);
			Minecraft.getMinecraft().fontRenderer.setFancy(false);
		}
	}


	public static void setVolume(float volume) {
		if (volume > 0f) volume = 0f;
		if (volume < -60f) volume = 60f;
		Solar.setModuleProperties(module, "VOLUME", String.valueOf(volume));
		RadioThread.setVolume(volume);
	}

}
