package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.mocraft.Block.TileCore;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCore extends GuiContainer {

    private TileCore tile;

    public GuiCore(TileCore tile) {
        super(new ContainerCore(tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawString(fontRendererObj, tile.getName() + "X", 10, 10, 0xffffff);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }
}
