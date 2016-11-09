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
import org.mocraft.Gui.GuiMainScreen;
import org.mocraft.Network.PacketManager;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.MainScreenAction;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/19.
 */
public class GuiMainScreenMessage implements IMessage {

    private NBTTagCompound data = new NBTTagCompound();

    public GuiMainScreenMessage() {  }

    public GuiMainScreenMessage(MainScreenAction action, String landName) {
        data.setInteger("MainScreenAction", action.getValue());
        switch(action) {
            case SEND_LAND_NAME: {
                data.setString("LandName", landName);
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

    public static class Handler extends MessageManager<GuiMainScreenMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiMainScreenMessage message, MessageContext ctx) {
            switch(MainScreenAction.fromInteger(message.data.getInteger("MainScreenAction"))) {
                case SEND_LAND_NAME: {
                    AgeOfKingdom.clientProxy.guiMainScreen.updateLandName(message.data.getString("LandName"));
                    break;
                }
            }
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiMainScreenMessage message, MessageContext ctx) {
            return null;
        }
    }
}
