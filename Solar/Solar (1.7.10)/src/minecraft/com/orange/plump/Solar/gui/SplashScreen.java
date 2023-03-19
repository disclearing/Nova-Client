package com.orange.plump.Solar.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SplashScreen {

	private static int current = 1;
	private static int progress = 40;
	private static ResourceLocation splash1, splash2, splash3;
	private static float zLevel = 1.0f;

	public static void update() {
		if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) return;
		drawScreen(Minecraft.getMinecraft().getTextureManager());
	}

	public static void setProgress(int progress) {
		current = SplashScreen.progress;
		SplashScreen.progress = progress;
		update();
	}


	private static void drawScreen(TextureManager textureManager) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int scale = scaledResolution.getScaleFactor();

		Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scale, scaledResolution.getScaledHeight() * scale, true);
		framebuffer.bindFramebuffer(false);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0D, (double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight(), 0D, 1000D, 3000D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0, 0, -2000);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_DEPTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		if (splash1 == null) splash1 = new ResourceLocation("textures/solar/splash1.png");
		if (splash2 == null) splash2 = new ResourceLocation("textures/solar/splash2.png");
		if (splash3 == null) splash3 = new ResourceLocation("textures/solar/splash3.png");

		GL11.glColor4f(1, 1, 1, 1);
		float tscale = 1.75f;
		float mscale = (float)Math.pow(tscale,-1);
		float tscale1 = 1.25f;
		float mscale1 = (float)Math.pow(tscale1,-1);
		GL11.glScalef(tscale, 1, 1);
		zLevel = 1.0f;
		textureManager.bindTexture(splash1);
		drawTexturedModalRectS(0, 0, 256, 256, 256, 256);

		GL11.glScalef(1, tscale1, 1);
		zLevel = 2.0f;
		textureManager.bindTexture(splash2);
		drawTexturedModalRectS(0, -Minecraft.getMinecraft().displayHeight + ((Minecraft.getMinecraft().displayHeight / 100) * progress), 256, 256, 256, 256);
		GL11.glScalef(1, mscale1 ,1);

		zLevel = 3.0f;
		textureManager.bindTexture(splash3);
		drawTexturedModalRectS(0, 0, 256, 256, 256, 256);
		GL11.glScalef(mscale, 1 ,1);
		framebuffer.unbindFramebuffer();
		framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scale, scaledResolution.getScaledHeight() * scale);

		Minecraft.getMinecraft().updateDisplay();
	}

	public static void drawTexturedModalRectS(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + p_73729_6_), (double) zLevel, (double) ((float) (p_73729_3_ + 0) * var7), (double) ((float) (p_73729_4_ + p_73729_6_) * var8));
		var9.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + p_73729_6_), (double) zLevel, (double) ((float) (p_73729_3_ + p_73729_5_) * var7), (double) ((float) (p_73729_4_ + p_73729_6_) * var8));
		var9.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + 0), (double) zLevel, (double) ((float) (p_73729_3_ + p_73729_5_) * var7), (double) ((float) (p_73729_4_ + 0) * var8));
		var9.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + 0), (double) zLevel, (double) ((float) (p_73729_3_ + 0) * var7), (double) ((float) (p_73729_4_ + 0) * var8));
		var9.draw();
	}
}
