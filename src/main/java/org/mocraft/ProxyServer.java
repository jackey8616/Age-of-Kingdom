package org.mocraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.mocraft.Common.ClientAok;
import org.mocraft.Common.EventManager;
import org.mocraft.Gui.*;
import org.mocraft.Gui.vanilla.GuiAokChat;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

import java.util.*;

/**
 * Created by Clode on 2016/10/10.
 */
public class ProxyServer implements IGuiHandler {

    public static final int GUI_AOK = 1;
    public static final int GUI_CORE = 2;
    public static final int GUI_CORE_NO_CREATED = 3;
    public static final int GUI_MEMBER = 4;
    public static final int GUI_INVITATION = 5;
    public static final int GUI_CHAT = 6;
    public static final int GUI_ADMIN = 7;

    public static final Map<UUID, NBTTagCompound> playerData = new HashMap<UUID, NBTTagCompound>();
    public static final ArrayList<BlockPos> corePos = new ArrayList<BlockPos>();

    @SideOnly(Side.SERVER)
    public TileCore getClosestTileCoreInField(EntityPlayer player) {
        BlockPos point = new BlockPos(player.posX, player.posY, player.posZ);
        for(BlockPos pos : corePos) {
            if(pos.compareSquareRange(point, Util.LAND_FIELD_RADIUS))
                return (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
        }
        return null;
    }

    @SideOnly(Side.SERVER)
    public TileCore getClosestTileCore(EntityPlayer player) {
        BlockPos point = new BlockPos(player.posX, player.posY, player.posZ);
        double[] distance = new double[corePos.size()];
        int min = 0;
        for(int i = 0; i < corePos.size(); ++i) {
            distance[i] = corePos.get(i).caculateDistance(point);
            min = distance[i] < distance[min] ? i : min;
        }
        return (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(corePos.get(min).getX(), corePos.get(min).getY(), corePos.get(min).getZ());
    }

    public static void addCorePos(BlockPos pos) {
        for(int i = 0; i < corePos.size(); ++i) {
            BlockPos p = corePos.get(i);
            if(p.equals(pos)) { return; }
        }
        corePos.add(pos);
        //EventManager.serverDataStorage.markDirty(); 10/26 12:36:47 Server mark NullPointerException : during world data loading.
    }

    public static void removeCorePos(BlockPos pos) {
        for(int i = 0; i < corePos.size(); ++i) {
            BlockPos p = corePos.get(i);
            if(p.equals(pos)) {
                corePos.remove(i);
                EventManager.serverDataStorage.markDirty();
            }
        }
    }

    public static boolean containsCorePos(BlockPos pos) {
        for(int i = 0; i < corePos.size(); ++i) {
            BlockPos p = corePos.get(i);
            if(p.equals(pos)) { return true; }
        }
        return false;
    }

    public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }

    public NBTTagCompound getPlayerClientCore(EntityPlayer player) { return playerData.get(player.getUniqueID()); }

    public void setPlayerClientCore(UUID uuid, NBTTagCompound compound) { playerData.put(uuid, compound); }

    @SideOnly(Side.SERVER)
    public EntityPlayer getPlayerByUuid(UUID uuid) {
        for(Object p : MinecraftServer.getServer().getEntityWorld().playerEntities) {
            EntityPlayer player = (EntityPlayer) p;
            if(player.getUniqueID().equals(uuid)) return player;
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch(ID) {
            case GUI_AOK : return null;
            case GUI_CORE :
            case GUI_CORE_NO_CREATED : return new ContainerCore((TileCore) world.getTileEntity(x, y, z));
            case GUI_MEMBER : {
                BlockPos pos = ClientAok.get(player).getLandPos();
                TileCore tile = (TileCore) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
                return new ContainerCore(tile);
            }
            case GUI_INVITATION:
            case GUI_CHAT:
            case GUI_ADMIN: return null;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case GUI_AOK : return new GuiAok(player);
            case GUI_CORE : return new GuiCore(((TileCore)world.getTileEntity(x, y, z)), player);
            case GUI_CORE_NO_CREATED : return new GuiCoreNoCreated((TileCore) world.getTileEntity(x, y, z), player);
            case GUI_MEMBER : {
                BlockPos pos = ClientAok.get(player).getLandPos();
                TileCore tile = (TileCore) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
                return new GuiMember(tile, player);
            }
            case GUI_INVITATION: return new GuiInvitation(player);
            case GUI_CHAT: return new GuiAokChat();
            case GUI_ADMIN: return new GuiAdmin();
        }
        return null;
    }

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

}
