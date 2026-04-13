package com.astryxion.ironshulkerbox.common.data.loot;

import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;

import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesBlockLoot extends FabricBlockLootSubProvider {

  public IronShulkerBoxesBlockLoot(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate() {
    this.add(IronShulkerBoxesBlocks.IRON_SHULKER_BOX, this::createShulkerBoxDrop);
    this.add(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, this::createShulkerBoxDrop);
    this.add(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, this::createShulkerBoxDrop);
    this.add(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, this::createShulkerBoxDrop);
    this.add(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, this::createShulkerBoxDrop);
    this.add(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, this::createShulkerBoxDrop);

    for (DyeColor color : DyeColor.values()) {
      this.add(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
      this.add(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
      this.add(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
      this.add(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
      this.add(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
      this.add(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color), this::createShulkerBoxDrop);
    }
  }
}
