package scellena.fallen_elves.blocks.spell;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import scellena.fallen_elves.FallenElves;

import java.io.IOException;

public class GuiSpellSet extends GuiContainer {

    TileEntitySpellSet table;
    ResourceLocation texture = new ResourceLocation(FallenElves.MOD_ID, "textures/gui/spell_set.png");

    public GuiSpellSet(InventoryPlayer player, TileEntitySpellSet tile) {
        super(new ContainerSpellSet(player, tile));
        table = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop,0, 0, 176, 166); }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

}
