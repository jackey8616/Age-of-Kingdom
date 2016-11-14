package org.mocraft.Client.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Client.Gui.vanilla.GuiAokButton;
import org.mocraft.Client.Gui.vanilla.GuiAokContainer;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.Network.server.CoreCreateMessage;
import org.mocraft.TileEntity.TileCore;
import org.mocraft.Utils.BlockPos;
import org.mocraft.Utils.Util;

/**
 * Created by Clode on 2016/10/11.
 */
@SideOnly(Side.CLIENT)
public class GuiCoreNoCreated extends GuiAokContainer {

    int btnId = 0;

    private EntityPlayer player;
    private TileCore tile;
    private GuiTextField txtName;
    private GuiAokButton btnCreate, btnCancel;

    public GuiCoreNoCreated(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        this.tile = tile;
        this.player = player;
    }

    @Override
    public void initGui() {
        this.txtName = new GuiTextField(fontRendererObj, 10, 40, 100, 20);
        this.txtName.setFocused(true);

        this.buttonList.add(this.btnCreate = new GuiAokButton(btnId++, 10, 70, 100, 20, StatCollector.translateToLocal("gui.CoreNoCreated.button.create")));
        this.buttonList.add(this.btnCancel = new GuiAokButton(btnId++, 10 + 100, 70, 100, 20, StatCollector.translateToLocal("gui.CoreNoCreated.button.cancel")));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
        this.drawString(fontRendererObj, StatCollector.translateToLocal("gui.CoreNoCreated.msg.CreateAVillage"), 10, 10, 0xFFFFFF);
        this.drawString(fontRendererObj, StatCollector.translateToLocal("gui.CoreNoCreated.msg.IfYouWantToCreateAVillageYouMustHaveLevel") + Util.CREATE_AOK_MIN_LEVEL + ".", 10, 22, 0xFFFFFF);

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
    public void mouseClicked(int mouseX, int mouseY, int btnMouse) {
        super.mouseClicked(mouseX, mouseX, btnMouse);
        this.txtName.mouseClicked(mouseX, mouseY, btnMouse);
        this.btnCreate.mouseClicked(this, mouseX, mouseY, btnMouse);
        this.btnCancel.mouseClicked(this, mouseX, mouseY, btnMouse);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0: {
                if(!this.txtName.getText().equals("")) {
                    AgeOfKingdom.channel.sendToServer(new CoreCreateMessage(this.txtName.getText(), player.getUniqueID(), new BlockPos(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord)));
                    this.btnCreate.enabled = false;
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
