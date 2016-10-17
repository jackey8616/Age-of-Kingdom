package org.mocraft.Common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Common.network.client.AbstractClientMessageManager;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/17.
 */
public class CoreCreateMessage implements IMessage {

    private NBTTagCompound data;

    public CoreCreateMessage() {  }

    public CoreCreateMessage(String name, UUID lord, int x, int y, int z) {
        this.data = new NBTTagCompound();
        this.data.setString("Name", name);
        this.data.setString("Lord", lord.toString());
        this.data.setInteger("X", x);
        this.data.setInteger("Y", y);
        this.data.setInteger("Z", z);
    }

    @Override
    public void fromBytes(ByteBuf buf) { this.data = ByteBufUtils.readTag(buf); }

    @Override
    public void toBytes(ByteBuf buf) { ByteBufUtils.writeTag(buf, data); }

    public static class Handler extends AbstractClientMessageManager<CoreCreateMessage> {

        @Override
        public IMessage messageFromClient(EntityPlayer player, CoreCreateMessage message, MessageContext ctx) {
            NBTTagCompound data = message.data;
            UUID lord = UUID.fromString(data.getString("Lord"));
            String name = data.getString("Name");
            int x = data.getInteger("X");
            int y = data.getInteger("Y");
            int z = data.getInteger("Z");
            TileCore coreTile = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(x, y, z);
            coreTile.onCreate(lord, name);
            player.closeScreen();
            //AgeOfKingdom.channel.sendTo(new SyncIEEPMessage(player), player);

            return null;
        }

    }
}
