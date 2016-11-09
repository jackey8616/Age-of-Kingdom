package org.mocraft.Utils;

/**
 * Created by Clode on 2016/10/24.
 */
public enum MainScreenAction {

    SEND_LAND_NAME(0);

    private int value;

    private MainScreenAction(int value) {
        this.value = value;
    }

    public int getValue() { return this.value; }

    public static MainScreenAction fromInteger(int x) {
        switch(x) {
            case 0: return SEND_LAND_NAME;
        }
        return null;
    }

}
