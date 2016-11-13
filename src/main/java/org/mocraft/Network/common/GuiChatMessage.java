package org.mocraft.Network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/25.
 */
public class GuiChatMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public GuiChatMessage() {}

    public GuiChatMessage(String message, Type action) {
        data.setInteger("Action", action.getValue());
        switch(action) {
            case TO_SERVER: {
                data.setString("Message", message);
                break;
            }
        }
    }

    public GuiChatMessage(String message, String speaker, Type action) {
        data.setInteger("Action", action.getValue());
        switch(action) {
            case TO_CLIENT: {
                data.setString("Speaker", speaker);
                data.setString("Message", message);
                break;
            }
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

    public static class Handler extends MessageManager<GuiChatMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiChatMessage message, MessageContext ctx) {
            switch (Type.fromInteger(message.data.getInteger("Action"))) {
                case TO_CLIENT: {
                    String msg = message.data.getString("Speaker") + " : ";
                    msg += message.data.getString("Message");
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(msg));
                    break;
                }
            }
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiChatMessage message, MessageContext ctx) {
            switch(Type.fromInteger(message.data.getInteger("Action"))) {
                case TO_SERVER: {
                    for(Object obj : MinecraftServer.getServer().getEntityWorld().playerEntities) {
                        if(((EntityPlayer) obj).getDistanceToEntity(player) <= Util.CHAT_MIN_RANGE) {
                            AgeOfKingdom.channel.sendTo(new GuiChatMessage(message.data.getString("Message"), player.getDisplayName(), Type.TO_CLIENT), (EntityPlayerMP) obj);
                        }
                    }
                    break;
                }
            }
            return null;
        }
    }

    public enum Type {

        TO_SERVER(0), TO_CLIENT(1);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() { return this.value; }

        public static Type fromInteger(int x) {
            switch(x) {
                case 0: return TO_SERVER;
                case 1: return TO_CLIENT;
            }
            return null;
        }
    }

}
