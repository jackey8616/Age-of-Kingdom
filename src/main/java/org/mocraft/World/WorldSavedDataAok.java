package org.mocraft.World;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import org.mocraft.AgeOfKingdom;
import org.mocraft.ProxyServer;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/24.
 */
public class WorldSavedDataAok extends WorldSavedData {

    public WorldSavedDataAok() {
        super(AgeOfKingdom.MODID);
    }

    public WorldSavedDataAok(String ID) {
        super(ID);
    }

    public static WorldSavedDataAok get(World world) {
        MapStorage storage = world.mapStorage;
        WorldSavedDataAok data = (WorldSavedDataAok) storage.loadData(WorldSavedDataAok.class, AgeOfKingdom.MODID);
        if(data == null) {
            storage.setData(AgeOfKingdom.MODID, data = new WorldSavedDataAok());
            data.markDirty();
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList posList = (NBTTagList) compound.getTag("CorePosList");
        for (int i = 0; i < posList.tagCount(); ++i) {
            NBTTagCompound nbt = posList.getCompoundTagAt(i);
            ProxyServer.addCorePos(new BlockPos(nbt));
        }
        Util.CREATE_AOK_MIN_LEVEL = compound.getInteger("CREATE_AOK_MIN_LEVEL");
        Util.LAND_FIELD_RADIUS = compound.getInteger("LAND_FIELD_RADIUS");
        Util.LAND_FIELD_OFFSET = compound.getInteger("LAND_FIELD_OFFSET");
        Util.CHAT_MIN_RANGE = compound.getInteger("CHAT_MIN_RANGE");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList posList = new NBTTagList();
        for(BlockPos pos : ProxyServer.corePos) {
            NBTTagCompound posNbt = new NBTTagCompound();
            pos.saveNBTData(posNbt);
            posList.appendTag(posNbt);
        }
        compound.setTag("CorePosList", posList);
        compound.setInteger("CREATE_AOK_MIN_LEVEL", Util.CREATE_AOK_MIN_LEVEL);
        compound.setInteger("LAND_FIELD_RADIUS", Util.LAND_FIELD_RADIUS);
        compound.setInteger("LAND_FIELD_OFFSET", Util.LAND_FIELD_OFFSET);
        compound.setInteger("CHAT_MIN_RANGE", Util.CHAT_MIN_RANGE);
    }
}
