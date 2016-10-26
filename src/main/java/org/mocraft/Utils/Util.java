package org.mocraft.Utils;

/**
 * Created by Clode on 2016/10/21.
 */
public class Util {

    // Server variables.
    public static int CREATE_AOK_MIN_LEVEL = 30;
    public static int LAND_FIELD_RADIUS = 100;
    public static int LAND_FIELD_OFFSET = 20;
    public static final int CORE_MIN_DISTANCE = LAND_FIELD_RADIUS * 2 + LAND_FIELD_OFFSET;

    public static int CHAT_MIN_RANGE = 30;

   // Client variables.
    public static final String[] CHAT_DISABLE_KEYS = {
           "multiplayer.player.joined",
           "multiplayer.player.left",
           "chat.type.achievement",
           "death.attack.mob",
           "death.attack.player"
   };
}
