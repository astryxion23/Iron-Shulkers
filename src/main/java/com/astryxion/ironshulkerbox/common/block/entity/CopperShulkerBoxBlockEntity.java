package com.astryxion.ironshulkerbox.common.block.entity;

import com.google.common.collect.ImmutableSet;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.inventory.IronShulkerBoxMenu;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class CopperShulkerBoxBlockEntity extends AbstractIronShulkerBoxBlockEntity {

  public CopperShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
    super(IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.COPPER);
  }

  public CopperShulkerBoxBlockEntity(@Nullable DyeColor pColor, BlockPos blockPos, BlockState blockState) {
    super(IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX, blockPos, blockState, pColor, IronShulkerBoxesTypes.COPPER);
  }

  @Override
  protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
    return IronShulkerBoxMenu.createCopperContainer(pContainerId, pInventory, this);
  }

  @Override
  public Block getBlockToUse() {
    if (this.getColor() == null) {
      return IronShulkerBoxesBlocks.COPPER_SHULKER_BOX;
    } else {
      return IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(this.getColor());
    }
  }

  public static void buildBlocks(ImmutableSet.Builder<Block> builder) {
    builder.add(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX);
    IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.forEach((dyeColor, block) -> builder.add(block));
  }
}
