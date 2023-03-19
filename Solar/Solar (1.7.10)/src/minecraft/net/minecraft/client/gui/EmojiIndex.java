package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmojiIndex {

	private static final ResourceLocation json = new ResourceLocation("textures/solar/emoji.json");

	public String getData() throws IOException {
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(json).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String str;
		StringBuilder builder = new StringBuilder();
		while ((str = reader.readLine()) != null) {
			builder.append(str);
		}
		return builder.toString();
	}
}
