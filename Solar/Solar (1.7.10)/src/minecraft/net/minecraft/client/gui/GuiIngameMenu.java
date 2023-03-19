package net.minecraft.client.gui;

import com.orange.plump.Solar.Solar;
import com.orange.plump.Solar.RadioThread;
import com.orange.plump.Solar.Station;
import com.orange.plump.Solar.gui.button.GuiCogButton;
import com.orange.plump.Solar.gui.button.GuiSlider;
import com.orange.plump.Solar.modules.RadioModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiIngameMenu extends GuiScreen
{
    private boolean stationOpen = false;
    private static GuiSlider volumeSlider;
    private List<com.orange.plump.Solar.gui.button.GuiButton> buttons;
    private GuiCogButton radioStations;
    private static final ResourceLocation play = new ResourceLocation("textures/solar/play.png"), pause = new ResourceLocation("textures/solar/pause.png"), volume = new ResourceLocation("textures/solar/volume.png");
    private int field_146445_a;
    private int field_146444_f;
    private static final String __OBFID = "CL_00000703";
    private int scroll = 0;


    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
        if (volumeSlider != null) volumeSlider.mouseDragged(p_146273_1_, p_146273_2_);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (volumeSlider != null) volumeSlider.mousePressed(mouseX, mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(com.orange.plump.Solar.gui.button.GuiButton button : buttons)
            if (mouseButton == 0) if (button.isOnButton(mouseX, mouseY, true)) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
                if (button.name.startsWith("playPause")) {
                    if (RadioThread.currentStation != null) {
                        if (RadioThread.isPlaying) RadioThread.pauseStation();
                        else RadioThread.playStation(RadioThread.currentStation);
                    }
                } else if (button.name.startsWith("play.")) {
                    stationOpen = false;
                    Station station = null;
                    for (Station s : Solar.stations) if (s.getId() == Integer.parseInt(button.name.replace("play.", ""))) {
                        station = s;
                        break;
                    }
                    RadioThread.playStation(station);
                }
            }
        if (radioStations != null) if (mouseButton == 0) if (radioStations.isOnButton(mouseX, mouseY, true)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 3.0F));
            stationOpen = !stationOpen;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        buttons = new ArrayList<com.orange.plump.Solar.gui.button.GuiButton>();
        this.field_146445_a = 0;
        this.buttonList.clear();
        byte var1 = -16;
        boolean var2 = true;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var1, I18n.format("menu.returnToMenu", new Object[0])));
        GuiButton var3 = null;
        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
            this.buttonList.add(new GuiButton(8, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, "Server List"));
        } else {
            this.buttonList.add(var3 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        }

        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var1, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.stats", new Object[0])));
        if (var3 != null) var3.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
        if (RadioModule.isToggled) {
            volumeSlider = new GuiSlider(width - 177 + 45 + 20, 67 - 30, 85, 10);
            radioStations = new GuiCogButton("stations", ((width - (15)) + 2) -7, 67 - 23 - 7, ((width - (15)) + 2) + 7, 67 - 23 + 7, 0x00000000, this);

        }
    }

    private com.orange.plump.Solar.gui.button.GuiButton getButton(String name) {
        for (com.orange.plump.Solar.gui.button.GuiButton butt : buttons)
            if (butt.name.equals(name))
                return butt;
        return null;
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                boolean wasServer = !this.mc.isIntegratedServerRunning();
                p_146284_1_.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);
                this.mc.displayGuiScreen(new GuiMainMenu());

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 8:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        if (stationOpen) for (; !this.mc.gameSettings.touchscreen && Mouse.next(); this.mc.currentScreen.handleMouseInput())
        {
            int var7 = Mouse.getEventDWheel();

            if (var7 != 0)
            {
                var7 = -var7;

                this.scroll += (float)(var7 / 5);
                if (this.scroll < 0) scroll = 0;
                if (this.scroll > (int) Math.ceil(Solar.stations.size() * 37) - 136) scroll = (int) (Math.ceil(Solar.stations.size() * 37) - 136);
            }
        }
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);

        if (RadioModule.isToggled) {
            RadioModule.drawRadio(this, p_73863_1_, p_73863_2_);

            ScaledResolution var3 = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            int width = var3.getScaledWidth();
            int height = var3.getScaledHeight();
            float scale = 0.175F;
            float mscale = (float) Math.pow(scale, -1);

            if (getButton("playPause") == null)
                buttons.add(new com.orange.plump.Solar.gui.button.GuiButton("playPause", "", ((width - (175 - 20)) + 2) - 10, (((65 / 2)) + 2) - 10, ((width - (175 - 20)) + 2) + 10, (((65 / 2)) + 2) + 10, 0, 0x00000000, 0xFFFFFF, this));

            int var6 = (int) (((width - (175 - 20)) + 2) / scale) - 256 / 2;
            int var7 = (int) ((((65 / 2)) + 2) / scale) - 256 / 2;

            if (RadioThread.isPlaying) this.mc.getTextureManager().bindTexture(pause);
            else this.mc.getTextureManager().bindTexture(play);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glScalef(scale, scale, scale);
            this.drawTexturedModalRect(var6, var7, 0, 0, 256, 256);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glScalef(mscale, mscale, mscale);

            scale = 0.050F;
            mscale = (float) Math.pow(scale, -1);
            var6 = (int) ((width - 177 + 53) / scale) - 256 / 2;
            var7 = (int) ((67 - 26) / scale) - 256 / 2;
            this.mc.getTextureManager().bindTexture(volume);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glScalef(scale, scale, scale);
            this.drawTexturedModalRect(var6, var7, 0, 0, 256, 256);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glScalef(mscale, mscale, mscale);

            radioStations.draw(p_73863_1_, p_73863_2_, true);
            volumeSlider.draw(this);


            if (stationOpen) {
                drawRoundedRect(width - 110, 69, width - 2, 225, 10, 0x80444444);

                drawRect(width - 100, 74, width - 12, 75, 0x44000000);
                drawRect(width - 100,  225 - 5, width - 12, 225 - 6, 0x44000000);
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                GL11.glScissor(0, this.mc.displayHeight - ((215) * var3.getScaleFactor() - 2), this.mc.displayWidth, ((136) * var3.getScaleFactor()));
                int row = 0;
                for (Station station : Solar.stations) {
                    int y = 79 + (37 * row) - scroll;
                    drawRoundedRect(width - 108, y, width - 4, y + 35, 10, 0x80444444);
                    mc.fontRenderer.setFancy(true);
                    drawCenteredString(mc.fontRenderer, station.getName() + " : " + station.getGenre(), width - 56, y + 4, 0xFFFFFF);
                    mc.fontRenderer.setFancy(false);
                    if (getButton("play." + station.getId()) == null)
                        buttons.add(new com.orange.plump.Solar.gui.button.GuiButton("play." + station.getId(), "", (width - 56) - 7, (y + 22) - 7, (width - 56) + 7, (y + 22) + 7, 0, 0x00000000, 0xFFFFFF, this));
                    else {
                        getButton("play." + station.getId()).setScroll(scroll);
                    }


                    scale = 0.10F;
                    mscale = (float) Math.pow(scale, -1);
                    var6 = (int) ((width - 56) / scale) - 256 / 2;
                    var7 = (int) ((y + 22) / scale) - 256 / 2;
                    this.mc.getTextureManager().bindTexture(play);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glScalef(scale, scale, scale);
                    this.drawTexturedModalRect(var6, var7, 0, 0, 256, 256);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glScalef(mscale, mscale, mscale);



                    row++;
                }
                for (com.orange.plump.Solar.gui.button.GuiButton button : buttons)
                    if (button.name.contains("play."))button.draw(p_73863_1_, p_73863_2_);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
        }
        for (com.orange.plump.Solar.gui.button.GuiButton button : buttons)
            if (!button.name.contains("play."))button.draw(p_73863_1_, p_73863_2_);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
