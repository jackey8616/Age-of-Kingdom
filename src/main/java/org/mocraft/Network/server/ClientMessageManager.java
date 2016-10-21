package org.mocraft.Network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Network.common.MessageManager;

/**
 * Created by Clode on 2016/10/12.
 */
public abstract class ClientMessageManager<T extends IMessage> extends MessageManager<T> {

    public final IMessage messageFromServer(EntityPlayer player, T message, MessageContext ctx) {
        return null;
    }

}
