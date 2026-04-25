package com.progwml6.ironshulkerbox.common.item;

import com.progwml6.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class IronShulkerBoxBlockItem extends BlockItem {

  protected Supplier<IronShulkerBoxesTypes> type;
  @Nullable
  protected Supplier<DyeColor> color;

  public IronShulkerBoxBlockItem(Block block, Properties properties, Supplier<IronShulkerBoxesTypes> type, @Nullable Supplier<DyeColor> color) {
    super(block, properties);
    this.type = type;
    this.color = color;
  }

  public Supplier<IronShulkerBoxesTypes> getTypeSupplier() {
    return this.type;
  }

  @Nullable
  public Supplier<DyeColor> getColorSupplier() {
    return this.color;
  }

  @Override
  public boolean canFitInsideContainerItems() {
    return !(this.getBlock() instanceof AbstractIronShulkerBoxBlock);
  }
}
