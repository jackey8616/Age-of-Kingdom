package org.mocraft.Block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.ProxyServer;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/10.
 */
public class BlockCore extends Block implements ITileEntityProvider {

    protected BlockCore(String unlocalizeName, Material material) {
        super(material);
        this.setBlockName(unlocalizeName);
        this.setBlockTextureName(AgeOfKingdom.MODID + ":" +unlocalizeName);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBlockUnbreakable();
        this.isBlockContainer = true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCore();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        super.breakBlock(world, x, y, z, block, metadata);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventID, int eventParam) {
        super.onBlockEventReceived(world, x, y, z, eventID, eventParam);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int action, float hitX, float hitY, float hitZ) {
        TileCore tile = (TileCore) world.getTileEntity(x, y, z);
        if(!world.isRemote && !tile.isUsing()) {
            if(!tile.getAokName().equals("null") && !tile.hasPlayer(player.getUniqueID())) {
                player.addChatComponentMessage(new ChatComponentText("You are not belong this AoK!"));
                return false;
            } else if(!ClientAok.get(player).getAokName().equals("null") && !ClientAok.get(player).getAokName().equals(tile.getAokName())) {
                player.addChatComponentMessage(new ChatComponentText("You already have a AoK!"));
                return false;
            }

            tile.setUsing(true);
            if(tile.getAokName().equals("null")) {
                player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_CORE_NO_CREATED, world, x, y, z);
            } else {
                player.openGui(AgeOfKingdom.INSTANCE, AgeOfKingdom.serverProxy.GUI_CORE, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void onPostBlockPlaced(World world, int x, int y, int z, int metadata) {
        ProxyServer.corePos.add(new BlockPos(x, y, z));
        return;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
        for(BlockPos p : ProxyServer.corePos) {
            if(p.equals(new BlockPos(x, y, z))) {
                ProxyServer.corePos.remove(p);
                return;
            }
        }
    }

}
