package org.mocraft.Model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelItemOperator extends ModelBase {

    ModelRenderer Shape1,   Shape2,   Shape3,   Shape4,   Shape5,   Shape6,   Shape7,   Shape8,   Shape9,
            Shape11,  Shape12,  Shape13,  Shape14,  Shape15,  Shape16,  Shape22,  Shape23,

    Shape10L, Shape10R, Shape17L, Shape17R, Shape18L, Shape18R, Shape19L, Shape19R, Shape20L,
            Shape20R, Shape21L, Shape21R;

    public ModelItemOperator() {
        textureWidth = 512;
        textureHeight = 512;

        Shape23 = new ModelRenderer(this, 20, 0);
        Shape23.addBox(0F, 0F, 0F, 2, 2, 2);
        Shape23.setRotationPoint(14.5F, 19F, 0.4F);
        Shape23.setTextureSize(64, 32);
        Shape23.mirror = true;
        setRotation(Shape23, 0F, 0F, 0F);
        Shape8 = new ModelRenderer(this, 150, 48);
        Shape8.addBox(0F, 0F, 0F, 1, 4, 1);
        Shape8.setRotationPoint(18F, -1F, 0F);
        Shape8.setTextureSize(64, 32);
        Shape8.mirror = true;
        setRotation(Shape8, 0F, 0F, 0F);
        Shape7 = new ModelRenderer(this, 150, 46);
        Shape7.addBox(0F, 0F, 0F, 1, 5, 1);
        Shape7.setRotationPoint(17F, -1F, 0F);
        Shape7.setTextureSize(64, 32);
        Shape7.mirror = true;
        setRotation(Shape7, 0F, 0F, 0F);
        Shape6 = new ModelRenderer(this, 150, 44);
        Shape6.addBox(0F, 0F, 0F, 1, 5, 1);
        Shape6.setRotationPoint(16F, 0F, 0F);
        Shape6.setTextureSize(64, 32);
        Shape6.mirror = true;
        setRotation(Shape6, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 150, 42);
        Shape5.addBox(0F, 0F, 0F, 1, 5, 1);
        Shape5.setRotationPoint(15F, 1F, 0F);
        Shape5.setTextureSize(64, 32);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 150, 30);
        Shape4.addBox(0F, 0F, 0F, 1, 5, 1);
        Shape4.setRotationPoint(14F, 0F, 0F);
        Shape4.setTextureSize(64, 32);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 150, 19);
        Shape3.addBox(0F, 0F, 0F, 1, 5, 1);
        Shape3.setRotationPoint(13F, -1F, 0F);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 150, 8);
        Shape2.addBox(0F, 0F, 0F, 1, 4, 1);
        Shape2.setRotationPoint(12F, -1F, 0F);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape1 = new ModelRenderer(this, 150, 0);
        Shape1.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape1.setRotationPoint(11F, 0F, 0F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        Shape19L = new ModelRenderer(this, 100, 20);
        Shape19L.addBox(0F, 0F, 0F, 3, 5, 1);
        Shape19L.setRotationPoint(9F, -3F, 2F);
        Shape19L.setTextureSize(64, 32);
        Shape19L.mirror = true;
        setRotation(Shape19L, 0F, 0F, 0F);
        Shape10L = new ModelRenderer(this, 160, 0);
        Shape10L.addBox(0F, 0F, 0F, 2, 1, 1);
        Shape10L.setRotationPoint(12F, 0F, -1F);
        Shape10L.setTextureSize(64, 32);
        Shape10L.mirror = true;
        setRotation(Shape10L, 0F, 0F, 0F);
        Shape12 = new ModelRenderer(this, 160, 15);
        Shape12.addBox(0F, 0F, 0F, 5, 1, 1);
        Shape12.setRotationPoint(13F, 2F, -1F);
        Shape12.setTextureSize(64, 32);
        Shape12.mirror = true;
        setRotation(Shape12, 0F, 0F, 0F);
        Shape13 = new ModelRenderer(this, 160, 20);
        Shape13.addBox(0F, 0F, 0F, 3, 1, 1);
        Shape13.setRotationPoint(14F, 3F, -1F);
        Shape13.setTextureSize(64, 32);
        Shape13.mirror = true;
        setRotation(Shape13, 0F, 0F, 0F);
        Shape9 = new ModelRenderer(this, 150, 50);
        Shape9.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape9.setRotationPoint(19F, 0F, 0F);
        Shape9.setTextureSize(64, 32);
        Shape9.mirror = true;
        setRotation(Shape9, 0F, 0F, 0F);
        Shape16 = new ModelRenderer(this, 160, 0);
        Shape16.addBox(0F, 0F, 0F, 2, 1, 1);
        Shape16.setRotationPoint(17F, 0F, -1F);
        Shape16.setTextureSize(64, 32);
        Shape16.mirror = true;
        setRotation(Shape16, 0F, 0F, 0F);
        Shape11 = new ModelRenderer(this, 160, 10);
        Shape11.addBox(0F, 0F, 0F, 3, 1, 1);
        Shape11.setRotationPoint(12F, 1F, -1F);
        Shape11.setTextureSize(64, 32);
        Shape11.mirror = true;
        setRotation(Shape11, 0F, 0F, 0F);
        Shape14 = new ModelRenderer(this, 160, 25);
        Shape14.addBox(0F, 0F, 0F, 1, 1, 1);
        Shape14.setRotationPoint(15F, 4F, -1F);
        Shape14.setTextureSize(64, 32);
        Shape14.mirror = true;
        setRotation(Shape14, 0F, 0F, 0F);
        Shape15 = new ModelRenderer(this, 160, 10);
        Shape15.addBox(0F, 0F, 0F, 3, 1, 1);
        Shape15.setRotationPoint(16F, 1F, -1F);
        Shape15.setTextureSize(64, 32);
        Shape15.mirror = true;
        setRotation(Shape15, 0F, 0F, 0F);
        Shape17L = new ModelRenderer(this, 200, 0);
        Shape17L.addBox(0F, 0F, 0F, 1, 3, 2);
        Shape17L.setRotationPoint(13F, 0F, 1F);
        Shape17L.setTextureSize(64, 32);
        Shape17L.mirror = true;
        setRotation(Shape17L, 0F, 0F, 0F);
        Shape20R = new ModelRenderer(this, 100, 30);
        Shape20R.addBox(0F, 0F, 0F, 3, 5, 1);
        Shape20R.setRotationPoint(20F, -4F, 2F);
        Shape20R.setTextureSize(64, 32);
        Shape20R.mirror = true;
        setRotation(Shape20R, 0F, 0F, 0F);
        Shape18L = new ModelRenderer(this, 200, 10);
        Shape18L.addBox(0F, 0F, 0F, 2, 4, 1);
        Shape18L.setRotationPoint(11.33333F, -1F, 3F);
        Shape18L.setTextureSize(64, 32);
        Shape18L.mirror = true;
        setRotation(Shape18L, 0F, 0F, 0F);
        Shape21L = new ModelRenderer(this, 100, 40);
        Shape21L.addBox(0F, 0F, 0F, 2, 1, 1);
        Shape21L.setRotationPoint(9.333333F, -5F, 2F);
        Shape21L.setTextureSize(64, 32);
        Shape21L.mirror = true;
        setRotation(Shape21L, 0F, 0F, 0F);
        Shape20L = new ModelRenderer(this, 100, 30);
        Shape20L.addBox(0F, 0F, 0F, 3, 5, 1);
        Shape20L.setRotationPoint(8F, -4F, 2F);
        Shape20L.setTextureSize(64, 32);
        Shape20L.mirror = true;
        setRotation(Shape20L, 0F, 0F, 0F);
        Shape17R = new ModelRenderer(this, 200, 0);
        Shape17R.addBox(0F, 0F, 0F, 1, 3, 2);
        Shape17R.setRotationPoint(17F, 0.3333333F, 1F);
        Shape17R.setTextureSize(64, 32);
        Shape17R.mirror = true;
        setRotation(Shape17R, 0F, 0F, 0F);
        Shape18R = new ModelRenderer(this, 200, 10);
        Shape18R.addBox(0F, 0F, 0F, 2, 4, 1);
        Shape18R.setRotationPoint(18F, -1F, 3F);
        Shape18R.setTextureSize(64, 32);
        Shape18R.mirror = true;
        setRotation(Shape18R, 0F, 0F, 0F);
        Shape19R = new ModelRenderer(this, 100, 20);
        Shape19R.addBox(0F, 0F, 0F, 3, 5, 1);
        Shape19R.setRotationPoint(19F, -3F, 2F);
        Shape19R.setTextureSize(64, 32);
        Shape19R.mirror = true;
        setRotation(Shape19R, 0F, 0F, 0F);
        Shape21R = new ModelRenderer(this, 200, 40);
        Shape21R.addBox(0F, 0F, 0F, 2, 1, 1);
        Shape21R.setRotationPoint(19.7F, -5F, 2F);
        Shape21R.setTextureSize(64, 32);
        Shape21R.mirror = true;
        setRotation(Shape21R, 0F, 0F, 0F);
        Shape22 = new ModelRenderer(this, 10, 0);
        Shape22.addBox(0F, 0F, 0F, 1, 16, 1);
        Shape22.setRotationPoint(15F, 3F, 1F);
        Shape22.setTextureSize(64, 32);
        Shape22.mirror = true;
        setRotation(Shape22, 0F, 0F, 0F);
        Shape10R = new ModelRenderer(this, 160, 40);
        Shape10R.addBox(0F, 0F, 0F, 3, 3, 1);
        Shape10R.setRotationPoint(14F, 1F, 1F);
        Shape10R.setTextureSize(64, 32);
        Shape10R.mirror = true;
        setRotation(Shape10R, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        Shape23.render(f5);
        Shape8.render(f5);
        Shape7.render(f5);
        Shape6.render(f5);
        Shape5.render(f5);
        Shape4.render(f5);
        Shape3.render(f5);
        Shape2.render(f5);
        Shape1.render(f5);
        Shape19L.render(f5);
        Shape10L.render(f5);
        Shape12.render(f5);
        Shape13.render(f5);
        Shape9.render(f5);
        Shape16.render(f5);
        Shape11.render(f5);
        Shape14.render(f5);
        Shape15.render(f5);
        Shape17L.render(f5);
        Shape20R.render(f5);
        Shape18L.render(f5);
        Shape21L.render(f5);
        Shape20L.render(f5);
        Shape17R.render(f5);
        Shape18R.render(f5);
        Shape19R.render(f5);
        Shape21R.render(f5);
        Shape22.render(f5);
        Shape10R.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
