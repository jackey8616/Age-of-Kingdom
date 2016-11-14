package org.mocraft.Client.Gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Client.Gui.vanilla.GuiAokButton;
import org.mocraft.Client.Gui.vanilla.GuiAokScreen;
import org.mocraft.Network.common.GuiMemberMessage;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/23.
 */
public class GuiInvitation extends GuiAokScreen {

    private EntityPlayer player;
    private static String inviter, lordName, aokName;
    private static int lordLevel, aokLevel;
    private static BlockPos landPos = new BlockPos(0, 0, 0);

    private GuiAokButton btnAccept, btnDenied;

    public GuiInvitation(EntityPlayer player) {
        this.player = player;
    }

    public static void insert(String i, String an, int al, String ln, int ll, BlockPos bp) {
        inviter = i;
        aokName = an;
        aokLevel = al;
        lordName = ln;
        lordLevel = ll;
        landPos = bp;
    }

    @Override
    public void initGui() {
        this.buttonList.add(btnAccept = new  GuiAokButton(0, 10, 20, 100, 10, StatCollector.translateToLocal("gui.invitation.button.accept")));
        this.buttonList.add(btnDenied = new  GuiAokButton(1, 10 + 100, 20, 100, 10, StatCollector.translateToLocal("gui.invitation.button.denied")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tick) {
        super.drawScreen(mouseX, mouseY, tick);

        this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.AokName") + " : " + this.aokName, 10, 20, 0xffffff);
        this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.AokLevel") + " : " + String.valueOf(this.aokLevel), 10, 30, 0xffffff);
        this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.LordName") + " : " + this.lordName, 10, 40, 0xffffff);
        this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.LordLevel") + " : " + String.valueOf(this.lordLevel), 10, 50, 0xffffff);
        this.drawString(fontRendererObj, StatCollector.translateToLocal("aok.AokPos") + " : " + landPos.toString(), 10, 60, 0xffffff);

        for(Object obj : this.buttonList) {
            ((GuiAokButton) obj).drawButton(mc, mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
        super.mouseClicked(mouseX, mouseX, mouseBtn);
        this.btnAccept.mouseClicked(this, mouseX, mouseY, mouseBtn);
        this.btnDenied.mouseClicked(this, mouseX, mouseY, mouseBtn);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0: AgeOfKingdom.channel.sendToServer(new GuiMemberMessage(player, inviter, GuiMemberMessage.Type.PLAYER_ACCEPT)); break;
            case 1: AgeOfKingdom.channel.sendToServer(new GuiMemberMessage(player, inviter, GuiMemberMessage.Type.PLAYER_DENIED)); break;
        }
        player.closeScreen();
    }


}
