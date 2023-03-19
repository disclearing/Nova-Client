/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import com.orange.plump.Solar.modules.ArmorHUDModule;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.orange.plump.Solar.modules.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorHUDPreview extends Preview {

	private final RenderItem itemRenderer = new RenderItem();
	
	public ArmorHUDPreview(Module module) {
		super(module);
	}
	
	@Override
	public void renderPreview(GuiScreen screen) {
		super.renderPreview(screen);
		float mscale = (float)Math.pow(scale,-1);
		GL11.glScalef(scale, scale, scale);
		if (stayRight) {
			if (stayBottom) {
				drawHUD(mc.currentScreen.width - x, mc.currentScreen.height - y, screen);
			} else {
				drawHUD(mc.currentScreen.width - x, y, screen);
			}
		} else {
			if (stayBottom) {
				drawHUD(x, mc.currentScreen.height - y, screen);
			} else {
				drawHUD(x, y, screen);
			}
		}
		GL11.glScalef(mscale, mscale, mscale);
	}
	
	@Override
	public int getWidth() {
		if (ArmorHUDModule.drawHorizantal)
			return Math.round(95 * scale);
		else
			return Math.round(19 * scale);
	}
	
	@Override
	public int getHeight() {
		if (ArmorHUDModule.drawHorizantal)
			return Math.round(19 * scale);
		else
			return Math.round(95 * scale);
	}
	
	public void drawHUD(int theX, int theY, GuiScreen screen)
    {
        float armorScale = 1.2f;
        float mscale = (float)Math.pow(armorScale,-1);
        GL11.glScalef(armorScale, armorScale, armorScale);
        int armorX = Math.round(theX * mscale);
        int armorY = Math.round(theY * mscale);
        if (ArmorHUDModule.drawHorizantal) {
	        renderInventorySlot(armorX, armorY, new ItemStack(Item.getItemById(310), 1, 10));
	        renderInventorySlot(armorX + 16, armorY, new ItemStack(Item.getItemById(311), 1, 10));
	        renderInventorySlot(armorX + 32, armorY, new ItemStack(Item.getItemById(312), 1, 10));
	        renderInventorySlot(armorX + 48, armorY, new ItemStack(Item.getItemById(313), 1, 10));
	        renderInventorySlot(armorX + 64, armorY , new ItemStack(Item.getItemById(276), 1, 10));
        } else {
	        renderInventorySlot(armorX, armorY, new ItemStack(Item.getItemById(310), 1, 10));
	        renderInventorySlot(armorX, armorY + 16, new ItemStack(Item.getItemById(311), 1, 10));
	        renderInventorySlot(armorX, armorY + 32, new ItemStack(Item.getItemById(312), 1, 10));
	        renderInventorySlot(armorX, armorY + 48, new ItemStack(Item.getItemById(313), 1, 10));
	        renderInventorySlot(armorX, armorY + 64, new ItemStack(Item.getItemById(276), 1, 10));
        }
        GL11.glScalef(mscale, mscale, mscale);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        this.mc.mcProfiler.endSection();
        GL11.glDisable(GL11.GL_BLEND);
    }
	
	private void renderInventorySlot(int p_73832_2_, int p_73832_3_, ItemStack item)
    {
        ItemStack var5 = item;

        if (var5 != null)
        {

            itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), var5, p_73832_2_, p_73832_3_);
            itemRenderer.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), var5, p_73832_2_, p_73832_3_);
        }
    }
}
