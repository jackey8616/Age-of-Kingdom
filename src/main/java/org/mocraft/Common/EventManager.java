package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.mocraft.AgeOfKingdom;
import org.mocraft.TileEntity.TileCore;

/**
 * Created by Clode on 2016/10/21.
 */
public class EventManager {

    public static ServerDataStorage serverDataStorage;

    public EventManager() {}

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onEntityDamage(LivingHurtEvent e) {
        if(e.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer    attacker = (EntityPlayer) e.source.getEntity();
            ClientAok       attackerAok = ClientAok.get(attacker);
            TileCore nearByCore = AgeOfKingdom.serverProxy.getClosestTileCore(attacker);
            if(nearByCore == null || nearByCore.getAokName().equals("null") || nearByCore.getAokName().equals(attackerAok.getAokName())) {
                return;
            }
            attacker.addChatComponentMessage(new ChatComponentText("You don't have permission to launch attack."));
            e.setCanceled(true);
        }
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onBlockPlaceEvent(PlayerInteractEvent e) {
        if(e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) { return; }
        ClientAok clientAok = ClientAok.get(e.entityPlayer);
        TileCore nearByCore = AgeOfKingdom.serverProxy.getClosestTileCore(e.entityPlayer);
        if(nearByCore == null || nearByCore.getAokName().equals("null") || nearByCore.getAokName().equals(clientAok.getAokName())) { return; }
        e.entityPlayer.addChatComponentMessage(new ChatComponentText("You don't have permission to build in this aok."));
        e.setCanceled(true);

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

    @SubscribeEvent
    public void worldLoad(WorldEvent.Load event) {
        serverDataStorage = ServerDataStorage.get(event.world);
    }

    @SubscribeEvent
    public void worldSave(WorldEvent.Save event) {
        this.serverDataStorage.markDirty();
    }
}
