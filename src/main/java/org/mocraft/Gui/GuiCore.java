package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Gui.vanilla.GuiAokButton;
import org.mocraft.Gui.vanilla.GuiAokContainer;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.Network.common.GuiCoreMessage;
import org.mocraft.Network.common.GuiMemberMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.CoreAction;
import org.mocraft.Utils.MemberAction;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCore extends GuiAokContainer {

    private int btnId = 0;

    private ClientAok clientAok;
    private EntityPlayer player;
    private GuiAokButton btnMember, btnDismiss, btnQuit;

    public GuiCore(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        this.player = player;
        this.clientAok = ClientAok.get(player);
    }

    @Override
    public void initGui() {
        this.buttonList.add(this.btnMember = new GuiAokButton(this.btnId++, width - 100 - 10, 60, 100, 10, "Members"));
        this.buttonList.add(this.btnDismiss = new GuiAokButton(this.btnId++, width - 100 - 10, this.height - 20, 100, 10, "Dismiss"));
        this.buttonList.add(this.btnQuit = new GuiAokButton(this.btnId++, width - 100 -10 - 10 - 100, this.height - 20, 100, 10, "Quit"));

        if(!this.clientAok.getLordName().equals(player.getDisplayName())) {
            this.btnMember.enabled = false;
            this.btnDismiss.enabled = false;
        } else {
            this.btnQuit.enabled = false;
        }
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if(this.clientAok.getLandPos().equals(new BlockPos(0, 0, 0)) && this.clientAok.getLordLevel() == 0) {
            this.drawString(fontRendererObj, "Non Kingdom", this.width / 2, this.height / 2, 0xffffff);
        } else {
            this.drawString(fontRendererObj, "Name : " + this.clientAok.getAokName(), 10, 10, 0xffffff);
            this.drawString(fontRendererObj, "BlockPos : x=" + this.clientAok.getLandPos().getX() + ", y=" + this.clientAok.getLandPos().getY() + ", z=" + this.clientAok.getLandPos().getZ(), 10, 20, 0xffffff);
            this.drawString(fontRendererObj, "Aok Level : " + this.clientAok.getAokLevel(), 10, 30, 0xffffff);
            this.drawString(fontRendererObj, "Lord Level : Lv. " + this.clientAok.getLordLevel(), 10, 40, 0xffffff);
            this.drawString(fontRendererObj, "Lord Name : " + this.clientAok.getLordName(), 10, 50, 0xffffff);
            this.drawString(fontRendererObj, "Members : ", 10, 60, 0xffffff);
            for(int i = 0; i < this.clientAok.getMembers().size(); ++i) {
                this.drawString(fontRendererObj, this.clientAok.getMembers().get(i), 50 , 60 + i * 12, 0xffffff);
            }

            for(Object btn : this.buttonList) {
                ((GuiButton)btn).drawButton(mc, mouseX, mouseY);
            }
        }
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int btnMouse) {
        super.mouseClicked(mouseX, mouseX, btnMouse);
        for(Object obj : this.buttonList) {
            ((GuiAokButton) obj).mouseClicked(this, mouseX, mouseY, btnMouse);
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0: AgeOfKingdom.channel.sendToServer(new GuiMemberMessage(player, null, MemberAction.REQUEST_OPEN_GUI)); break;
            case 1: AgeOfKingdom.channel.sendToServer(new GuiCoreMessage(player, CoreAction.REQUEST_DISMISS)); break;
            case 2: AgeOfKingdom.channel.sendToServer(new GuiCoreMessage(player, CoreAction.SEND_QUIT)); break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

}
