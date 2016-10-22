package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.mocraft.AgeOfKingdom;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/21.
 */
public class EventManager {

    public EventManager() {}

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onBlockPlaceEvent(PlayerInteractEvent e) {
        if(e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) { return; }
        BlockPos location = new BlockPos(e.x, e.y, e.z);
        ClientAok clientAok = ClientAok.get(e.entityPlayer);
        for(BlockPos pos : AgeOfKingdom.serverProxy.corePos) {
            TileCore tile = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
            if(pos.compareSquareRange(location, Util.LAND_FIELD)) {
                e.setCanceled(!tile.getAokName().equals(clientAok.getAokName()));
                return;
            }
        }
        /**
        if(e.entityPlayer.getHeldItem() != null) {
            Item itemInHand = e.entityPlayer.getHeldItem().getItem();
            if (
                    !(itemInHand == ItemBlock.getItemFromBlock(Blocks.torch) ||
                            itemInHand.equals(Items.bed) ||
                            itemInHand == ItemBlock.getItemFromBlock(Blocks.crafting_table) ||
                            itemInHand == ItemBlock.getItemFromBlock(Blocks.furnace) ||
                            itemInHand instanceof ItemArmor ||
                            itemInHand instanceof ItemAxe ||
                            itemInHand instanceof ItemTool ||
                            itemInHand instanceof ItemSword ||
                            itemInHand == ItemBlock.getItemFromBlock(BlockManager.blockCore))) {
                e.setCanceled(true);
                return;
            }
        }
         **/
        return;
    }
}
