package org.mocraft.Gui;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.mocraft.Gui.vanilla.GuiAokScreen;

/**
 * Created by Clode on 2016/11/8.
 */
public class GuiMainScreen extends GuiAokScreen {

    private static int landNameTick = 0;
    private static String lastTickLandName = "null", landName = "null";

    private static Minecraft mc;

    public GuiMainScreen() {
        this.mc = Minecraft.getMinecraft();
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        if(e.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        renderLandNameGradient();
    }

    private void renderLandNameGradient() {
        //if(landName.equals(lastTickLandName)) { return; }
        //if(landNameTick == 0) { lastTickLandName = landName; }
        this.mc.fontRenderer.drawStringWithShadow("ASDASD", 10, 10, 0xffffffff);
        //landNameTick--;
    }

    public static void updateLandName(String lN) {
        landName = lN;
        landNameTick = 160;
    }

}
