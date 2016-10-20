package org.mocraft.Common.network.common;

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
import org.mocraft.Common.network.PacketManager;
import org.mocraft.Common.network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

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
        BlockPos landPos = new BlockPos(core.xCoord, core.yCoord, core.zCoord);
        landPos.saveNBTData(data);
        data.setString("Lord", AgeOfKingdom.serverProxy.getPlayerByUuid(core.getLord()).getDisplayName());
        data.setString("Name", core.getName());
        data.setInteger("Level", core.getAokLevel());
        NBTTagList list = new NBTTagList();
        for(UUID uuid : core.getMembers()) {
            list.appendTag(new NBTTagString(uuid.toString()));
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
            clientAok.getLandPos().loadNBTData(message.data);
            clientAok.setLordName(message.data.getString("Lord"));
            clientAok.setAokName(message.data.getString("Name"));
            clientAok.setAokLevel(message.data.getInteger("Level"));
            NBTTagList list = (NBTTagList) message.data.getTag("Members");
            for(int i = 0; i < list.tagCount(); ++i) {
                clientAok.getMembers().add(UUID.fromString(list.getStringTagAt(i)));
            }

            player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_AOK, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiAokMessage message, MessageContext ctx) {
            ClientAok aok = ClientAok.get(player);
            PacketManager.sendTo(new GuiAokMessage(player, aok.getLandPos()), (EntityPlayerMP) player);
            return null;
        }
    }
}
