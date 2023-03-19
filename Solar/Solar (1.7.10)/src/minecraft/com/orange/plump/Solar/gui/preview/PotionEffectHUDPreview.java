/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar.gui.preview;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.orange.plump.Solar.modules.Module;
import com.orange.plump.Solar.modules.PotionEffectHUDModule;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectHUDPreview extends Preview {

	private static HashMap<Integer, PotionEffect> previewEffects = new HashMap<Integer, PotionEffect>();
	
	public PotionEffectHUDPreview(Module module) {
		super(module);
		previewEffects.put(0, new PotionEffect(1, 600, 0));
		previewEffects.put(1, new PotionEffect(12, 1200, 0));
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
		return Math.round(100 * scale);
	}
	
	@Override
	public int getHeight() {
		return Math.round((23 * previewEffects.size()) * scale);
	}
	
	public void drawHUD(int theX, int theY, GuiScreen screen)
    {
        int var1 = theX - 6;
        int var2 = theY - 6;
        boolean var3 = true;
        Collection var4 = previewEffects.values();

        if (!var4.isEmpty())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int var5 = 25;

            if (var4.size() > 5)
            {
                var5 = 132 / (var4.size() - 1);
            }

            for (Iterator var6 = previewEffects.values().iterator(); var6.hasNext(); var2 += var5)
            {
                PotionEffect var7 = (PotionEffect)var6.next();
                Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(PotionEffectHUDModule.field_147001_a);
                //screen.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

                if (var8.hasStatusIcon())
                {
                    int var9 = var8.getStatusIconIndex();
                    screen.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String var11 = I18n.format(var8.getName(), new Object[0]);

                if (var7.getAmplifier() > 0)
                {
                    var11 = var11 + " " + I18n.format("enchantment.level." + (var7.getAmplifier() + 1), new Object[0]);
                }

                mc.fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(var7);
                mc.fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
            }
        }
    }
}
