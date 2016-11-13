package org.mocraft.Network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/24.
 */
public class GuiCoreMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public GuiCoreMessage() {}

    public GuiCoreMessage(EntityPlayer player, Type action) {
        data.setInteger("Action", action.getValue());
        switch(action) {
            case REQUEST_DISMISS: break;
            case SEND_QUIT: break;
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

    public static class Handler extends MessageManager<GuiCoreMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiCoreMessage message, MessageContext ctx) {
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiCoreMessage message, MessageContext ctx) {
            switch(Type.fromInteger(message.data.getInteger("Action"))) {
                case REQUEST_DISMISS: {
                    BlockPos landPos = ClientAok.get(player).getLandPos();
                    TileCore core = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ());
                    core.dismiss();
                    player.closeScreen();
                    break;
                }
                case SEND_QUIT: {
                    ClientAok clientAok = ClientAok.get(player);
                    BlockPos landPos = clientAok.getLandPos();
                    TileCore core = (TileCore) MinecraftServer.getServer().getEntityWorld().getTileEntity(landPos.getX(), landPos.getY(), landPos.getZ());
                    core.removeMember(player.getUniqueID());
                    core.syncToAll();
                    clientAok.clearAok();
                    player.closeScreen();
                    AgeOfKingdom.channel.sendTo(new SyncIEEPMessage(player), (EntityPlayerMP) player);
                    break;
                }
            }
            return null;
        }
    }

    public enum Type {

        REQUEST_DISMISS(0), SEND_QUIT(1);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() { return this.value; }

        public static Type fromInteger(int x) {
            switch(x) {
                case 0: return REQUEST_DISMISS;
                case 1: return SEND_QUIT;
            }
            return null;
        }
    }

}
