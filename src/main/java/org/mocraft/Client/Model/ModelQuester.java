package org.mocraft.Client.Model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelQuester extends ModelBase {

    ModelRenderer head, body, rightarm, leftarm, rightleg, leftleg;
    ModelRenderer BOX02, BOX01;
    ModelRenderer x06, x05, x03, x02, x01;

    public ModelQuester() {

        textureWidth = 512;
        textureHeight = 512;

        head = new ModelRenderer(this, 50, 60);
        head.addBox(-4F, -8F, -4F, 16, 16, 16);
        head.setRotationPoint(0F, -34F, 0F);
        head.setTextureSize(512, 512);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body = new ModelRenderer(this, 50, 100);
        body.addBox(-8F, 0F, -4F, 16, 24, 8);
        body.setRotationPoint(4F, -26F, 4F);
        body.setTextureSize(512, 512);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        rightarm = new ModelRenderer(this, 0, 100);
        rightarm.addBox(-7F, -2F, -2F, 8, 24, 8);
        rightarm.setRotationPoint(-5F, -24F, 2F);
        rightarm.setTextureSize(512, 512);
        rightarm.mirror = true;
        setRotation(rightarm, -0.6981317F, 0F, 0F);
        leftarm = new ModelRenderer(this, 0, 100);
        leftarm.addBox(-1F, -2F, -2F, 8, 24, 8);
        leftarm.setRotationPoint(13F, -24F, 2F);
        leftarm.setTextureSize(512, 512);
        leftarm.mirror = true;
        setRotation(leftarm, -0.6981317F, 0F, 0F);
        rightleg = new ModelRenderer(this, 50, 140);
        rightleg.addBox(-2F, 0F, -2F, 8, 24, 8);
        rightleg.setRotationPoint(-2F, -2F, 2F);
        rightleg.setTextureSize(512, 512);
        rightleg.mirror = true;
        setRotation(rightleg, 0F, 0F, 0F);
        leftleg = new ModelRenderer(this, 50, 140);
        leftleg.addBox(-2F, 0F, -2F, 8, 24, 8);
        leftleg.setRotationPoint(6F, -2F, 2F);
        leftleg.setTextureSize(512, 512);
        leftleg.mirror = true;
        setRotation(leftleg, 0F, 0F, 0F);
        BOX02 = new ModelRenderer(this, 150, 40);
        BOX02.addBox(-8F, -8F, -8F, 16, 10, 16);
        BOX02.setRotationPoint(4F, -5F, -15F);
        BOX02.setTextureSize(512, 512);
        BOX02.mirror = true;
        setRotation(BOX02, 0F, 0F, 0F);
        BOX01 = new ModelRenderer(this, 150, 0);
        BOX01.addBox(-8F, -6F, -16F, 16, 6, 16);
        BOX01.setRotationPoint(4F, -13F, -7F);
        BOX01.setTextureSize(512, 512);
        BOX01.mirror = true;
        setRotation(BOX01, -0.4089647F, 0F, 0F);
        x06 = new ModelRenderer(this, 50, 0);
        x06.addBox(0F, 0F, 0F, 12, 8, 12);
        x06.setRotationPoint(-2F, -50F, -2F);
        x06.setTextureSize(512, 512);
        x06.mirror = true;
        setRotation(x06, 0F, 0F, 0F);
        x05 = new ModelRenderer(this, 50, 30);
        x05.addBox(0F, 0F, 0F, 22, 1, 22);
        x05.setRotationPoint(-7F, -42.53333F, -7F);
        x05.setTextureSize(512, 512);
        x05.mirror = true;
        setRotation(x05, 0F, 0F, 0F);
        x03 = new ModelRenderer(this, 250, 40);
        x03.addBox(-6F, -3F, -12F, 8, 8, 8);
        x03.setRotationPoint(8F, -55F, 12F);
        x03.setTextureSize(512, 512);
        x03.mirror = true;
        setRotation(x03, 0F, 0F, 0F);
        x02 = new ModelRenderer(this, 250, 20);
        x02.addBox(0F, 0F, 0F, 6, 4, 6);
        x02.setRotationPoint(0F, -62F, 0F);
        x02.setTextureSize(512, 512);
        x02.mirror = true;
        setRotation(x02, 0F, 0F, 0F);
        x01 = new ModelRenderer(this, 250, 0);
        x01.addBox(-3F, -2F, -6F, 6, 2, 6);
        x01.setRotationPoint(3F, -62F, 6F);
        x01.setTextureSize(512, 512);
        x01.mirror = true;
        setRotation(x01, -0.7435722F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("aok:textures/npc/entityBanker.png"));

        GL11.glPushMatrix();

        float scale = 0.5F;
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(0.0F, 2.0F, 0.0F);

        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        head.render(f5);
        body.render(f5);
        rightarm.render(f5);
        leftarm.render(f5);
        rightleg.render(f5);
        leftleg.render(f5);
        BOX02.render(f5);
        BOX01.render(f5);
        x06.render(f5);
        x05.render(f5);
        x03.render(f5);
        x02.render(f5);
        x01.render(f5);

        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
