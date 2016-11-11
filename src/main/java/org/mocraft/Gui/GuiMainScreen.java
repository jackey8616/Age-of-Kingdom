package org.mocraft.Gui;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.mocraft.Gui.vanilla.GuiAokScreen;

/**
 * Created by Clode on 2016/11/8.
 */
@SideOnly(Side.CLIENT)
public class GuiMainScreen extends GuiAokScreen {

    private static int landNameTick = 0;
    private static String landName;

    private Minecraft mc;
    private ScaledResolution scale;
    private int width, height;

    public GuiMainScreen() {
        this.mc = Minecraft.getMinecraft();
        this.landName = "null";
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        if(e.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        this.scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        this.width = this.scale.getScaledWidth();
        this.height = this.scale.getScaledHeight();

        renderLandNameGradient();
        renderLandName();
    }

    private void renderLandNameGradient() {
        if(landNameTick == 0) {
            return;
        }
        GL11.glScaled(2.0f, 2.0f, 1.0f);
        drawCenteredString(mc.fontRenderer, this.landName, this.width / 4, this.height / 4, 0xffffff);
        GL11.glScaled(0.0f, 0.0f, 0.0f);
        landNameTick--;
    }

    private void renderLandName() {
        mc.fontRenderer.drawString(this.landName, 10, 10, 0xffffff);
    }

    public void updateLandName(String lN) {
        if(!this.landName.equals(lN)) {
            this.landName = lN;
            this.landNameTick = 80;
        }
    }

}
