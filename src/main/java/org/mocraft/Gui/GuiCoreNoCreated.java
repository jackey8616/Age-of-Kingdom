package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Block.TileCore;
import org.mocraft.Common.network.server.CoreCreateMessage;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCoreNoCreated extends GuiContainer {

    int btnId = 0;

    private EntityPlayer player;
    private TileCore tile;
    private GuiTextField txtVillageName;
    private GuiButton btnCreate, btnCancel;

    public GuiCoreNoCreated(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        this.tile = tile;
        this.player = player;
    }

    @Override
    public void initGui() {
        this.txtVillageName = new GuiTextField(fontRendererObj, 10, 40, 100, 20);
        this.txtVillageName.setFocused(true);

        this.buttonList.add(this.btnCreate = new GuiButton(btnId++, 10, 70, 100, 20, "Create"));
        this.buttonList.add(this.btnCancel = new GuiButton(btnId++, 10 + 100, 70, 100, 20, "Cancel"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
        this.drawString(fontRendererObj, "Create A Village", 10, 10, 0xFFFFFF);
        this.drawString(fontRendererObj, "If you want to create a village, you must have level 30.", 10, 22, 0xFFFFFF);

        this.txtVillageName.drawTextBox();
        for(Object btn : this.buttonList) {
            ((GuiButton)btn).drawButton(mc, mouseX, mouseY);
        }

        if(player.experienceLevel < 30) {
            this.txtVillageName.setEnabled(false);
            this.btnCreate.enabled = false;
        }
    }

    @Override
    protected void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
        this.txtVillageName.textboxKeyTyped(c, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int btnMouse) {
        super.mouseClicked(mouseX, mouseX, btnMouse);
        this.txtVillageName.mouseClicked(mouseX, mouseY, btnMouse);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        switch(button.id) {
            case 0: {
                if(!this.txtVillageName.getText().equals("")) {
                    AgeOfKingdom.channel.sendToServer(new CoreCreateMessage(this.txtVillageName.getText(), player.getUniqueID(), this.tile.xCoord, this.tile.yCoord, this.tile.zCoord));
                }
                break;
            }
            case 1: {

                break;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(player.experienceLevel >= 30) {
            this.txtVillageName.updateCursorCounter();
        }
    }

}
