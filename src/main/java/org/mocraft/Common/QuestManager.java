package org.mocraft.Common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Clode on 2016/11/14.
 */
public class QuestManager {

    private ArrayList<Quest> questList = new ArrayList<Quest>();

    public void init(FMLInitializationEvent event) {
        File modsFolder = MinecraftServer.getServer().getFile("mods");
        File aokFolder = new File(modsFolder, "AgeOfKingdom");

        if(aokFolder.exists()) {
            for(File file : aokFolder.listFiles()) {
                if(file.isFile())
                    addQuest(new Quest(file));
            }
        } else {
            aokFolder.mkdir();
        }
    }

    public void addQuest(Quest quest) {
        for(Quest q : questList) {
            if(q.equals(quest)) { return; }
        }
        questList.add(quest);
    }

}
