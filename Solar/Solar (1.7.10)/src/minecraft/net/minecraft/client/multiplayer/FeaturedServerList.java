package net.minecraft.client.multiplayer;

import com.orange.plump.Solar.Solar;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FeaturedServerList
{
    private static final Logger logger = LogManager.getLogger();

    /** The Minecraft instance. */
    private final Minecraft mc;

    /** List of ServerData instances. */
    private final List servers = new ArrayList();
    private static final String __OBFID = "CL_00000891";

    public FeaturedServerList(Minecraft p_i1194_1_)
    {
        this.mc = p_i1194_1_;
        this.loadServerList();
    }

    /**
     * Loads a list of servers from servers.dat, by running ServerData.getServerDataFromNBTCompound on each NBT compound
     * found in the "servers" tag list.
     */
    public void loadServerList()
    {
        try
        {
            this.servers.clear();
            //Featured Servers
            try {
                Socket socket = new Socket("192.95.4.92", 20090);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                //System.out.println("Successfully Connected to server!");
                DataInputStream in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out.writeUTF("NOVACLIENT$PACKET GETFEATURED");
                String line = "";
                line = in.readUTF();
                //System.out.println(line);
                for (String server : line.split(",")) {
                    if (server.contains("!")) {
                        String[] parts = server.split("!");
                        this.servers.add(new ServerData(":star: " + parts[0], parts[1]));
                    }
                }

                Solar.isOnline = true;
            } catch (Exception e) {
                Solar.isOnline = false;
            }
        }
        catch (Exception var4)
        {
            logger.error("Couldn\'t load server list", var4);
        }
    }

    /**
     * Runs getNBTCompound on each ServerData instance, puts everything into a "servers" NBT list and writes it to
     * servers.dat.
     */
    public void saveServerList()
    {
        //
    }

    /**
     * Gets the ServerData instance stored for the given index in the list.
     */
    public ServerData getServerData(int p_78850_1_)
    {
        return (ServerData)this.servers.get(p_78850_1_);
    }

    /**
     * Removes the ServerData instance stored for the given index in the list.
     */
    public void removeServerData(int p_78851_1_)
    {
        this.servers.remove(p_78851_1_);
    }

    /**
     * Adds the given ServerData instance to the list.
     */
    public void addServerData(ServerData p_78849_1_)
    {
        this.servers.add(p_78849_1_);
    }

    /**
     * Counts the number of ServerData instances in the list.
     */
    public int countServers()
    {
        return this.servers.size();
    }

    /**
     * Takes two list indexes, and swaps their order around.
     */
    public void swapServers(int p_78857_1_, int p_78857_2_)
    {
        //
    }

    public void func_147413_a(int p_147413_1_, ServerData p_147413_2_)
    {
        //
    }

    public static void func_147414_b(ServerData p_147414_0_)
    {
        //
    }
}
