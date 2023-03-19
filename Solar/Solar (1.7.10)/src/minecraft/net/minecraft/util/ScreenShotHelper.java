package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import com.sun.javafx.iio.ImageStorage;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;

public class ScreenShotHelper
{
    private static final Logger logger = LogManager.getLogger();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    /** A buffer to hold pixel values returned by OpenGL. */
    private static IntBuffer pixelBuffer;

    /**
     * The built-up array that contains all the pixel values returned by OpenGL.
     */
    private static int[] pixelValues;
    private static final String __OBFID = "CL_00000656";
    private static boolean isTaking = false;

    /**
     * Saves a screenshot in the game directory with a time-stamped filename.  Args: gameDirectory,
     * requestedWidthInPixels, requestedHeightInPixels, frameBuffer
     */
    public static void saveScreenshot(File p_148260_0_, int p_148260_1_, int p_148260_2_, Framebuffer p_148260_3_)
    {
        saveScreenshot(p_148260_0_, (String)null, p_148260_1_, p_148260_2_, p_148260_3_);
    }

    /**
     * Saves a screenshot in the game directory with the given file name (or null to generate a time-stamped name).
     * Args: gameDirectory, fileName, requestedWidthInPixels, requestedHeightInPixels, frameBuffer
     */
    public static void saveScreenshot(File p_148259_0_, String p_148259_1_, int p_148259_2_, int p_148259_3_, Framebuffer p_148259_4_)
    {
        try
        {
            final File var5 = new File(p_148259_0_, "screenshots");
            var5.mkdir();

            GL11.glReadBuffer(GL11.GL_FRONT);
            final int width = Display.getDisplayMode().getWidth();
            final int height= Display.getDisplayMode().getHeight();
            final int bpp = 4;
            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
            GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            final File a = p_148259_0_;
            final String b = p_148259_1_;
            final int c = p_148259_2_;
            final int d = p_148259_3_;
            final ByteBuffer e = buffer;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {


                        File var12;

                        if (b == null)
                        {
                            var12 = getTimestampedPNGFileForDirectory(var5);
                        }
                        else
                        {
                            var12 = new File(var5, b);
                        }
                        final File fp = var12;

                        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                        for(int x = 0; x < width; x++)
                        {
                            for(int y = 0; y < height; y++)
                            {
                                int i = (x + (width * y)) * bpp;
                                int r = e.get(i) & 0xFF;
                                int g = e.get(i + 1) & 0xFF;
                                int b = e.get(i + 2) & 0xFF;
                                bimg.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
                            }
                        }


                        ImageIO.write(bimg, "png", fp);ChatComponentText var13 = new ChatComponentText(fp.getName());
                        var13.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, fp.getAbsolutePath()));
                        var13.getChatStyle().setUnderlined(Boolean.valueOf(true));
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("screenshot.success", new Object[]{var13}));

                    } catch (Exception var11)
                    {
                        logger.warn("Couldn\'t save screenshot", var11);
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("screenshot.failure", new Object[] {var11.getMessage()}));
                        isTaking = false;
                    }
                }
            }).start();
            isTaking = false;
        }
        catch (Exception var11)
        {
            logger.warn("Couldn\'t save screenshot", var11);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("screenshot.failure", new Object[] {var11.getMessage()}));
            isTaking = false;
        }
    }

/**
 * Creates a unique PNG file in the given directory named by a timestamp.  Handles cases where the timestamp alone
 * is not enough to create a uniquely named file, though it still might suffer from an unlikely race condition where
 * the filename was unique when this method was called, but another process or thread created a file at the same
 * path immediately after this method returned.
 */
private static File getTimestampedPNGFileForDirectory(File p_74290_0_)
    {
        String var2 = dateFormat.format(new Date()).toString();
        int var3 = 1;

        while (true)
        {
            File var1 = new File(p_74290_0_, var2 + (var3 == 1 ? "" : "_" + var3) + ".png");

            if (!var1.exists())
            {
                return var1;
            }

            ++var3;
        }
    }
}
