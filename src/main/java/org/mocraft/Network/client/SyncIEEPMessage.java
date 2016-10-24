package org.mocraft.Network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.mocraft.Common.ClientAok;
import org.mocraft.ProxyServer;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/12.
 */
public class SyncIEEPMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public SyncIEEPMessage() {  }

    public SyncIEEPMessage(EntityPlayer player) {
        ClientAok clientAok = ClientAok.get(player);
        BlockPos pos = clientAok.getLandPos();
        if(ProxyServer.containsCorePos(pos)) {
            TileCore tile = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(pos.getX(), pos.getY(), pos.getZ());
            tile.insertToClientAok(clientAok);
        } else {
            clientAok.clearAok();
        }
        clientAok.saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler extends ServerMessageManager<SyncIEEPMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, SyncIEEPMessage message, MessageContext ctx) {
            ClientAok clientAok = ClientAok.get(player);
            clientAok.loadNBTData(message.data);
            return null;
        }
    }
}
