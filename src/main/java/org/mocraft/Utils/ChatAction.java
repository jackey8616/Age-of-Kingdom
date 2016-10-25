package org.mocraft.Utils;

/**
 * Created by Clode on 2016/10/25.
 */
public enum ChatAction {

     TO_SERVER(0), TO_CLIENT(1);

    private int value;

    private ChatAction(int value) {
        this.value = value;
    }

    public int getValue() { return this.value; }

    public static ChatAction fromInteger(int x) {
        switch(x) {
            case 0: return TO_SERVER;
            case 1: return TO_CLIENT;
        }
        return null;
    }
}
