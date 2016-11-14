package org.mocraft.Network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.mocraft.Network.NetworkManager;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.PlayerAokIEEP;
import org.mocraft.Utils.Util;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/17.
 */
public class CoreCreateMessage implements IMessage {

    private NBTTagCompound  data = new NBTTagCompound();

    public CoreCreateMessage() {  }

    public CoreCreateMessage(String aokName, UUID lord, BlockPos blockPos) {
        this.data.setString("Lord", lord.toString());
        this.data.setString("AokName", aokName);
        blockPos.saveNBTData(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf) { this.data = ByteBufUtils.readTag(buf); }

    @Override
    public void toBytes(ByteBuf buf) { ByteBufUtils.writeTag(buf, data); }

    public static class Handler extends ClientMessageManager<CoreCreateMessage> {

        @Override
        public IMessage messageFromClient(EntityPlayer player, CoreCreateMessage message, MessageContext ctx) {
            BlockPos landPos = new BlockPos(message.data);
            TileCore core = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ());
            core.setAokName(message.data.getString("AokName"));
            core.setLord(UUID.fromString(message.data.getString("Lord")));
            core.addMember(core.getLord());
            core.insertToClientAok(PlayerAokIEEP.get(player));
            core.spawnNPC(MinecraftServer.getServer().getEntityWorld());
            player.closeScreen();
            player.experienceLevel -= Util.CREATE_AOK_MIN_LEVEL;

            NetworkManager.sendTo(new SyncIEEPMessage(player), player);
            return null;
        }

    }
}
