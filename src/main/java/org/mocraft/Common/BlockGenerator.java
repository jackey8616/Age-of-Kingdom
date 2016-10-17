package org.mocraft.Common;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.mocraft.Block.BlockManager;
import org.mocraft.ProxyServer;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Clode on 2016/10/17.
 */
public class BlockGenerator implements IWorldGenerator {

    private static final int CORE_MAX_DISTANCE = 200;

    private WorldGenerator coreGenerator= new WorldGenMinable(BlockManager.blockCore, 1);

    public BlockGenerator() {  }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case 0:
                int xCoord = chunkX * 16 + random.nextInt(16);
                int zCoord = chunkZ * 16 + random.nextInt(16);
                int yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
                BlockPos pos = new BlockPos(xCoord, yCoord, zCoord);

                for(int i = 0; i < ProxyServer.corePos.size(); ++i) {
                    BlockPos bPos = ProxyServer.corePos.get(i);
                    if(Math.abs(pos.getX() - bPos.getX()) <= CORE_MAX_DISTANCE && Math.abs(pos.getZ() - bPos.getZ()) <=  CORE_MAX_DISTANCE) {
                        return;
                    }
                }

                world.setBlock(xCoord, yCoord, zCoord, BlockManager.blockCore);
                System.out.println("x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord);
                ProxyServer.corePos.add(pos);
                break;
            case -1: break; // Nether
            case 1: break; // End
        }
    }
}
