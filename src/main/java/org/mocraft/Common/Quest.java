package org.mocraft.Common;

/**
 * Created by Clode on 2016/11/13.
 */
public class Quest {

    private String questName;
    private Type type;
    private Object[] object;

    public Quest(String questName, Type type) {
        this.questName = questName;
        this.type = type;
    }

    public enum Type {

        COLLECT(0);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() { return this.value; }

        public static Type fromInteger(int value) {
            switch(value) {
                case 0: return COLLECT;
            }
            return null;
        }
    }

}