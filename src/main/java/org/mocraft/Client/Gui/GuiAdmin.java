package org.mocraft.Client.Gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import org.mocraft.Client.Gui.vanilla.GuiAokButton;
import org.mocraft.Client.Gui.vanilla.GuiAokScreen;

/**
 * Created by Clode on 2016/10/27.
 */
@SideOnly(Side.CLIENT)
public class GuiAdmin extends GuiAokScreen {

    private int page = 1;
    private int btnId = 0;
    private GuiAokButton btnConfirm, btnCancel, btnNext, btnPrevious;

    public GuiAdmin() {}

    @Override
    public void initGui() {
        this.buttonList.add(btnNext = new GuiAokButton(btnId++, width - 30 - 5, height - 25, 30, 20, "►"));
        this.buttonList.add(btnPrevious = new GuiAokButton(btnId++, 5, height - 25, 30, 20, "◄"));
        this.buttonList.add(btnConfirm = new GuiAokButton(btnId++, width / 2 - 100 - 5, height - 25, 100, 20, StatCollector.translateToLocal("gui.admin.button.confirm")));
        this.buttonList.add(btnCancel = new GuiAokButton(btnId++, width / 2 + 5, height - 25, 100, 20, StatCollector.translateToLocal("gui.admin.button.cancel")));

        this.btnPrevious.enabled = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tick) {
        super.drawScreen(mouseX, mouseX, tick);
        this.drawDefaultBackground();
        this.drawString(fontRendererObj, "Page " + this.page, width - 5 - 60 - 5, height - 5 - 15, 0xffffff);
        for(Object obj : this.buttonList) {
            ((GuiAokButton) obj).drawButton(mc, mouseX, mouseY);
        }
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
            case 0: page++; break;
            case 1: page -= page > 1 ? 1 : 0; break;
            case 2:
            case 3:
        }
    }

    @Override
    public void updateScreen() {
        this.btnPrevious.enabled = page > 1 ? true : false;
    }
}
