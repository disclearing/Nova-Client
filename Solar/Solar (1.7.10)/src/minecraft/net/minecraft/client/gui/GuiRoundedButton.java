package net.minecraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;

public class GuiRoundedButton extends GuiButton {

	public GuiRoundedButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, int i, int j, String p_i1020_4_) {
		super(p_i1020_1_, p_i1020_2_, p_i1020_3_, i, j, p_i1020_4_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
		if (this.field_146125_m)
        {
            FontRenderer var4 = p_146112_1_.fontRenderer;
            p_146112_1_.getTextureManager().bindTexture(field_146122_a);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g;
            int var5 = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //this.drawRect(this.field_146128_h, this.field_146129_i, this.field_146128_h + this.field_146120_f, this.field_146129_i +  this.field_146121_g, color);
            int radius = this.field_146120_f / 2;
            drawCircle(this.field_146128_h + radius, this.field_146129_i + radius, radius, color);
            //this.drawTexturedModalRect(this.field_146128_h, this.field_146129_i, 0, 46 + var5 * 20, this.field_146120_f / 2, this.field_146121_g);
            //this.drawTexturedModalRect(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + var5 * 20, this.field_146120_f / 2, this.field_146121_g);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int var6 = 14737632;

            if (!this.enabled)
            {
                var6 = 10526880;
            }
            else if (this.field_146123_n)
            {
                var6 = 0xFFFF80;
            }

            this.drawCenteredString(var4, this.displayString, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2 - 1, var6);
        }
	}
	
	public void drawCircle(int x, int y, float radius, int p_73734_4_)
    {
		float var10 = (float)(p_73734_4_ >> 24 & 255) / 255.0F;
        float var6 = (float)(p_73734_4_ >> 16 & 255) / 255.0F;
        float var7 = (float)(p_73734_4_ >> 8 & 255) / 255.0F;
        float var8 = (float)(p_73734_4_ & 255) / 255.0F;
    	double DEG2RAD = 3.14159/180;
    	GL11.glEnable(GL11.GL_BLEND);
    	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    	GL11.glBegin(GL11.GL_LINE_LOOP);
    	GL11.glColor4f(var6, var7, var8, var10);
       for (int i=0; i < 360; i++)
       {
          double degInRad = (i*DEG2RAD);
          GL11.glVertex2d(x, y);
          GL11.glVertex2d(Math.cos(degInRad)*radius + x,Math.sin(degInRad)*radius + y);
       }
       GL11.glEnd();
    }

}
