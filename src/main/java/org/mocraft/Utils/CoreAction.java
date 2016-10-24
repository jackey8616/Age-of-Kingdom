package org.mocraft.Utils;

/**
 * Created by Clode on 2016/10/24.
 */
public enum CoreAction {

    REQUEST_DISMISS(0);

    private int value;

    private CoreAction(int value) {
        this.value = value;
    }

    public int getValue() { return this.value; }

    public static CoreAction fromInteger(int x) {
        switch(x) {
            case 0: return REQUEST_DISMISS;
        }
        return null;
    }

}
