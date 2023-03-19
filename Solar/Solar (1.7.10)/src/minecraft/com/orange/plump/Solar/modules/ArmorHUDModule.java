/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import com.orange.plump.Solar.gui.settings.ArmorHUDSettings;
import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.gui.preview.ArmorHUDPreview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class ArmorHUDModule extends Module {
	private static String[] description = {"Displays your armor", "and the item in", "your hand"};
	public static int x, y;
	public static boolean stayRight = false;
	public static boolean stayBottom = false;
	public static boolean drawHorizantal = false;
	public static float scale;
	public static boolean toggled = false;
	private static final RenderItem itemRenderer = new RenderItem();
	
	public ArmorHUDModule() {
		super("ArmorHUD", description, "TOGGLED:FALSE>LOCATION:2/50>SIDE:LEFT/TOP>SCALE:1.0>DIR:VERTICAL");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	public ArmorHUDModule(int index) {
		super("ArmorHUD " + index, description, "TOGGLED:FALSE>LOCATION:2/50>SIDE:LEFT/TOP>SCALE:1.0>DIR:VERTICAL");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	
	@Override
	public void onEnable() {
		super.onEnable();
		ArmorHUDModule.toggled = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		ArmorHUDModule.toggled = false;
	}
	
	@Override
	public void ready() {
		super.ready();
		this.preview = new ArmorHUDPreview(this);
		this.settingsGui = new ArmorHUDSettings(this);
	}
	
	@Override
	public void onPropertiesUpdate() {
		super.onPropertiesUpdate();
		for (String property : this.properties.split(">")) {
			if (property.startsWith("LOCATION")) {
				String location = property.replace("LOCATION:", "");
				x = Integer.valueOf(location.split("/")[0]);
				y = Integer.valueOf(location.split("/")[1]);
			} else if (property.startsWith("SIDE")) {
				String side = property.replace("SIDE:", ""); 
				String rl = side.split("/")[0];
				String tb = side.split("/")[1];
				if (rl.equals("RIGHT")) stayRight = true;
				else stayRight = false;
				if (tb.equals("BOTTOM")) stayBottom = true;
				else stayBottom = false;
			} else if (property.startsWith("SCALE")) {
				String value = property.replace("SCALE:", ""); 
				scale = Float.parseFloat(value);
			} else if (property.startsWith("DIR")) {
				String value = property.replace("DIR:", "");
				if (value.equalsIgnoreCase("VERTICAL")) drawHorizantal = false;
				else drawHorizantal = true;
			}
			
		}
	}
	
	public static void drawHUD(int theX, int theY)
    {
		ItemStack[] armor = new ItemStack[4];
        for (int i = 0; i < 4; i++)
        	armor[i] = Minecraft.getMinecraft().thePlayer.getCurrentArmor(i);
        ItemStack inHand = Minecraft.getMinecraft().thePlayer.getHeldItem();
        float armorScale = 1.2f;
        float mscale = (float)Math.pow(armorScale,-1);
        GL11.glScalef(armorScale, armorScale, armorScale);
        int armorX = Math.round(theX * mscale);
        int armorY = Math.round(theY * mscale);
        if (drawHorizantal) {
	        renderInventorySlot(armorX, armorY, armor[3]);
	        renderInventorySlot(armorX + 16, armorY, armor[2]);
	        renderInventorySlot(armorX + 32, armorY, armor[1]);
	        renderInventorySlot(armorX + 48, armorY, armor[0]);
	        renderInventorySlot(armorX + 64, armorY, inHand);
        } else {
	        renderInventorySlot(armorX, armorY, armor[3]);
	        renderInventorySlot(armorX, armorY + 16, armor[2]);
	        renderInventorySlot(armorX, armorY + 32, armor[1]);
	        renderInventorySlot(armorX, armorY + 48, armor[0]);
	        renderInventorySlot(armorX, armorY + 64, inHand);
        }
        GL11.glScalef(mscale, mscale, mscale);
        
    }
	
	private static void renderInventorySlot(int p_73832_2_, int p_73832_3_, ItemStack item)
    {
        ItemStack var5 = item;

        if (var5 != null)
        {

            itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), var5, p_73832_2_, p_73832_3_);
            itemRenderer.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), var5, p_73832_2_, p_73832_3_);
        }
    }
}

