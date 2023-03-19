package net.minecraft.client.gui;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import com.orange.plump.Solar.Solar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;

public class GuiNovaConnecting extends GuiScreen {

	public String serverIP;
	public String state = "Connecting to servers...";
	private static final ResourceLocation eclipseTitle = new ResourceLocation("textures/solar/Solar.png");
	private static final ResourceLocation loading = new ResourceLocation("textures/solar/loading.png");
	public int tick = 0;
	
	private static final AtomicInteger field_146372_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager field_146371_g;
    private boolean field_146373_h;
    private final GuiScreen field_146374_i;
    private static final String __OBFID = "CL_00000685";
	
	public GuiNovaConnecting(GuiScreen p_i1181_1_, Minecraft p_i1181_2_, ServerData p_i1181_3_)
    {
		this.serverIP = p_i1181_3_.serverIP;
		this.mc = p_i1181_2_;
        this.field_146374_i = p_i1181_1_;
        final ServerAddress var4 = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
        p_i1181_2_.loadWorld((WorldClient)null);
        p_i1181_2_.setServerData(p_i1181_3_);
        new Thread() {
        	public void run() {
        		try {
        			sleep(200);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		try {
            		Socket socket = new Socket("192.95.4.92", 20090);
            		socket.setSoTimeout(10000);
        			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        			System.out.println("Successfully Connected to server!");
        			DataInputStream in = new DataInputStream( 
        			        new BufferedInputStream(socket.getInputStream()));
        			out.writeUTF("NOVACLIENT$PACKET JOINSERVER " + Minecraft.getMinecraft().getSession().getUsername() + ":" + Minecraft.getMinecraft().getSession().getPlayerID() + ":" + serverIP);
        		    Solar.isOnline = true;
        		} catch (Exception e) {
        			Solar.isOnline = false;

        		}
        		try {
        			sleep(200);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		state = "DONE";
        		func_146367_a(var4.getIP(), var4.getPort());
        	}
        }.start();
    }

	public void updateScreen()
    {
        if (this.field_146371_g != null)
        {
            if (this.field_146371_g.isChannelOpen())
            {
                this.field_146371_g.processReceivedPackets();
            }
            else if (this.field_146371_g.getExitMessage() != null)
            {
                this.field_146371_g.getNetHandler().onDisconnect(this.field_146371_g.getExitMessage());
            }
        }
    }
	
    public void initGui() {

    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
    	tick++;
    	if (!state.equals("DONE")) {
    	this.drawGradientRect(0, 0, this.width, this.height, new Color(195, 195, 195, 255).hashCode(), new Color(50, 50, 50, 255).hashCode());
    	this.drawBlurredBackgroundStationary();
    	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Tessellator var4 = Tessellator.instance;
        float scale = 0.35F;
        float mscale = (float)Math.pow(scale,-1);
        short var5 = 256;
        int var6 = this.width / 2;
        int var7 = this.height / 3;
        var6 = (int) (var6 / scale) - var5 / 2;
        var7 = (int) (var7 / scale) - var5 / 2;
        this.mc.getTextureManager().bindTexture(eclipseTitle);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(scale, scale, scale);
        this.drawTexturedModalRect(var6 + 8, var7 + 0, 0, 0, var5, var5);
        GL11.glScalef(mscale, mscale, mscale);        
        this.mc.getTextureManager().bindTexture(loading);
        scale = 0.25f;
        mscale = (float)Math.pow(scale,-1);
        var6 = (int) this.width / 2;
        var7 = (int) this.height - 50;
        var6 = (int) (var6 / scale);
        var7 = (int) (var7 / scale);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(var6, var7, 0);
        GL11.glRotatef(tick * 3, 0, 0, 1);
        this.drawTexturedModalRect(- var5 / 2, - var5 / 2, 0, 0, var5, var5);
        GL11.glTranslatef(var6 * -1, var7 * -1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glScalef(mscale, mscale, mscale);
//        scale = 1.25f;
//        mscale = (float)Math.pow(scale,-1);
//        var6 = (int) this.width / 2;
//        var7 = (int) this.height - 100;
//        var6 = (int) (var6 / scale);
//        var7 = (int) (var7 / scale);
//        GL11.glScalef(scale, scale, scale);
//        this.mc.cFontRenderer.drawCenteredString(state, var6, var7, Color.BLACK.hashCode());
        
    	} else {
    		this.drawDefaultBackground();

            if (this.field_146371_g == null)
            {
                this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
            }
            else
            {
                this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
            }

            super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    	}
        
    }
    
    private void func_146367_a(final String p_146367_1_, final int p_146367_2_)
    {
        if (Solar.isOnline) {
	        logger.info("Connecting to " + p_146367_1_ + ", " + p_146367_2_);
	        (new Thread("Server Connector #" + field_146372_a.incrementAndGet())
	        {
		        private static final String __OBFID = "CL_00000686";
		        public void run()
		        {
			        InetAddress var1 = null;

			        try
			        {
				        if (GuiNovaConnecting.this.field_146373_h)
				        {
					        return;
				        }

				        var1 = InetAddress.getByName(p_146367_1_);
				        GuiNovaConnecting.this.field_146371_g = NetworkManager.provideLanClient(var1, p_146367_2_);
				        GuiNovaConnecting.this.field_146371_g.setNetHandler(new NetHandlerLoginClient(GuiNovaConnecting.this.field_146371_g, GuiNovaConnecting.this.mc, GuiNovaConnecting.this.field_146374_i));
				        GuiNovaConnecting.this.field_146371_g.scheduleOutboundPacket(new C00Handshake(5, p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
				        GuiNovaConnecting.this.field_146371_g.scheduleOutboundPacket(new C00PacketLoginStart(GuiNovaConnecting.this.mc.getSession().func_148256_e()), new GenericFutureListener[0]);
			        }
			        catch (UnknownHostException var5)
			        {
				        if (GuiNovaConnecting.this.field_146373_h)
				        {
					        return;
				        }

				        GuiNovaConnecting.logger.error("Couldn\'t connect to server", var5);
				        GuiNovaConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiNovaConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
			        }
			        catch (Exception var6)
			        {
				        if (GuiNovaConnecting.this.field_146373_h)
				        {
					        return;
				        }

				        GuiNovaConnecting.logger.error("Couldn\'t connect to server", var6);
				        String var3 = var6.toString();

				        if (var1 != null)
				        {
					        String var4 = var1.toString() + ":" + p_146367_2_;
					        var3 = var3.replaceAll(var4, "");
				        }

				        GuiNovaConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiNovaConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {var3})));
			        }
		        }
	        }).start();
        } else {
	        GuiNovaConnecting.logger.error("Couldn\'t connect to server", new Exception());
	        GuiNovaConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiNovaConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Could not connect to Solar's Servers!"})));
        }
    }
}
