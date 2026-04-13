package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableSet;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.block.entity.CopperShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.CrystalShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.DiamondShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.GoldShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.IronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.ObsidianShulkerBoxBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Consumer;

public class IronShulkerBoxesBlockEntityTypes {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.MODID);

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronShulkerBoxBlockEntity>> IRON_SHULKER_BOX = BLOCK_ENTITIES.register("iron_shulker_box", () -> new BlockEntityType<>(IronShulkerBoxBlockEntity::new, Set.of()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldShulkerBoxBlockEntity>> GOLD_SHULKER_BOX = BLOCK_ENTITIES.register("gold_shulker_box", () -> new BlockEntityType<>(GoldShulkerBoxBlockEntity::new, Set.of()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiamondShulkerBoxBlockEntity>> DIAMOND_SHULKER_BOX = BLOCK_ENTITIES.register("diamond_shulker_box", () -> new BlockEntityType<>(DiamondShulkerBoxBlockEntity::new, Set.of()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperShulkerBoxBlockEntity>> COPPER_SHULKER_BOX = BLOCK_ENTITIES.register("copper_shulker_box", () -> new BlockEntityType<>(CopperShulkerBoxBlockEntity::new, Set.of()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalShulkerBoxBlockEntity>> CRYSTAL_SHULKER_BOX = BLOCK_ENTITIES.register("crystal_shulker_box", () -> new BlockEntityType<>(CrystalShulkerBoxBlockEntity::new, Set.of()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObsidianShulkerBoxBlockEntity>> OBSIDIAN_SHULKER_BOX = BLOCK_ENTITIES.register("obsidian_shulker_box", () -> new BlockEntityType<>(ObsidianShulkerBoxBlockEntity::new, Set.of()));

  public static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
    event.modify(IRON_SHULKER_BOX.get(), blocksFrom(IronShulkerBoxBlockEntity::buildBlocks));
    event.modify(GOLD_SHULKER_BOX.get(), blocksFrom(GoldShulkerBoxBlockEntity::buildBlocks));
    event.modify(DIAMOND_SHULKER_BOX.get(), blocksFrom(DiamondShulkerBoxBlockEntity::buildBlocks));
    event.modify(COPPER_SHULKER_BOX.get(), blocksFrom(CopperShulkerBoxBlockEntity::buildBlocks));
    event.modify(CRYSTAL_SHULKER_BOX.get(), blocksFrom(CrystalShulkerBoxBlockEntity::buildBlocks));
    event.modify(OBSIDIAN_SHULKER_BOX.get(), blocksFrom(ObsidianShulkerBoxBlockEntity::buildBlocks));
  }

  private static Block[] blocksFrom(Consumer<ImmutableSet.Builder<Block>> blockCollector) {
    ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
    blockCollector.accept(builder);
    return builder.build().toArray(Block[]::new);
  }
}
