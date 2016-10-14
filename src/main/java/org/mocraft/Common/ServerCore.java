package org.mocraft.Common;

import java.util.UUID;

/**
 * Created by Clode on 2016/10/13.
 */
public class ServerCore {

    private UUID uuid;
    private int x, y, z;

    public ServerCore() {
        this.uuid = new UUID(0L, 0L);
        this.x = this.y = this.z = 0;
    }

}
