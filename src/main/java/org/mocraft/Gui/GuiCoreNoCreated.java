package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.mocraft.Block.TileCore;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCoreNoCreated extends GuiContainer {

    private TileCore tile;

    public GuiCoreNoCreated(TileCore tile) {
        super(new ContainerCore(tile));
        this.tile = tile;
    }

    @Override
    public void initGui() {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        this.drawString(fontRendererObj, "Create", 10, 10, 0xFFFFFF);
    }
}
