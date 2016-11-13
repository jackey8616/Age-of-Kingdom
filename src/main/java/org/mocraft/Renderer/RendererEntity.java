package org.mocraft.Renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Clode on 2016/11/11.
 */
public class RendererEntity extends RenderLiving {

    public RendererEntity(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityLiving, double d, double d1, double d2, float f, float f1) {
        super.doRender((Entity)entityLiving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) { return new ResourceLocation("/mob/example.png"); }
}