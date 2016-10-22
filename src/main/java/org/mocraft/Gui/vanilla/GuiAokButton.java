package org.mocraft.Gui.vanilla;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Clode on 2016/10/22.
 */
@SideOnly(Side.CLIENT)
public class GuiAokButton extends GuiButton {

    public GuiAokButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
    }

    public void mouseClicked(GuiAokScreen screen, int mouseX, int mouseY, int mouseBtn) {
        if (mouseBtn == 0) {
            if(mouseX >= xPosition && mouseX <= xPosition + width && mouseY >= yPosition && mouseY <= yPosition + height) {
                screen.actionPerformed(this);
            }
        }
    }

}
