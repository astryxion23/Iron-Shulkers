package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableSet;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.block.entity.CopperShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.CrystalShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.DiamondShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.GoldShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.IronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.ObsidianShulkerBoxBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import java.util.function.Consumer;

public class IronShulkerBoxesBlockEntityTypes {

  public static final BlockEntityType<IronShulkerBoxBlockEntity> IRON_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "iron_shulker_box"),
      FabricBlockEntityTypeBuilder.create(IronShulkerBoxBlockEntity::new, blocksFrom(IronShulkerBoxBlockEntity::buildBlocks)).build());

  public static final BlockEntityType<GoldShulkerBoxBlockEntity> GOLD_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "gold_shulker_box"),
      FabricBlockEntityTypeBuilder.create(GoldShulkerBoxBlockEntity::new, blocksFrom(GoldShulkerBoxBlockEntity::buildBlocks)).build());

  public static final BlockEntityType<DiamondShulkerBoxBlockEntity> DIAMOND_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "diamond_shulker_box"),
      FabricBlockEntityTypeBuilder.create(DiamondShulkerBoxBlockEntity::new, blocksFrom(DiamondShulkerBoxBlockEntity::buildBlocks)).build());

  public static final BlockEntityType<CopperShulkerBoxBlockEntity> COPPER_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "copper_shulker_box"),
      FabricBlockEntityTypeBuilder.create(CopperShulkerBoxBlockEntity::new, blocksFrom(CopperShulkerBoxBlockEntity::buildBlocks)).build());

  public static final BlockEntityType<CrystalShulkerBoxBlockEntity> CRYSTAL_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "crystal_shulker_box"),
      FabricBlockEntityTypeBuilder.create(CrystalShulkerBoxBlockEntity::new, blocksFrom(CrystalShulkerBoxBlockEntity::buildBlocks)).build());

  public static final BlockEntityType<ObsidianShulkerBoxBlockEntity> OBSIDIAN_SHULKER_BOX = Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "obsidian_shulker_box"),
      FabricBlockEntityTypeBuilder.create(ObsidianShulkerBoxBlockEntity::new, blocksFrom(ObsidianShulkerBoxBlockEntity::buildBlocks)).build());

  private static Block[] blocksFrom(Consumer<ImmutableSet.Builder<Block>> blockCollector) {
    ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
    blockCollector.accept(builder);
    return builder.build().toArray(Block[]::new);
  }
}
