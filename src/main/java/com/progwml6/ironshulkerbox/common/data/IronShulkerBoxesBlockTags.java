package com.progwml6.ironshulkerbox.common.data;

import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesBlockTags extends FabricTagProvider.BlockTagProvider {

  public IronShulkerBoxesBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    FabricTagProvider.FabricTagBuilder shulkerBoxes = getOrCreateTagBuilder(BlockTags.SHULKER_BOXES);

    shulkerBoxes.add(
      IronShulkerBoxesBlocks.IRON_SHULKER_BOX,
      IronShulkerBoxesBlocks.GOLD_SHULKER_BOX,
      IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX,
      IronShulkerBoxesBlocks.COPPER_SHULKER_BOX,
      IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX,
      IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX
    );

    IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
    IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.forEach((dyeColor, block) -> shulkerBoxes.add(block));
  }
}
