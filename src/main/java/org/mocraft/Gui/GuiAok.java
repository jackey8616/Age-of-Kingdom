package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import org.mocraft.Common.ClientAok;
import org.mocraft.Gui.vanilla.GuiAokScreen;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/12.
 */
@SideOnly(Side.CLIENT)
public class GuiAok extends GuiAokScreen {

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
            this.drawString(fontRendererObj, StatCollector.translateToLocal("gui.aok.msg.NoKingdom"), this.width / 2, this.height / 2, 0xffffff);
        } else {
            this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.AokName") + " : " + ieep.getAokName(), 10, 10, 0xffffff);
            this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.BlockPos") + " : x=" + ieep.getLandPos().getX() + ", y=" + ieep.getLandPos().getY() + ", z=" + ieep.getLandPos().getZ(), 10, 20, 0xffffff);
            this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.LordLevel") + " : Lv. " + ieep.getLordLevel(), 10, 30, 0xffffff);
            this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.LordName") + " : " + ieep.getLordName(), 10, 40, 0xffffff);
            this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.MemberCount") + " : " + ieep.getMembers().size(), 10, 50, 0xffffff);
        }
    }
}
