package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.mocraft.AgeOfKingdom;
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
        for(BlockPos pos : AgeOfKingdom.serverProxy.corePos) {
            if(pos.compareSquareRange(location, Util.LAND_MIN_DISTANCE)) {
                e.setCanceled(true);
                return;
            }
        }
        Item itemInHand = e.entityPlayer.getHeldItem().getItem();
        if(
           !( itemInHand.equals(Blocks.torch) &&
           itemInHand.equals(Items.bed) &&
           itemInHand.equals(Blocks.crafting_table) &&
           itemInHand.equals(Blocks.furnace) &&
           itemInHand instanceof ItemArmor &&
           itemInHand instanceof ItemAxe &&
           itemInHand instanceof ItemTool &&
           itemInHand instanceof ItemSword)) {
            e.setCanceled(true);
        }
        return;
    }
}
