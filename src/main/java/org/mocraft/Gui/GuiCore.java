package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.Network.server.CoreCreateMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCore extends GuiContainer {

    private int btnId = 0;

    private ClientAok clientAok;
    private EntityPlayer player;
    private GuiButton btnMember;

    public GuiCore(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        clientAok = ClientAok.get(player);
    }

    @Override
    public void initGui() {
        this.buttonList.add(btnMember = new GuiButton(btnId++, width - 100 - 10, 60, 100, 10, "Members"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
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

            for(int i = 0; i < buttonList.size(); ++i) {
                ((GuiButton)buttonList.get(i)).drawButton(mc, mouseX, mouseY);
            }
        }
    }


    @Override
    protected void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int btnMouse) {
        super.mouseClicked(mouseX, mouseX, btnMouse);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        switch(button.id) {
            case 0: {

                break;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }


}
