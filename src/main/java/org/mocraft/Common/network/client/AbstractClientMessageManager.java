package org.mocraft.Common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.mocraft.Common.network.common.AbstractMessageManager;

/**
 * Created by Clode on 2016/10/12.
 */
public abstract class AbstractClientMessageManager<T extends IMessage> extends AbstractMessageManager<T> {

    public final IMessage messageFromServer(EntityPlayer player, T message, MessageContext ctx) {
        return null;
    }

}
