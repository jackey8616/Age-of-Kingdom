package org.mocraft.Renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.mocraft.Model.ModelItemOperator;

/**
 * Created by Clode on 2016/10/29.
 */
@SideOnly(Side.CLIENT)
public class RendererItemOperator implements IItemRenderer {

    protected ModelItemOperator model;

    public RendererItemOperator() {
        model = new ModelItemOperator();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        switch(type) {
            case INVENTORY: { return true;}
            default: { return false;}
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("aok:textures/items/itemControl.png"));
        float scale = 1.1F;

        switch(type) {
            case EQUIPPED: {
                GL11.glScaled(scale, scale, scale);
                GL11.glRotated(178.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotated(320.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotated(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(-1.6F, -0.5F, -0.1F);
                model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            }
            case ENTITY: {
                GL11.glScaled(scale, scale, scale);
                GL11.glRotated(178.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslated(-1.6F, -1.2F, -0.1F);
                model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                //GL11.glScaled(scale, scale, scale);
                GL11.glScalef(2.8F, 2.8F, 2.8F);
                GL11.glRotated(178.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotated(340.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotated(20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(-0.1F, -0.3F, -0.2F);
                model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            }
            case INVENTORY: {
                GL11.glRotated(40.0F, -1.0F, 0.0F, 0.0F);
                GL11.glRotated(160.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotated(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(-0.9F, -0.4F, -0.1F);
                model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                break;
            }
            default: { break; }
        }

        GL11.glPopMatrix();
    }
}
