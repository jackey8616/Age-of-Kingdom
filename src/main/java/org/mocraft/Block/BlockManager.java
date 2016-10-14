package org.mocraft.Block;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Clode on 2016/10/10.
 */
public class BlockManager {

    public static Block blockCore;

    public BlockManager() {}

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerBlock(blockCore = new BlockCore("blockCore", Material.rock), "blockCore");
        GameRegistry.registerTileEntity(TileCore.class, "tileCore");
    }

}
