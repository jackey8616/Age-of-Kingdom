package org.mocraft.Gui;

import net.minecraft.client.gui.GuiScreen;
import org.mocraft.Gui.vanilla.GuiAokButton;

/**
 * Created by Clode on 2016/10/27.
 */
public class GuiAdmin extends GuiScreen {

    private int btnId = 0;

    private GuiAokButton btnConfirm, btnCancel;

    public GuiAdmin() {}

    @Override
    public void initGui() {
        this.buttonList.add(btnConfirm = new GuiAokButton(btnId, 10, 10, 100, 10, "Confirm"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tick) {
        super.drawScreen(mouseX, mouseX, tick);
    }

}
