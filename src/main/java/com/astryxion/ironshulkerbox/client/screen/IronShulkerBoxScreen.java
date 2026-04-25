package com.astryxion.ironshulkerbox.client.screen;

import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.inventory.IronShulkerBoxMenu;
import net.minecraft.client.gui.GuiGraphics;
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
    super(container, playerInventory, title);

    this.shulkerBoxesType = container.getShulkerBoxType();
    this.imageWidth = container.getShulkerBoxType().xSize;
    this.imageHeight = container.getShulkerBoxType().ySize;
    this.inventoryLabelY = this.imageHeight - 94;
    this.textureXSize = container.getShulkerBoxType().textureXSize;
    this.textureYSize = container.getShulkerBoxType().textureYSize;
  }

  @Override
  protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
    int x = this.leftPos;
    int y = this.topPos;

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
  protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    int labelColor = 0xFF404040;
    graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, labelColor, false);
    graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, labelColor, false);
  }
}
