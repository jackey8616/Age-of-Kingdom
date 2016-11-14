package org.mocraft.Common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Clode on 2016/11/13.
 */
public class Quest {

    private String name;
    private Type type;
    private Object[] object;

    public Quest(File file) {
        readFromFile(file);
    }

    public Quest(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public void readFromFile(File fileName) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(new FileReader(fileName)).getAsJsonObject();
            this.name = object.get("Name").getAsString();
            this.type = Type.fromInteger(object.get("Type").getAsInt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(File fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            JsonObject object = new JsonObject();
            object.addProperty("Name", name);
            object.addProperty("Type", type.value);

            writer.write(object.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean equals(Quest quest) {
        if(quest.name.equals(this.name) && quest.type.equals(this.type) && quest.object.length == this.object.length) {
            for(int i = 0; i < object.length; ++i) {
                if(!quest.object[i].equals(this.object[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
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