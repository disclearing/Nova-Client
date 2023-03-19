/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.modules;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.gui.preview.PotionEffectHUDPreview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionEffectHUDModule extends Module {
	private static String[] description = {"Shows your", "active potion", "effects"};
	public static int x, y;
	public static boolean stayRight = false;
	public static boolean stayBottom = false;
	public static float scale;
	public static final ResourceLocation field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
	public static boolean toggled = false;
	
	public PotionEffectHUDModule() {
		super("PotionEffectHUD", description, "TOGGLED:FALSE>LOCATION:2/50>SIDE:LEFT/TOP>SCALE:1");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	public PotionEffectHUDModule(int index) {
		super("PotionEffectHUD " + index, description, "TOGGLED:FALSE>LOCATION:2/50>SIDE:LEFT/TOP>SCALE:1");
		x = 2;
		y = 2;
		stayRight = false;
		scale = 1F;
	}
	
	
	@Override
	public void onEnable() {
		super.onEnable();
		PotionEffectHUDModule.toggled = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		PotionEffectHUDModule.toggled = false;
	}
	
	@Override
	public void ready() {
		super.ready();
		this.preview = new PotionEffectHUDPreview(this);
		this.icon = new ResourceLocation("textures/solar/modules/PotionEffectHUD.png");
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
			}
			
		}
	}
	
	public static void drawHUD(int theX, int theY)
    {
        int var1 = theX - 6;
        int var2 = theY - 6;
        boolean var3 = true;
        Collection var4 = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();

        if (!var4.isEmpty())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int var5 = 25;

            if (var4.size() > 5)
            {
                var5 = 132 / (var4.size() - 1);
            }

            for (Iterator var6 = Minecraft.getMinecraft().thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
            {
                PotionEffect var7 = (PotionEffect)var6.next();
                Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(field_147001_a);
                //screen.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

                if (var8.hasStatusIcon())
                {
                    int var9 = var8.getStatusIconIndex();
                    drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String var11 = I18n.format(var8.getName(), new Object[0]);

                if (var7.getAmplifier() > 0)
                {
                    var11 = var11 + " " + I18n.format("enchantment.level." + (var7.getAmplifier() + 1), new Object[0]);
                }

                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(var7);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
            }
        }
    }
	
	public static void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)0, (double)((float)(p_73729_3_ + 0) * var7), (double)((float)(p_73729_4_ + p_73729_6_) * var8));
        var9.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)0, (double)((float)(p_73729_3_ + p_73729_5_) * var7), (double)((float)(p_73729_4_ + p_73729_6_) * var8));
        var9.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)0, (double)((float)(p_73729_3_ + p_73729_5_) * var7), (double)((float)(p_73729_4_ + 0) * var8));
        var9.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)0, (double)((float)(p_73729_3_ + 0) * var7), (double)((float)(p_73729_4_ + 0) * var8));
        var9.draw();
    }
}
