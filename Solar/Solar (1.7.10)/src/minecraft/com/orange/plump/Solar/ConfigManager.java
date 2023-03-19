/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.orange.plump.Solar.modules.Module;

public class ConfigManager {
	
	public static ConfigManager instance;
	private final File folder;
	private static String name = "Solar";
	private static final String __OBFID = "CL_00200000";
	
	public ConfigManager() {
		instance = this;
		File temp = new File("temp.file");
		File folder = new File(temp.getAbsoluteFile().toString().replace("temp.file", "") + name);
		//System.out.println(folder.getAbsolutePath().toString());
		if (!folder.exists())
			folder.mkdirs();
		this.folder = folder;
	}
	
	public String checkForPin() {
		if (!folder.exists())
			folder.mkdirs();
		File pinFile = new File(folder.getAbsolutePath().toString() + "\\pin.txt");
		if (!pinFile.exists())
			return null;
		else {
			String pin = "";
			try {
				BufferedReader reader = new BufferedReader(new FileReader(pinFile));
				String buffer;
				while((buffer = reader.readLine()) != null)
					pin += buffer;
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (pin.equals("")) return null;
			else return pin;
		}
	}
	
	public File registerMainProperties(String defaultProperties) {
		if (!folder.exists())
			folder.mkdirs();
		File mainProperties = new File(folder.getAbsolutePath().toString() + "\\" + name + ".properties");
		if (!mainProperties.exists())
			mainProperties = createMainProperties(defaultProperties);
		return mainProperties;
	}
	
	public HashMap<Module, File> registerModulesProperties(List<Module> modules) {
		if (!folder.exists())
			folder.mkdirs();
		HashMap<Module, File> properties = new HashMap<Module, File>();
		for (Module module : modules) {
			File moduleProperties = new File(folder.getAbsolutePath().toString() + "\\" + module.getName() + ".properties");
			if (!moduleProperties.exists())
				moduleProperties = createModuleProperties(module);
			properties.put(module, moduleProperties);
		}
		return properties;
	}
	
	private File createModuleProperties(Module module) {
		File moduleProperties = new File(folder.getAbsolutePath().toString() + "\\" + module.getName() + ".properties");
		try {
			moduleProperties.createNewFile();
			
			FileWriter writer = new FileWriter(moduleProperties, false);
			
			writer.write(module.properties);
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return moduleProperties;
	}
	
	private File createMainProperties(String defaultProperties) {
		File mainProperties = new File(folder.getAbsolutePath().toString() + "\\" + name + ".properties");
		try {
			mainProperties.createNewFile();
			
			FileWriter writer = new FileWriter(mainProperties, false);
			
			writer.write(defaultProperties);
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return mainProperties;
	}
	
	

}
