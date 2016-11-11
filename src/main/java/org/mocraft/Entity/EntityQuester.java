package org.mocraft.Entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Clode on 2016/11/11.
 */
public class EntityQuester extends EntityAnimal {

    public EntityQuester(World world) {
        super(world);
        this.setSize(0.45F, 2.475F);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        return false;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_) {
        return null;
    }

}
