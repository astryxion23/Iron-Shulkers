package com.progwml6.ironshulkerbox.common.registraton;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.block.entity.CopperShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.CrystalShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.DiamondShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.GoldShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.IronShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.ObsidianShulkerBoxBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.stream.Stream;

public class IronShulkerBoxesBlockEntityTypes {

  public static BlockEntityType<IronShulkerBoxBlockEntity> IRON_SHULKER_BOX;
  public static BlockEntityType<GoldShulkerBoxBlockEntity> GOLD_SHULKER_BOX;
  public static BlockEntityType<DiamondShulkerBoxBlockEntity> DIAMOND_SHULKER_BOX;
  public static BlockEntityType<CopperShulkerBoxBlockEntity> COPPER_SHULKER_BOX;
  public static BlockEntityType<CrystalShulkerBoxBlockEntity> CRYSTAL_SHULKER_BOX;
  public static BlockEntityType<ObsidianShulkerBoxBlockEntity> OBSIDIAN_SHULKER_BOX;

  public static void register() {
    IRON_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("iron_shulker_box"), typeOf(IronShulkerBoxBlockEntity::new, IronShulkerBoxBlockEntity.blocksForType()));
    GOLD_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("gold_shulker_box"), typeOf(GoldShulkerBoxBlockEntity::new, GoldShulkerBoxBlockEntity.blocksForType()));
    DIAMOND_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("diamond_shulker_box"), typeOf(DiamondShulkerBoxBlockEntity::new, DiamondShulkerBoxBlockEntity.blocksForType()));
    COPPER_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("copper_shulker_box"), typeOf(CopperShulkerBoxBlockEntity::new, CopperShulkerBoxBlockEntity.blocksForType()));
    CRYSTAL_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("crystal_shulker_box"), typeOf(CrystalShulkerBoxBlockEntity::new, CrystalShulkerBoxBlockEntity.blocksForType()));
    OBSIDIAN_SHULKER_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronShulkerBoxes.id("obsidian_shulker_box"), typeOf(ObsidianShulkerBoxBlockEntity::new, ObsidianShulkerBoxBlockEntity.blocksForType()));
  }

  private static <T extends BlockEntity> BlockEntityType<T> typeOf(BlockEntityType.BlockEntitySupplier<T> entity, Block[] blocks) {
    return BlockEntityType.Builder.of(entity, blocks).build(null);
  }
}
