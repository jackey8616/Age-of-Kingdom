package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Network.common.GuiChatMessage;
import org.mocraft.Network.common.GuiMainScreenMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.Util;

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

    @SubscribeEvent
    public void serverRecievedChat(ServerChatEvent e) {
        e.setCanceled(true);
        EntityPlayer player = e.player;
        for(Object obj : MinecraftServer.getServer().getEntityWorld().playerEntities) {
            if(((EntityPlayer) obj).getDistanceToEntity(player) <= Util.CHAT_MIN_RANGE && !e.message.contains("achievement")) {
                AgeOfKingdom.channel.sendTo(new GuiChatMessage(e.message, player.getDisplayName(), GuiChatMessage.Type.TO_CLIENT), (EntityPlayerMP) obj);
            }
        }
    }

    @SubscribeEvent
    public void clientReceivedChat(ClientChatReceivedEvent e) {
        ChatComponentTranslation a = (ChatComponentTranslation)e.message;
        for(String disable : Util.CHAT_DISABLE_KEYS) {
            if(a.getKey().contains(disable)) {
                e.setCanceled(true);
                return;
            }
        }
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onEntityDamage(LivingHurtEvent e) {
        if(e.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer    attacker = (EntityPlayer) e.source.getEntity();
            ClientAok       attackerAok = ClientAok.get(attacker);
            TileCore nearByCore = AgeOfKingdom.serverProxy.getClosestTileCoreInField(attacker);
            if(nearByCore == null || nearByCore.getAokName().equals("null") || nearByCore.getAokName().equals(attackerAok.getAokName())) {
                return;
            }
            attacker.addChatComponentMessage(new ChatComponentText( StatCollector.translateToLocal("event.onEntityDamage.NoPermission")));
            e.setCanceled(true);
        }
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onBlockPlaceEvent(PlayerInteractEvent e) {
        if(e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) { return; }
        ClientAok clientAok = ClientAok.get(e.entityPlayer);
        TileCore nearByCore = AgeOfKingdom.serverProxy.getClosestTileCoreInField(e.entityPlayer);
        if(nearByCore == null || nearByCore.getAokName().equals("null") || nearByCore.getAokName().equals(clientAok.getAokName())) { return; }
        e.entityPlayer.addChatComponentMessage(new ChatComponentText( StatCollector.translateToLocal("event.onBlockPlaceEvent.NoPermission")));
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

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerUpdateEvent(LivingEvent.LivingUpdateEvent e) {
        if(!(e.entity instanceof EntityPlayer)) { return; }
        EntityPlayer player = (EntityPlayer) e.entity;
        if(player.lastTickPosX != player.posX || player.lastTickPosZ != player.posZ) {
            TileCore tile = AgeOfKingdom.serverProxy.getClosestTileCore(player);
            String groundName = tile.getAokName().equals("null") ? "Non" : tile.getAokName();
            AgeOfKingdom.channel.sendTo(new GuiMainScreenMessage(GuiMainScreenMessage.Type.SEND_LAND_NAME, groundName), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void worldLoad(WorldEvent.Load event) {
        serverDataStorage = ServerDataStorage.get(event.world);
    }

    @SubscribeEvent
    public void worldSave(WorldEvent.Save event) {
        this.serverDataStorage.markDirty();
    }

    public void chatEvent(ClientChatReceivedEvent event) {
    }
}
