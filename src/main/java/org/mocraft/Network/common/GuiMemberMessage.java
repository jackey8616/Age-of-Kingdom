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
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.Action;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;

/**
 * Created by Clode on 2016/10/22.
 */
public class GuiMemberMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public GuiMemberMessage() {  }

    public GuiMemberMessage(EntityPlayer player, Action action) {
        data.setInteger("Action", action.getValue());
        BlockPos landPos = ClientAok.get(player).getLandPos();
        switch(action) {
            case SEND_MEMBER: {
                ArrayList<String> membersName = ((TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ())).toStringArrayList();
                NBTTagList list = new NBTTagList();
                for(String member : membersName) {
                    list.appendTag(new NBTTagString(member));
                }
                data.setTag("Members", list);
                break;
            }
            case REQUEST_OPEN_GUI:
            case RECIEVED_MEMBER:
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class Handler extends MessageManager<GuiMemberMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiMemberMessage message, MessageContext ctx) {
            switch(Action.fromInteger(message.data.getInteger("Action"))) {
                case SEND_MEMBER: {
                    ClientAok clientAok = ClientAok.get(player);
                    NBTTagList list = (NBTTagList) message.data.getTag("Members");
                    for (int i = 0; i < list.tagCount(); ++i) {
                        clientAok.addMember(list.getStringTagAt(i));
                    }
                    AgeOfKingdom.channel.sendToServer(new GuiMemberMessage(player, Action.RECIEVED_MEMBER));
                    break;
                }
            }
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiMemberMessage message, MessageContext ctx) {
            switch(Action.fromInteger(message.data.getInteger("Action"))) {
                case REQUEST_OPEN_GUI: {
                    AgeOfKingdom.channel.sendTo(new GuiMemberMessage(player, Action.SEND_MEMBER), (EntityPlayerMP) player);
                    break;
                }
                case RECIEVED_MEMBER: {
                    player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_MEMBER, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
                    break;
                }
            }
            return null;
        }
    }


}