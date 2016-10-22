package org.mocraft.Network.common;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.mocraft.Common.ClientAok;
import org.mocraft.TileEntity.TileCore;

import java.util.ArrayList;

/**
 * Created by Clode on 2016/10/22.
 */
public class GuiMemberMessage implements IMessage {

    private NBTTagCompound data;

    public GuiMemberMessage() {  }

    public GuiMemberMessage(TileCore core) {
        data.setInteger("Action", ACTION.SEND_MEMBER.getValue());
        ArrayList<String> membersName = core.toStringArrayList();
        NBTTagList list = new NBTTagList();
        for(String member : membersName) {
            list.appendTag(new NBTTagString(member));
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

    public static class Handler extends MessageManager<GuiMemberMessage> {

        @Override
        public IMessage messageFromServer(EntityPlayer player, GuiMemberMessage message, MessageContext ctx) {
            switch(ACTION.fromInteger(message.data.getInteger("Action"))) {
                case SEND_MEMBER: {
                    ClientAok clientAok = ClientAok.get(player);
                    NBTTagList list = (NBTTagList) message.data.getTag("Members");
                    for (int i = 0; i < list.tagCount(); ++i) {
                        clientAok.addMember(list.getStringTagAt(i));
                    }
                    break;
                }
            }
            return null;
        }

        @Override
        public IMessage messageFromClient(EntityPlayer player, GuiMemberMessage message, MessageContext ctx) {
            return null;
        }
    }


}
enum ACTION {
    SEND_MEMBER(0);
    private int value;

    private ACTION(int value) { this.value = value; }

    public int getValue() { return this.value; }

    public static ACTION fromInteger(int x) {
        switch(x) {
            case 0:
                return SEND_MEMBER;
        }
        return null;
    }
}