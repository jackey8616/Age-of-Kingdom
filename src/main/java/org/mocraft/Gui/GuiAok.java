package org.mocraft.Gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Common.ClientAok;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/12.
 */
public class GuiAok extends GuiScreen {

    private EntityPlayer player;
    private ClientAok ieep;

    public GuiAok(EntityPlayer player) {
        this.player = player;
        this.ieep = ClientAok.get(player);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float particalTick) {
        super.drawScreen(mouseX, mouseY, particalTick);
        this.drawDefaultBackground();
        if(this.ieep.getLandPos().equals(new BlockPos(0, 0, 0)) && this.ieep.getLordLevel() == 0) {
            this.drawString(fontRendererObj, "Non Kingdom", this.width / 2, this.height / 2, 0xffffff);
        } else {
            this.drawString(fontRendererObj, "Name : " + ieep.getAokName(), 10, 10, 0xffffff);
            this.drawString(fontRendererObj, "BlockPos : x=" + ieep.getLandPos().getX() + ", y=" + ieep.getLandPos().getY() + ", z=" + ieep.getLandPos().getZ(), 10, 20, 0xffffff);
            this.drawString(fontRendererObj, "Lord Level : Lv. " + ieep.getLordLevel(), 10, 30, 0xffffff);
            this.drawString(fontRendererObj, "Lord Name : " + ieep.getLordName(), 10, 40, 0xffffff);
            this.drawString(fontRendererObj, "Member Count : " + ieep.getMembers().size(), 10, 50, 0xffffff);
        }
    }
}
