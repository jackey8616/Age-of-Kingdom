package org.mocraft.Item;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import org.mocraft.Renderer.RendererItemOperator;

/**
 * Created by Clode on 2016/10/29.
 */
public class ItemManager {

    public static Item itemOperator = new ItemOperator();

    public void init(FMLInitializationEvent e) {
        GameRegistry.registerItem(itemOperator, "itemOperator");
    }

    @SideOnly(Side.CLIENT)
    public static void item3DRendererRegister() {
        MinecraftForgeClient.registerItemRenderer(itemOperator, new RendererItemOperator());
    }

}
