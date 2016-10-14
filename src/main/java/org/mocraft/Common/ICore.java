package org.mocraft.Common;

import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/15.
 */
public interface ICore {

    UUID getLord();

    void setLord(UUID lord);

    UUID getUuid();

    void setUuid(UUID uuid);

    String getName();

    void setName(String name);

    int getLevel();

    void setLevel(int level);

    UUID getMembers(int i);

    void addMembers(UUID player);
}
