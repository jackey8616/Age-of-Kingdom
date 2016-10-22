package org.mocraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.mocraft.Gui.GuiMember;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Common.ClientAok;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.Gui.GuiAok;
import org.mocraft.Gui.GuiCore;
import org.mocraft.Gui.GuiCoreNoCreated;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/10.
 */
public class ProxyServer implements IGuiHandler {

    public static final int GUI_AOK = 1;
    public static final int GUI_CORE = 2;
    public static final int GUI_CORE_NO_CREATED = 3;
    public static final int GUI_MEMBER = 4;

    public static final Map<UUID, NBTTagCompound> playerData = new HashMap<UUID, NBTTagCompound>();
    public static final ArrayList<BlockPos> corePos = new ArrayList<BlockPos>();

    public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }

    public NBTTagCompound getPlayerClientCore(EntityPlayer player) { return playerData.get(player.getUniqueID()); }

    public void setPlayerClientCore(UUID uuid, NBTTagCompound compound) { playerData.put(uuid, compound); }

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
            case GUI_CORE_NO_CREATED :
            case GUI_MEMBER : {
                BlockPos pos = ClientAok.get(player).getLandPos();
                TileCore tile = (TileCore) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
                return new ContainerCore(tile); }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AOK : return new GuiAok(player);
            case GUI_CORE : return new GuiCore(((TileCore)world.getTileEntity(x, y, z)), player);
            case GUI_CORE_NO_CREATED : return new GuiCoreNoCreated((TileCore)world.getTileEntity(x, y, z), player);
            case GUI_MEMBER : {
                BlockPos pos = ClientAok.get(player).getLandPos();
                TileCore tile = (TileCore) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
                return new GuiMember(tile, player);
            }
        }
        return null;
    }

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

}
