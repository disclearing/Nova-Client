package com.orange.plump.Solar.gui.settings;

import com.orange.plump.Solar.gui.GuiManager;
import com.orange.plump.Solar.gui.ModuleGui;
import com.orange.plump.Solar.gui.button.GuiButton;
import com.orange.plump.Solar.modules.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class SettingsGui extends GuiScreen {

	public final Module module;
	private List<GuiButton> buttons;
	protected int scroll = 0;
	protected int frameHeight = 0;

	public SettingsGui(Module module) {
		this.module = module;
		buttons = new ArrayList<GuiButton>();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == mc.gameSettings.keyBindMainGui.getKeyCode()) {
			GuiManager.show(new ModuleGui());
		}
	}

	public void saveSettings() {

	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (GuiButton button : buttons)
			if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY)) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
				if (button.name.startsWith("close")) {
					GuiManager.show(new ModuleGui());
				}
			}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (; !this.mc.gameSettings.touchscreen && Mouse.next(); this.mc.currentScreen.handleMouseInput())
		{
			int var7 = Mouse.getEventDWheel();

			if (var7 != 0)
			{
				var7 = -var7;

				this.scroll += (float)(var7 / 5);
				if (this.scroll < 0) scroll = 0;
				if (this.scroll > frameHeight) scroll = frameHeight;
			}
		}
		buttons.clear();
		ScaledResolution scaled = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		mc.fontRenderer.setFancy(true);
		this.drawBlurredBackgroundStationary();
		float scale = 0.25F;
		float mscale = (float)Math.pow(scale,-1);
		short var5 = 256;
		int var6 = this.width / 2;
		int var7 = this.height / 2 - 100 - (var5 / 6) - 15;
		var6 = (int) (var6 / scale) - var5 / 2;
		var7 = (int) (var7 / scale) - var5 / 2;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/solar/Solar.png"));
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glScalef(scale, scale, scale);
		this.drawTexturedModalRect(var6 + 8, var7 + 0, 0, 0, var5, var5);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glScalef(mscale, mscale, mscale);

		int xMove = this.width/2, yMove = this.height/2;

		drawRect(-200 + xMove, -100 + 1 + yMove, 200 + 4 + xMove, -100 + yMove, 0x44C8C8C8);
		drawRect(-200 + xMove, 100 - 1 + yMove, 200 + 4 + xMove, 100 + yMove, 0x44C8C8C8);

		drawRect(-200 + 1 + xMove, -100 + 1 + yMove, -200 + xMove, 100 - 1 + yMove, 0x44C8C8C8);
		drawRect(200 + 4 - 1 + xMove, -100 + 1 + yMove, 200 + 4 + xMove, 100 - 1 + yMove, 0x44C8C8C8);

		drawRoundedRect(-200 + xMove, -100 - 15 + yMove, 200 + 4 + xMove, -100 - 2 + yMove, 5, 0x44C8C8C8);
		if (getButton("close") == null) buttons.add(new GuiButton("close", "X", 200 - 9, -100 - 15, 200 + 4, -100 - 2, 4, 0x00000000, 0xFFFFFF, this));
		this.mc.cFontRenderer.drawString(module.getName(), -200 + 2 + xMove, -100 - 12 + yMove, 0xFFFFFF);

		GL11.glTranslatef(xMove, yMove, 0);
		for (GuiButton button : buttons)
			button.draw(mouseX, mouseY);
		GL11.glTranslatef(-xMove, -yMove, 0);
		mc.fontRenderer.setFancy(false);
	}


	private GuiButton getButton(String name) {
		for (GuiButton butt : buttons)
			if (butt.name.equals(name))
				return butt;
		return null;
	}

}
