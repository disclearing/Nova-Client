package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import com.orange.plump.Solar.Solar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

public class CapeUtils
{	
	
    public static void downloadCape(final AbstractClientPlayer player)
    {
        String username = player.getNameClear();
        String uuid = player.getUniqueID().toString().replaceAll("-", "");
        if (username != null && !username.isEmpty())
        {
            String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
            try {
                Socket socket = new Socket("192.95.4.92", 20090);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                //System.out.println("Successfully Connected to server!");
                DataInputStream in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out.writeUTF("NOVACLIENT$PACKET GETCOSMETICS " + uuid);
                String line = "";
                line = in.readUTF();
                System.out.println(line);
                Solar.isOnline = true;
                if (!line.equals("none")) {

                    //get cape
                }
            } catch (Exception e) {
                Solar.isOnline = false;
            }
            if (Minecraft.customCapes.containsKey(uuid)) ofCapeUrl = Minecraft.customCapes.get(uuid)[0];
            String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
            final ResourceLocation rl = new ResourceLocation("capeof/" + mptHash);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject tex = textureManager.getTexture(rl);

            if (tex != null && tex instanceof ThreadDownloadImageData)
            {
                ThreadDownloadImageData thePlayer = (ThreadDownloadImageData)tex;

                if (thePlayer.imageFound != null)
                {
                    if (thePlayer.imageFound.booleanValue())
                    {
                        player.setLocationOfCape(rl);
                    }

                    return;
                }
            }

            IImageBuffer iib = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage var1)
                {
                    return CapeUtils.parseCape(var1);
                }
                public void func_152634_a()
                {
                    player.setLocationOfCape(rl);
                }
            };
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, ofCapeUrl, (ResourceLocation)null, iib);
            textureCape.pipeline = true;
            textureManager.loadTexture(rl, textureCape);
        }
    }
    
    public static BufferedImage parseCape(BufferedImage img)
    {
    	
    	int scale = 1;
    	//if (img.getWidth() % 64 == 0)
    		//scale = img.getWidth() / 64;
        int imageWidth = 64 * scale;
        int imageHeight = 32 * scale;
        int srcWidth = img.getWidth();

        for (int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2)
        {
            imageWidth *= 2;
        }

        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, (ImageObserver)null);
        g.dispose();
        return imgNew;
    }
}
