package com.astryxion.ironshulkerbox.common.item;

import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class IronShulkerBoxBlockItem extends BlockItem {

  protected IronShulkerBoxesTypes type;
  protected DyeColor color;

  public IronShulkerBoxBlockItem(Block block, Properties properties, IronShulkerBoxesTypes type, DyeColor color) {
    super(block, properties);

    this.type = type;
    this.color = color;
  }

  @Override
  public boolean canFitInsideContainerItems() {
    return !(this.getBlock() instanceof AbstractIronShulkerBoxBlock);
  }
}
