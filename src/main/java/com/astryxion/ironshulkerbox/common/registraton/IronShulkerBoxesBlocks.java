package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableMap;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.astryxion.ironshulkerbox.common.block.CopperShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.CrystalShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.DiamondShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.GoldShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.block.ObsidianShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IronShulkerBoxesBlocks {

  /**
   * NeoForge assigns {@code IronShulkerBoxesItems.ITEMS} before registering block items, so upgrade items
   * are registered first. Match that order so JEI (and anything keyed off registry sequence) matches NeoForge.
   */
  @SuppressWarnings("unused")
  private static final ImmutableMap<IronShulkerBoxesUpgradeType, Item> REGISTER_UPGRADE_ITEMS_BEFORE_BLOCKS = IronShulkerBoxesItems.UPGRADES;

  static BlockBehaviour.StatePredicate positionPredicate = (state, level, pos) -> {
    BlockEntity blockEntity = level.getBlockEntity(pos);

    if (!(blockEntity instanceof AbstractIronShulkerBoxBlockEntity shulkerBoxBlockEntity)) {
      return true;
    } else {
      return shulkerBoxBlockEntity.isClosed();
    }
  };

  private static BlockBehaviour.Properties standardProperties(ResourceKey<Block> blockKey) {
    return BlockBehaviour.Properties.of()
        .setId(blockKey)
        .mapColor(MapColor.METAL)
        .strength(3.0F)
        .dynamicShape()
        .noOcclusion()
        .isSuffocating(positionPredicate)
        .isViewBlocking(positionPredicate)
        .pushReaction(PushReaction.DESTROY);
  }

  private static BlockBehaviour.Properties reinforcedProperties(ResourceKey<Block> blockKey) {
    return BlockBehaviour.Properties.of()
        .setId(blockKey)
        .mapColor(MapColor.METAL)
        .strength(3.0F, 10000.0F)
        .dynamicShape()
        .noOcclusion()
        .isSuffocating(positionPredicate)
        .isViewBlocking(positionPredicate)
        .pushReaction(PushReaction.DESTROY);
  }

  private static Identifier id(String name) {
    return Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, name);
  }

  // Default uncolored
  public static final IronShulkerBoxBlock IRON_SHULKER_BOX = register("iron_shulker_box", p -> new IronShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::ironUncoloredProps, IronShulkerBoxesTypes.IRON, null);
  public static final GoldShulkerBoxBlock GOLD_SHULKER_BOX = register("gold_shulker_box", p -> new GoldShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::goldUncoloredProps, IronShulkerBoxesTypes.GOLD, null);
  public static final DiamondShulkerBoxBlock DIAMOND_SHULKER_BOX = register("diamond_shulker_box", p -> new DiamondShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::diamondUncoloredProps, IronShulkerBoxesTypes.DIAMOND, null);
  public static final CopperShulkerBoxBlock COPPER_SHULKER_BOX = register("copper_shulker_box", p -> new CopperShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::copperUncoloredProps, IronShulkerBoxesTypes.COPPER, null);
  public static final CrystalShulkerBoxBlock CRYSTAL_SHULKER_BOX = register("crystal_shulker_box", p -> new CrystalShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::crystalUncoloredProps, IronShulkerBoxesTypes.CRYSTAL, null);
  public static final ObsidianShulkerBoxBlock OBSIDIAN_SHULKER_BOX = register("obsidian_shulker_box", p -> new ObsidianShulkerBoxBlock(p, null), IronShulkerBoxesBlocks::obsidianUncoloredProps, IronShulkerBoxesTypes.OBSIDIAN, null);

  public static final ImmutableMap<DyeColor, IronShulkerBoxBlock> IRON_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("iron_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new IronShulkerBoxBlock(p, type), () -> standardProperties(ResourceKey.create(Registries.BLOCK, id("iron_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.IRON, type))));
  public static final ImmutableMap<DyeColor, GoldShulkerBoxBlock> GOLD_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("gold_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new GoldShulkerBoxBlock(p, type), () -> standardProperties(ResourceKey.create(Registries.BLOCK, id("gold_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.GOLD, type))));
  public static final ImmutableMap<DyeColor, DiamondShulkerBoxBlock> DIAMOND_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("diamond_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new DiamondShulkerBoxBlock(p, type), () -> standardProperties(ResourceKey.create(Registries.BLOCK, id("diamond_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.DIAMOND, type))));
  public static final ImmutableMap<DyeColor, CopperShulkerBoxBlock> COPPER_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("copper_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new CopperShulkerBoxBlock(p, type), () -> standardProperties(ResourceKey.create(Registries.BLOCK, id("copper_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.COPPER, type))));
  public static final ImmutableMap<DyeColor, CrystalShulkerBoxBlock> CRYSTAL_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("crystal_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new CrystalShulkerBoxBlock(p, type), () -> standardProperties(ResourceKey.create(Registries.BLOCK, id("crystal_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.CRYSTAL, type))));
  public static final ImmutableMap<DyeColor, ObsidianShulkerBoxBlock> OBSIDIAN_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("obsidian_shulker_box_" + type.name().toLowerCase(Locale.ROOT), p -> new ObsidianShulkerBoxBlock(p, type), () -> reinforcedProperties(ResourceKey.create(Registries.BLOCK, id("obsidian_shulker_box_" + type.name().toLowerCase(Locale.ROOT)))), IronShulkerBoxesTypes.OBSIDIAN, type))));

  private static BlockBehaviour.Properties ironUncoloredProps() {
    return standardProperties(ResourceKey.create(Registries.BLOCK, id("iron_shulker_box")));
  }

  private static BlockBehaviour.Properties goldUncoloredProps() {
    return standardProperties(ResourceKey.create(Registries.BLOCK, id("gold_shulker_box")));
  }

  private static BlockBehaviour.Properties diamondUncoloredProps() {
    return standardProperties(ResourceKey.create(Registries.BLOCK, id("diamond_shulker_box")));
  }

  private static BlockBehaviour.Properties copperUncoloredProps() {
    return standardProperties(ResourceKey.create(Registries.BLOCK, id("copper_shulker_box")));
  }

  private static BlockBehaviour.Properties crystalUncoloredProps() {
    return standardProperties(ResourceKey.create(Registries.BLOCK, id("crystal_shulker_box")));
  }

  private static BlockBehaviour.Properties obsidianUncoloredProps() {
    return reinforcedProperties(ResourceKey.create(Registries.BLOCK, id("obsidian_shulker_box")));
  }

  private static <T extends Block> T register(
      String name,
      Function<BlockBehaviour.Properties, ? extends T> blockFactory,
      Supplier<BlockBehaviour.Properties> blockProps,
      IronShulkerBoxesTypes shulkerBoxesType,
      @Nullable DyeColor color) {
    Identifier rid = id(name);
    ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, rid);
    ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, rid);
    T block = blockFactory.apply(blockProps.get());
    Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    Registry.register(BuiltInRegistries.ITEM, itemKey, new IronShulkerBoxBlockItem(block,
        new Item.Properties()
            .setId(itemKey)
            .useBlockDescriptionPrefix()
            .stacksTo(1)
            .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY),
        shulkerBoxesType,
        color));
    return block;
  }
}
