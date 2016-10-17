package org.mocraft.Utils;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/15.
 */
public interface ICore {

    UUID getLord();

    void setLord(UUID lord);

    int getLordLevel();

    void setLordLevel(int level);

    UUID getUuid();

    void setUuid(UUID uuid);

    String getName();

    void setName(String name);

    int getLevel();

    void setLevel(int level);

    UUID getMembers(int i);

    void addMembers(UUID player);

    void setX(int x);

    int getX();

    void setY(int y);

    int getY();

    void setZ(int z);

    int getZ();

}
