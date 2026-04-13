package com.astryxion.ironshulkerbox.common.data;

import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesBlockTags extends FabricTagsProvider.BlockTagsProvider {

  public IronShulkerBoxesBlockTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
    super(output, registryLookupFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    var shulkerBoxes = valueLookupBuilder(BlockTags.SHULKER_BOXES);

    shulkerBoxes.add(
        IronShulkerBoxesBlocks.IRON_SHULKER_BOX,
        IronShulkerBoxesBlocks.GOLD_SHULKER_BOX,
        IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX,
        IronShulkerBoxesBlocks.COPPER_SHULKER_BOX,
        IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX,
        IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX);

    IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
  }
}
