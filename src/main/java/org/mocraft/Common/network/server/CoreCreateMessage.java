package org.mocraft.Common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.mocraft.Common.ClientAok;
import org.mocraft.Common.network.PacketManager;
import org.mocraft.Common.network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/17.
 */
public class CoreCreateMessage implements IMessage {

    private NBTTagCompound  data;

    public CoreCreateMessage() {  }

    public CoreCreateMessage(String name, UUID lord, BlockPos blockPos) {
        this.data = new NBTTagCompound();
        this.data.setString("Lord", lord.toString());
        this.data.setString("Name", name);
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
            core.setName(message.data.getString("Name"));
            core.setLord(UUID.fromString(message.data.getString("Lord")));
            core.addMembers(core.getLord());
            core.insertToClientAok(ClientAok.get(player));
            player.closeScreen();

            PacketManager.sendTo(new SyncIEEPMessage(player), player);
            return null;
        }

    }
}
