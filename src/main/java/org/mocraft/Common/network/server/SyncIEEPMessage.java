package org.mocraft.Common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.mocraft.Common.ClientAok;
import org.mocraft.Common.network.client.ServerMessageManager;

/**
 * Created by Clode on 2016/10/12.
 */
public class SyncIEEPMessage implements IMessage {

    private NBTTagCompound data;

    public SyncIEEPMessage() {  }

    public SyncIEEPMessage(EntityPlayer player) {
        data = new NBTTagCompound();
        ClientAok.get(player).saveNBTData(data);
    }

    public SyncIEEPMessage(NBTTagCompound compound) {
        data = compound;
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
            System.out.println("Client recieve Server");

            if(player.getExtendedProperties(ClientAok.PROP_NAME) != null) {
                ClientAok.get(player).loadNBTData(message.data);
            } else {
                player.registerExtendedProperties(ClientAok.PROP_NAME, new ClientAok(message.data, player));
            }
            return null;
        }
    }
}