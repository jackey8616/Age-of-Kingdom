package org.mocraft.Utils;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Clode on 2016/10/22.
 */
public enum Action {

    SEND_MEMBER(0), REQUEST_OPEN_GUI(1), RECIEVED_MEMBER(2);


    private int value;

    private Action(int value) {
        this.value = value;
    }

    public int getValue() { return this.value; }

    public static Action fromInteger(int x) {
        switch(x) {
            case 0: return SEND_MEMBER;
            case 1: return REQUEST_OPEN_GUI;
            case 2: return RECIEVED_MEMBER;
        }
        return null;
    }
}