package org.mocraft.Network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Network.PacketManager;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/19.
 */
public class GuiAokMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public GuiAokMessage() {  }

    public GuiAokMessage(EntityPlayer player) {
        PacketManager.sendTo(new SyncIEEPMessage(player), (EntityPlayerMP) player);
    }

    public GuiAokMessage(EntityPlayer player, BlockPos blockPos) {
        TileCore core = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        blockPos.saveNBTData(data);
        data.setString("Lord", blockPos.equals(new BlockPos(0, 0, 0)) || core == null ? "null" : core.getLordName());
        data.setString("Name", blockPos.equals(new BlockPos(0, 0, 0)) || core == null ? "null" : core.getAokName());
        data.setInteger("Level", blockPos.equals(new BlockPos(0, 0, 0)) || core == null ? 0 : core.getAokLevel());
        NBTTagList list = new NBTTagList();
        for(UUID uuid : blockPos.equals(new BlockPos(0, 0, 0)) || core == null ? new ArrayList<UUID>() : core.getMembers()) {
            EntityPlayer member = AgeOfKingdom.serverProxy.getPlayerByUuid(uuid);
            if(member != null) {
                list.appendTag(new NBTTagString(member.getDisplayName()));
            }
        }
        data.setTag("Members", list);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class Handler extends MessageManager<GuiAokMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiAokMessage message, MessageContext ctx) {
            ClientAok clientAok = ClientAok.get(player);
            clientAok.loadNBTData(message.data);

            player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_AOK, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiAokMessage message, MessageContext ctx) {
            ClientAok aok = ClientAok.get(player);
            if(aok.getAokName().equals("null")) {
                PacketManager.sendTo(new GuiAokMessage(player), (EntityPlayerMP) player);
            } else {
                PacketManager.sendTo(new GuiAokMessage(player, aok.getLandPos()), (EntityPlayerMP) player);
            }
            return null;
        }
    }
}
