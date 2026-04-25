package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableMap;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.block.CopperShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.CrystalShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.DiamondShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.GoldShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.block.ObsidianShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxBlockItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IronShulkerBoxesBlocks {

  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IronShulkerBoxes.MODID);

  public static final DeferredRegister.Items ITEMS = IronShulkerBoxesItems.ITEMS;

  static BlockBehaviour.StatePredicate positionPredicate = (state, level, pos) -> {
    BlockEntity blockEntity = level.getBlockEntity(pos);

    if (!(blockEntity instanceof AbstractIronShulkerBoxBlockEntity shulkerBoxBlockEntity)) {
      return true;
    } else {
      return shulkerBoxBlockEntity.isClosed();
    }
  };

  private static BlockBehaviour.Properties standardProperties() {
    return BlockBehaviour.Properties.of()
        .mapColor(MapColor.METAL)
        .strength(3.0F)
        .dynamicShape()
        .noOcclusion()
        .isSuffocating(positionPredicate)
        .isViewBlocking(positionPredicate)
        .pushReaction(PushReaction.DESTROY);
  }

  private static BlockBehaviour.Properties reinforcedProperties() {
    return BlockBehaviour.Properties.of()
        .mapColor(MapColor.METAL)
        .strength(3.0F, 10000.0F)
        .dynamicShape()
        .noOcclusion()
        .isSuffocating(positionPredicate)
        .isViewBlocking(positionPredicate)
        .pushReaction(PushReaction.DESTROY);
  }

  //Default uncolored
  public static final DeferredBlock<IronShulkerBoxBlock> IRON_SHULKER_BOX = register("iron_shulker_box", p -> new IronShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.IRON, null);
  public static final DeferredBlock<GoldShulkerBoxBlock> GOLD_SHULKER_BOX = register("gold_shulker_box", p -> new GoldShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.GOLD, null);
  public static final DeferredBlock<DiamondShulkerBoxBlock> DIAMOND_SHULKER_BOX = register("diamond_shulker_box", p -> new DiamondShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.DIAMOND, null);
  public static final DeferredBlock<CopperShulkerBoxBlock> COPPER_SHULKER_BOX = register("copper_shulker_box", p -> new CopperShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.COPPER, null);
  public static final DeferredBlock<CrystalShulkerBoxBlock> CRYSTAL_SHULKER_BOX = register("crystal_shulker_box", p -> new CrystalShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.CRYSTAL, null);
  public static final DeferredBlock<ObsidianShulkerBoxBlock> OBSIDIAN_SHULKER_BOX = register("obsidian_shulker_box", p -> new ObsidianShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::reinforcedProperties, IronShulkerBoxesTypes.OBSIDIAN, null);

  public static final ImmutableMap<DyeColor, DeferredBlock<IronShulkerBoxBlock>> IRON_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("iron_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new IronShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.IRON, type))));
  public static final ImmutableMap<DyeColor, DeferredBlock<GoldShulkerBoxBlock>> GOLD_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("gold_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new GoldShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.GOLD, type))));
  public static final ImmutableMap<DyeColor, DeferredBlock<DiamondShulkerBoxBlock>> DIAMOND_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("diamond_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new DiamondShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.DIAMOND, type))));
  public static final ImmutableMap<DyeColor, DeferredBlock<CopperShulkerBoxBlock>> COPPER_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("copper_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new CopperShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.COPPER, type))));
  public static final ImmutableMap<DyeColor, DeferredBlock<CrystalShulkerBoxBlock>> CRYSTAL_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("crystal_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new CrystalShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::standardProperties, IronShulkerBoxesTypes.CRYSTAL, type))));
  public static final ImmutableMap<DyeColor, DeferredBlock<ObsidianShulkerBoxBlock>> OBSIDIAN_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("obsidian_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new ObsidianShulkerBoxBlock(p, type), IronShulkerBoxesBlocks::reinforcedProperties, IronShulkerBoxesTypes.OBSIDIAN, type))));

  private static <T extends Block> DeferredBlock<T> register(
      String name,
      Function<BlockBehaviour.Properties, ? extends T> blockFactory,
      Supplier<BlockBehaviour.Properties> blockProps,
      IronShulkerBoxesTypes shulkerBoxesType,
      @Nullable DyeColor color) {
    DeferredBlock<T> ret = BLOCKS.registerBlock(name, blockFactory, blockProps);
    ITEMS.registerItem(
        name,
        props -> new IronShulkerBoxBlockItem(ret.get(), props, shulkerBoxesType, color),
        props -> props.useBlockDescriptionPrefix().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    return ret;
  }
}
