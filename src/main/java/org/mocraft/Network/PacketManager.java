package org.mocraft.Network;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Network.common.GuiAokMessage;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.Network.common.GuiCoreMessage;
import org.mocraft.Network.common.GuiMemberMessage;
import org.mocraft.Network.server.CoreCreateMessage;

/**
 * Created by Clode on 2016/10/11.
 */
public class PacketManager {

    private static byte packetId = 0;

    public void preInit(FMLPreInitializationEvent event) {
        AgeOfKingdom.channel.registerMessage(SyncIEEPMessage.Handler.class, SyncIEEPMessage.class, packetId++, Side.CLIENT);
        AgeOfKingdom.channel.registerMessage(CoreCreateMessage.Handler.class, CoreCreateMessage.class, packetId++, Side.SERVER);
        AgeOfKingdom.channel.registerMessage(GuiAokMessage.Handler.class, GuiAokMessage.class, packetId++, Side.CLIENT);
        AgeOfKingdom.channel.registerMessage(GuiAokMessage.Handler.class, GuiAokMessage.class, packetId++, Side.SERVER);
        AgeOfKingdom.channel.registerMessage(GuiMemberMessage.Handler.class, GuiMemberMessage.class, packetId++, Side.CLIENT);
        AgeOfKingdom.channel.registerMessage(GuiMemberMessage.Handler.class, GuiMemberMessage.class, packetId++, Side.SERVER);
        AgeOfKingdom.channel.registerMessage(GuiCoreMessage.Handler.class, GuiCoreMessage.class, packetId++, Side.CLIENT);
        AgeOfKingdom.channel.registerMessage(GuiCoreMessage.Handler.class, GuiCoreMessage.class, packetId++, Side.SERVER);
    }

    private void regsiter(Class handler, Class glass, Side side) {
        AgeOfKingdom.channel.registerMessage(handler, glass, packetId++, side);
    }

    public static final void sendTo(IMessage message, EntityPlayer player) {
        for(Object object : MinecraftServer.getServer().getEntityWorld().playerEntities) {
            if(((EntityPlayer) object).getUniqueID().equals(player.getUniqueID())) {
                AgeOfKingdom.channel.sendTo(message, (EntityPlayerMP) object);
                return;
            }
        }
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
