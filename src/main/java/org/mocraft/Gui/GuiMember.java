package org.mocraft.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.mocraft.Inventory.ContainerCore;
import org.mocraft.TileEntity.TileCore;

/**
 * Created by Clode on 2016/10/22.
 */
@SideOnly(Side.CLIENT)
public class GuiMember extends GuiContainer {

    private int btnId = 0;

    private EntityPlayer player;
    private GuiTextField txtName;
    private GuiButton btnInvite, btnCancel;

    public GuiMember(TileCore tile, EntityPlayer player) {
        super(new ContainerCore(tile));
        this.player = player;
    }

    @Override
    public void initGui() {
        this.txtName = new GuiTextField(fontRendererObj, 10, 40, 100, 20);
        this.txtName.setFocused(true);

        this.buttonList.add(btnInvite = new GuiButton(btnId++, 10, 60, 100, 20, "Invite"));
        this.buttonList.add(btnCancel = new GuiButton(btnId++, 10 + 100, 60, 100, 20, "Cancel"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

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
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.txtName.updateCursorCounter();
    }


}
