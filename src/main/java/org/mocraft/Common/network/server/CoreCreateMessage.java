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
            player.closeScreen();

            ClientAok clientAok = ((ClientAok)player.getExtendedProperties(ClientAok.PROP_NAME));
            clientAok.setLandPos(landPos);
            NBTTagCompound compound = new NBTTagCompound();
            clientAok.saveNBTData(compound);
            PacketManager.sendTo(new SyncIEEPMessage(compound), player);

            /**
                UUID lord = UUID.fromString(message.data.getString("Lord"));
                String name = message.data.getString("Name");
                int x = message.data.getInteger("X");
                int y = message.data.getInteger("Y");
                int z = message.data.getInteger("Z");
                TileCore coreTile = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(x, y, z);
                coreTile.onCreate(lord, name);
                player.closeScreen();
                ClientAok.get(player).loadData(UUID.randomUUID(), lord, 0, name, 0, new UUID[] {lord}, x, y, z);
                System.out.println(message.data);
                PacketManager.sendTo(new SyncIEEPMessage(message.data), player);
             **/
            return null;
        }

    }
}
