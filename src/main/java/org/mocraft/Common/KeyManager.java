package org.mocraft.Common;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Network.PacketManager;
import org.mocraft.Network.common.GuiAokMessage;

/**
 * Created by Clode on 2016/10/12.
 */
public class KeyManager {

    public KeyBinding[] keys = {
            new KeyBinding("AoK Interface", 37, "Age of Kingdom") // 'K'
    };

    public void init(FMLInitializationEvent e) {
        FMLCommonHandler.instance().bus().register(this);
        for(int i = 0; i < keys.length; ++i) {
            ClientRegistry.registerKeyBinding(keys[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        if(keys[0].isPressed()) {
            PacketManager.sendToServer(new GuiAokMessage());
        }
        if(mc.gameSettings.keyBindChat.isPressed()) {
            player.closeScreen();
            player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_CHAT, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

}
