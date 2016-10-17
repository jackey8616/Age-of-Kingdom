package org.mocraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Common.ClientCore;
import org.mocraft.Common.ServerCore;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.Gui.GuiAok;
import org.mocraft.Gui.GuiCore;
import org.mocraft.Gui.GuiCoreNoCreated;

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

    private static final Map<UUID, NBTTagCompound> playerData = new HashMap<UUID, NBTTagCompound>();
    private static final Map<UUID, ServerCore> coreData = new HashMap<UUID, ServerCore>();

    public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
    public NBTTagCompound getPlayerClientCore(EntityPlayer player) { return playerData.get(player.getUniqueID()); }
    public void setPlayerClientCore(UUID uuid, NBTTagCompound compound) { playerData.put(uuid, compound); }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_AOK: return null;
            case GUI_CORE:
            case GUI_CORE_NO_CREATED: return new ContainerCore((TileCore)world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_AOK: return new GuiAok(player, ClientCore.get(player));
            case GUI_CORE: return new GuiCore(((TileCore)world.getTileEntity(x, y, z)));
            case GUI_CORE_NO_CREATED: return new GuiCoreNoCreated((TileCore)world.getTileEntity(x, y, z), player);
        }
        return null;
    }

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

}
