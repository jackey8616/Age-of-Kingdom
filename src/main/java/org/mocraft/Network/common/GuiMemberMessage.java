package org.mocraft.Network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Gui.GuiInvitation;
import org.mocraft.Gui.GuiMember;
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

    public GuiMemberMessage(EntityPlayer player, String invite, Action action) {
        data.setInteger("Action", action.getValue());
        switch(action) {
            case SEND_MEMBER: {
                BlockPos landPos = ClientAok.get(player).getLandPos();
                ArrayList<String> membersName = ((TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ())).toStringArrayList();
                NBTTagList list = new NBTTagList();
                for (String member : membersName) {
                    list.appendTag(new NBTTagString(member));
                }
                data.setTag("Members", list);
                break;
            }
            case REQUEST_OPEN_GUI: break;
            case RECIEVED_MEMBER: break;
            case INVITE_MEMBER: data.setString("InvitationTo", invite); break;
            case INVITATION_FROM: {
                EntityPlayer inviter = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(invite);
                BlockPos landPos = ClientAok.get(inviter).getLandPos();
                TileCore tile = (TileCore) inviter.getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ());
                data.setString("AokName", tile.getAokName());
                data.setInteger("AokLevel", tile.getAokLevel());
                data.setString("LordName", tile.getLordName());
                data.setInteger("LordLevel", tile.getLordLevel());
                landPos.saveNBTData(data);
                data.setString("InvitationFrom", invite);
                break;
            }
            case PLAYER_OFFLINE: break;
            case PLAYER_ACCEPT:
            case PLAYER_DENIED: data.setString("ReplyTo", invite); break;
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
                    AgeOfKingdom.channel.sendToServer(new GuiMemberMessage(player, null, Action.RECIEVED_MEMBER));
                    break;
                }
                case INVITATION_FROM: {
                    NBTTagCompound data = message.data;
                    String invitationFrom = message.data.getString("InvitationFrom");
                    GuiInvitation.insert(invitationFrom, data.getString("AokName"), data.getInteger("AokLelve"), data.getString("LordName"), data.getInteger("LordLevel"), new BlockPos(data));
                    player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_INVITATION, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
                    break;
                }
                case PLAYER_OFFLINE: GuiMember.announceMessage("PLAYER OFFLINE"); break;
                case PLAYER_ACCEPT:
                case PLAYER_DENIED: GuiMember.announceMessage(String.valueOf(message.data.getInteger("Action"))); break;
            }
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiMemberMessage message, MessageContext ctx) {
            switch(Action.fromInteger(message.data.getInteger("Action"))) {
                case REQUEST_OPEN_GUI:
                    AgeOfKingdom.channel.sendTo(new GuiMemberMessage(player, null, Action.SEND_MEMBER), (EntityPlayerMP) player);
                    break;
                case RECIEVED_MEMBER:
                    player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_MEMBER, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
                    break;
                case INVITE_MEMBER:
                    EntityPlayer invitationTo = player.getEntityWorld().getPlayerEntityByName(message.data.getString("InvitationTo"));
                    if(invitationTo == null) {
                        AgeOfKingdom.channel.sendTo(new GuiMemberMessage(player, null, Action.PLAYER_OFFLINE), (EntityPlayerMP) player);
                    } else {
                        AgeOfKingdom.channel.sendTo(new GuiMemberMessage(invitationTo, player.getDisplayName(), Action.INVITATION_FROM), (EntityPlayerMP) invitationTo);
                    }
                    break;
                case PLAYER_ACCEPT:
                    ClientAok clientInviter = ClientAok.get(MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(message.data.getString("ReplyTo")));
                    TileCore tile = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(clientInviter.getLandPos().getX(), clientInviter.getLandPos().getY(), clientInviter.getLandPos().getZ());
                    tile.addMember(player.getPersistentID());
                    tile.syncToAll();
                case PLAYER_DENIED:
                    EntityPlayer replyTo = player.getEntityWorld().getPlayerEntityByName(message.data.getString("ReplyTo"));
                    AgeOfKingdom.channel.sendTo(new GuiMemberMessage(replyTo, player.getDisplayName(), Action.fromInteger(message.data.getInteger("Action"))), (EntityPlayerMP) replyTo);
                    break;
            }
            return null;
        }
    }
}