package org.mocraft.Common;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.mocraft.Entity.EntityQuester;
import org.mocraft.Model.ModelQuester;
import org.mocraft.Renderer.RendererEntity;

/**
 * Created by Clode on 2016/11/11.
 */
public class EntityManager {

    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerGlobalEntityID(EntityQuester.class, "Quester", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x000000);
    }

    @SideOnly(Side.CLIENT)
    public static void entityRenderRegister() {
        RenderingRegistry.registerEntityRenderingHandler(EntityQuester.class, new RendererEntity(new ModelQuester(), 0.5F));
    }

}
