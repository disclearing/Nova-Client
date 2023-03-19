package com.orange.plump.Solar.gui.button;

import com.orange.plump.Solar.modules.RadioModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiSlider {
    private float value;
    private int x, y, width, height;
    public boolean field_146135_o;
    private static final String __OBFID = "CL_00000680";

    public static float mapRange(float a1, float a2, float b1, float b2, float s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    public GuiSlider(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = mapRange(0f, -60f, 1f, 0f, RadioModule.volume);
        Minecraft var7 = Minecraft.getMinecraft();
    }

    public void draw(GuiScreen screen) {
        screen.drawRect(x, y + (height / 2) - 1, x + width, y + (height / 2) + 1, 0x44000000);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        screen.drawRoundedRect(
                this.x + (int)(this.value * (float)(this.width - 8)),
                this.y,
                this.x + (int)(this.value * (float)(this.width - 8)) + 8,
                this.y + height,
                5,
                0x80FFFFFF);
    }


    public int getHoverState(boolean p_146114_1_)
    {
        return 0;
    }


    public void mouseDragged(int p_146119_2_, int p_146119_3_)
    {
        if (p_146119_2_ > x && p_146119_3_ > y && p_146119_2_ < x + width && p_146119_3_ < y + height) {
            this.value = (float) (p_146119_2_ - (this.x + 4)) / (float) (this.width - 8);
            if (this.value < 0.0F) {
                this.value = 0.0F;
            }

            if (this.value > 1.0F) {
                this.value = 1.0F;
            }
            RadioModule.setVolume(mapRange(1f, 0f, 0f, -60f, value));
        }
    }

    public void mousePressed(int p_146116_2_, int p_146116_3_)
    {
        if (p_146116_2_ > x && p_146116_3_ > y && p_146116_2_ < x + width && p_146116_3_ < y + height) {
            this.value = (float) (p_146116_2_ - (this.x + 4)) / (float) (this.width - 8);

            if (this.value < 0.0F) {
                this.value = 0.0F;
            }

            if (this.value > 1.0F) {
                this.value = 1.0F;
            }
            RadioModule.setVolume(mapRange(1f, 0f, 0f, -60f, value));

            this.field_146135_o = true;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int p_146118_1_, int p_146118_2_)
    {
        this.field_146135_o = false;
    }
}
