package com.astryxion.ironshulkerbox.client.screen;

import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.inventory.IronShulkerBoxMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class IronShulkerBoxScreen extends AbstractContainerScreen<IronShulkerBoxMenu> implements MenuAccess<IronShulkerBoxMenu> {

  private final IronShulkerBoxesTypes shulkerBoxesType;

  private final int textureXSize;

  private final int textureYSize;

  public IronShulkerBoxScreen(IronShulkerBoxMenu container, Inventory playerInventory, Component title) {
    super(container, playerInventory, title, container.getShulkerBoxType().xSize, container.getShulkerBoxType().ySize);

    this.shulkerBoxesType = container.getShulkerBoxType();
    this.textureXSize = container.getShulkerBoxType().textureXSize;
    this.textureYSize = container.getShulkerBoxType().textureYSize;
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
    super.extractBackground(graphics, mouseX, mouseY, partialTick);

    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    graphics.blit(
        RenderPipelines.GUI_TEXTURED,
        this.shulkerBoxesType.guiTexture,
        x,
        y,
        0.0F,
        0.0F,
        this.imageWidth,
        this.imageHeight,
        this.textureXSize,
        this.textureYSize);
  }

  @Override
  protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    // Must use opaque ARGB (e.g. 0xFF404040); legacy grey 4210752 is 0x00404040 and alpha 0 â€” 26.1 skips drawing (see GuiGraphicsExtractor.text).
    int labelColor = 0xFF404040;
    graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, labelColor, false);
    graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, labelColor, false);
  }
}
