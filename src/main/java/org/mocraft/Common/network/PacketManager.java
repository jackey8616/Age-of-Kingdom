package org.mocraft.Common.network;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.network.common.SyncIEEPMessage;
import org.mocraft.Common.network.server.CoreCreateMessage;
import tv.twitch.Core;

/**
 * Created by Clode on 2016/10/11.
 */
public class PacketManager {

    private static byte packetId = 0;

    public void preInit(FMLPreInitializationEvent event) {
        AgeOfKingdom.channel.registerMessage(SyncIEEPMessage.Handler.class, SyncIEEPMessage.class, packetId++, Side.CLIENT);
        AgeOfKingdom.channel.registerMessage(SyncIEEPMessage.Handler.class, SyncIEEPMessage.class, packetId++, Side.SERVER);
        AgeOfKingdom.channel.registerMessage(CoreCreateMessage.Handler.class, CoreCreateMessage.class, packetId++, Side.SERVER);
    }

    public static final void sendTo(IMessage message, EntityPlayerMP player) {
        AgeOfKingdom.channel.sendTo(message, player);
    }

    public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        AgeOfKingdom.channel.sendToAllAround(message, point);
    }

    public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        AgeOfKingdom.channel.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    public static final void sendToServer(IMessage message) {
        AgeOfKingdom.channel.sendToServer(message);
    }

}
