package org.mocraft.Common.network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.mocraft.Common.ClientCore;

/**
 * Created by Clode on 2016/10/12.
 */
public class SyncIEEPMessage implements IMessage {

    private NBTTagCompound data;

    public SyncIEEPMessage() {  }

    public SyncIEEPMessage(EntityPlayer player) {
        data = new NBTTagCompound();
        ClientCore.get(player).saveNBTData(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler extends AbstractMessageManager<SyncIEEPMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, SyncIEEPMessage message, MessageContext ctx) {
            System.out.println("Client recieve Server");
            System.out.println(message.data);
            player.registerExtendedProperties(ClientCore.PROP_NAME, new ClientCore(message.data));
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, SyncIEEPMessage message, MessageContext ctx) {
            System.out.println("Server Recieved Client");
            System.out.println(message.data);
            return null;
        }
    }
}
