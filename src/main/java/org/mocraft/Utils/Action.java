package org.mocraft.Utils;

/**
 * Created by Clode on 2016/10/22.
 */
public enum Action {

    SEND_MEMBER(0), REQUEST_OPEN_GUI(1), RECIEVED_MEMBER(2), INVITE_MEMBER(3), INVITATION_FROM(4),
    PLAYER_OFFLINE(5), PLAYER_ACCEPT(6), PLAYER_DENIED(7);


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
            case 3: return INVITE_MEMBER;
            case 4: return INVITATION_FROM;
            case 5: return PLAYER_OFFLINE;
            case 6: return PLAYER_ACCEPT;
            case 7: return PLAYER_DENIED;
        }
        return null;
    }
}