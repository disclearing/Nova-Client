package com.orange.plump.Solar.gui.settings;

import com.orange.plump.Solar.modules.ArmorHUDModule;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class ArmorHUDSettings extends SettingsGui {

	private final ArmorHUDModule module;

	public ArmorHUDSettings(ArmorHUDModule module) {
		super(module);
		this.module = module;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		ScaledResolution scaled = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(0, (this.mc.displayHeight / 2) - ((100) * scaled.getScaleFactor() - 4) - (0 * scaled.getScaleFactor()), this.mc.displayWidth, ((100) * scaled.getScaleFactor()) * 2 - 4 * 2);
		GL11.glTranslatef(0, -this.scroll, 0);
		GL11.glTranslatef((float) (this.width/2) - 200 + 2, (float) (this.height/2) - 100 + 2, 0f);
		draw(mouseX, mouseY);
		GL11.glTranslatef((float) -((this.width/2) - 200 + 2), (float) -((this.height/2) - 100 + 2), 0f);
		GL11.glTranslatef(0, scroll, 0);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public void draw(int mouseX, int mouseY) {
		drawRoundedRect(0, 0, 400, 100 - 6, 20, 0x44C8C8C8);

	}

}
