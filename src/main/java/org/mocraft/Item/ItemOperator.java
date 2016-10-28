package org.mocraft.Item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.mocraft.AgeOfKingdom;

/**
 * Created by Clode on 2016/10/29.
 */
public class ItemOperator extends Item {

    public ItemOperator() {
        setCreativeTab(CreativeTabs.tabBlock);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_ADMIN, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
        return itemStack;
    }
}
