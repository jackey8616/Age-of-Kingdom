package org.mocraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Client.Gui.GuiMainScreen;
import org.mocraft.Client.KeyManager;
import org.mocraft.Entity.EntityManager;
import org.mocraft.Item.ItemManager;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/10.
 */
public class ProxyClient extends ProxyServer {

    public KeyManager keyManager = new KeyManager();
    public GuiMainScreen guiMainScreen = new GuiMainScreen();

    @Override
    public EntityPlayer getPlayerByUuid(UUID uuid) {
        for(Object p : Minecraft.getMinecraft().theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) p;
            if(player.getUniqueID().equals(uuid)) return player;
        }
        return null;
    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) { return Minecraft.getMinecraft().thePlayer; }

    @Override
    public void preInit(FMLPreInitializationEvent event) {  }

    @Override
    public void init(FMLInitializationEvent event) {
        ItemManager.item3DRendererRegister();
        keyManager.init(event);
        guiMainScreen.init(event);
        EntityManager.entityRenderRegister();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {  }

}
