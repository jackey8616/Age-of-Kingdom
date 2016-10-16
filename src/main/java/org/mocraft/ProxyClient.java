package org.mocraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Common.KeyManager;

/**
 * Created by Clode on 2016/10/10.
 */
public class ProxyClient extends ProxyServer {

    public KeyManager keyManager = new KeyManager();

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) { return Minecraft.getMinecraft().thePlayer; }

    @Override
    public void preInit(FMLPreInitializationEvent event) {}

    @Override
    public void init(FMLInitializationEvent event) {
        keyManager.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {}

}
