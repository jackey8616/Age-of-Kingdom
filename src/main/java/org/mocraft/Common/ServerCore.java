package org.mocraft.Common;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/13.
 */
public class ServerCore {

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getZ() { return z; }

    public void setZ(int z) { this.z = z; }

    private UUID uuid;
    private int x, y, z;

    public ServerCore() {
        this.uuid = new UUID(0L, 0L);
        this.x = this.y = this.z = 0;
    }

}
