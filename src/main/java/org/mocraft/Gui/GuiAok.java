package org.mocraft.Gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Common.ClientCore;

/**
 * Created by Clode on 2016/10/12.
 */
public class GuiAok extends GuiScreen {

    private EntityPlayer player;
    private ClientCore ieep;

    public GuiAok(EntityPlayer player, ClientCore ieep) {
        this.player = player;
        this.ieep = ieep;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTick) {
        super.drawScreen(mouseX, mouseY, particalTick);
        this.drawDefaultBackground();
        if(ieep.getName().equals("null")) {
            this.drawString(fontRendererObj, "Non Kingdom", this.width / 2, this.height / 2, 0xffffff);
        } else {
            this.drawString(fontRendererObj, "UUID : " + ieep.getUuid().toString(), 10, 10, 0xffffff);
            this.drawString(fontRendererObj, "Lord : " + ieep.getLord().toString(), 10, 20, 0xffffff);
            this.drawString(fontRendererObj, "Name : " + ieep.getName(), 10, 30, 0xffffff);
            this.drawString(fontRendererObj, "Level: " + String.valueOf(ieep.getLevel()), 10, 40, 0xffffff);
        }
    }

}
