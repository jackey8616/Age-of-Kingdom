package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Network.server.CoreCreateMessage;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCoreNoCreated extends GuiContainer {

    int btnId = 0;

    private EntityPlayer player;
    private TileCore tile;
    private GuiTextField txtName;
    private GuiButton btnCreate, btnCancel;

    public GuiCoreNoCreated(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        this.tile = tile;
        this.player = player;
    }

    @Override
    public void initGui() {
        this.txtName = new GuiTextField(fontRendererObj, 10, 40, 100, 20);
        this.txtName.setFocused(true);

        this.buttonList.add(this.btnCreate = new GuiButton(btnId++, 10, 70, 100, 20, "Create"));
        this.buttonList.add(this.btnCancel = new GuiButton(btnId++, 10 + 100, 70, 100, 20, "Cancel"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
        this.drawString(fontRendererObj, "Create A Village", 10, 10, 0xFFFFFF);
        this.drawString(fontRendererObj, "If you want to create a village, you must have level " + Util.CREATE_AOK_MIN_LEVEL + ".", 10, 22, 0xFFFFFF);

        this.txtName.drawTextBox();
        for(Object btn : this.buttonList) {
            ((GuiButton)btn).drawButton(mc, mouseX, mouseY);
        }

        if(player.experienceLevel < Util.CREATE_AOK_MIN_LEVEL) {
            this.txtName.setEnabled(false);
            this.btnCreate.enabled = false;
        }
    }

    @Override
    protected void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
        this.txtName.textboxKeyTyped(c, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int btnMouse) {
        super.mouseClicked(mouseX, mouseX, btnMouse);
        this.txtName.mouseClicked(mouseX, mouseY, btnMouse);
        if(mouseX > this.btnCreate.xPosition && mouseX < this.btnCreate.xPosition + this.btnCreate.width) {
            if(mouseY > this.btnCreate.yPosition && mouseY < this.btnCreate.yPosition + this.btnCreate.height) {
                actionPerformed(this.btnCreate);
            }
        } else if(mouseX > this.btnCancel.xPosition && mouseX < this.btnCancel.xPosition + this.btnCancel.width) {
            if(mouseY > this.btnCancel.yPosition && mouseY < this.btnCancel.yPosition + this.btnCancel.height) {
                actionPerformed(this.btnCancel);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0: {
                if(!this.txtName.getText().equals("")) {
                    AgeOfKingdom.channel.sendToServer(new CoreCreateMessage(this.txtName.getText(), player.getUniqueID(), new BlockPos(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord)));
                }
                break;
            }
            case 1: {
                this.txtName.setText("");
                player.closeScreen();
                break;
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(player.experienceLevel >= Util.CREATE_AOK_MIN_LEVEL) {
            this.txtName.updateCursorCounter();
        }
    }

}
