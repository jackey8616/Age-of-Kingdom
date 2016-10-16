package org.mocraft.Gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.mocraft.Block.TileCore;

/**
 * Created by Clode on 2016/10/11.
 */
public class ContainerCore extends Container {

    private TileCore tile;

    public ContainerCore(TileCore tileCore) {
        this.tile = tileCore;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) { return true; }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        this.tile.setUsing(false);
    }
}
