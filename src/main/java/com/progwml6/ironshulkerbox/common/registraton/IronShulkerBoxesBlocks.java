package com.progwml6.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.block.CopperShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.CrystalShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.DiamondShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.GoldShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.progwml6.ironshulkerbox.common.block.ObsidianShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IronShulkerBoxesBlocks {

  public static final List<Block> ALL_REGISTERED_BLOCKS = new ArrayList<>();
  public static final List<IronShulkerBoxBlockItem> ALL_SHULKER_BOX_BLOCK_ITEMS = new ArrayList<>();

  static BlockBehaviour.StatePredicate positionPredicate = (state, level, pos) -> {
    BlockEntity blockEntity = level.getBlockEntity(pos);

    if (!(blockEntity instanceof AbstractIronShulkerBoxBlockEntity shulkerBoxBlockEntity)) {
      return true;
    } else {
      return shulkerBoxBlockEntity.isClosed();
    }
  };

  private static final BlockBehaviour.Properties STANDARD = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).dynamicShape().noOcclusion().isSuffocating(positionPredicate).isViewBlocking(positionPredicate).pushReaction(PushReaction.DESTROY);
  private static final BlockBehaviour.Properties REINFORCED = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F, 10000.0F).dynamicShape().noOcclusion().isSuffocating(positionPredicate).isViewBlocking(positionPredicate).pushReaction(PushReaction.DESTROY);

  public static IronShulkerBoxBlock IRON_SHULKER_BOX;
  public static GoldShulkerBoxBlock GOLD_SHULKER_BOX;
  public static DiamondShulkerBoxBlock DIAMOND_SHULKER_BOX;
  public static CopperShulkerBoxBlock COPPER_SHULKER_BOX;
  public static CrystalShulkerBoxBlock CRYSTAL_SHULKER_BOX;
  public static ObsidianShulkerBoxBlock OBSIDIAN_SHULKER_BOX;

  public static ImmutableMap<DyeColor, IronShulkerBoxBlock> IRON_SHULKER_BOXES;
  public static ImmutableMap<DyeColor, GoldShulkerBoxBlock> GOLD_SHULKER_BOXES;
  public static ImmutableMap<DyeColor, DiamondShulkerBoxBlock> DIAMOND_SHULKER_BOXES;
  public static ImmutableMap<DyeColor, CopperShulkerBoxBlock> COPPER_SHULKER_BOXES;
  public static ImmutableMap<DyeColor, CrystalShulkerBoxBlock> CRYSTAL_SHULKER_BOXES;
  public static ImmutableMap<DyeColor, ObsidianShulkerBoxBlock> OBSIDIAN_SHULKER_BOXES;

  public static void register() {
    IRON_SHULKER_BOX = register("iron_shulker_box", new IronShulkerBoxBlock(STANDARD, null), IronShulkerBoxesTypes.IRON, null);
    GOLD_SHULKER_BOX = register("gold_shulker_box", new GoldShulkerBoxBlock(STANDARD, null), IronShulkerBoxesTypes.GOLD, null);
    DIAMOND_SHULKER_BOX = register("diamond_shulker_box", new DiamondShulkerBoxBlock(STANDARD, null), IronShulkerBoxesTypes.DIAMOND, null);
    COPPER_SHULKER_BOX = register("copper_shulker_box", new CopperShulkerBoxBlock(STANDARD, null), IronShulkerBoxesTypes.COPPER, null);
    CRYSTAL_SHULKER_BOX = register("crystal_shulker_box", new CrystalShulkerBoxBlock(STANDARD, null), IronShulkerBoxesTypes.CRYSTAL, null);
    OBSIDIAN_SHULKER_BOX = register("obsidian_shulker_box", new ObsidianShulkerBoxBlock(REINFORCED, null), IronShulkerBoxesTypes.OBSIDIAN, null);

    IRON_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("iron_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new IronShulkerBoxBlock(STANDARD, type), IronShulkerBoxesTypes.IRON, type))));
    GOLD_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("gold_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new GoldShulkerBoxBlock(STANDARD, type), IronShulkerBoxesTypes.GOLD, type))));
    DIAMOND_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("diamond_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new DiamondShulkerBoxBlock(STANDARD, type), IronShulkerBoxesTypes.DIAMOND, type))));
    COPPER_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("copper_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new CopperShulkerBoxBlock(STANDARD, type), IronShulkerBoxesTypes.COPPER, type))));
    CRYSTAL_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("crystal_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new CrystalShulkerBoxBlock(STANDARD, type), IronShulkerBoxesTypes.CRYSTAL, type))));
    OBSIDIAN_SHULKER_BOXES = ImmutableMap.copyOf(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), type -> register("obsidian_shulker_box_" + type.name().toLowerCase(Locale.ROOT), new ObsidianShulkerBoxBlock(REINFORCED, type), IronShulkerBoxesTypes.OBSIDIAN, type))));
  }

  private static <T extends Block> T register(String name, T block, IronShulkerBoxesTypes shulkerBoxesType, @Nullable DyeColor color) {
    T registeredBlock = registerBlock(name, block);
    IronShulkerBoxBlockItem item = new IronShulkerBoxBlockItem(registeredBlock, new Item.Properties(), () -> shulkerBoxesType, color == null ? null : () -> color);
    registerItem(name, item);
    ALL_SHULKER_BOX_BLOCK_ITEMS.add(item);
    return registeredBlock;
  }

  private static <T extends Block> T registerBlock(String name, T block) {
    T out = Registry.register(BuiltInRegistries.BLOCK, IronShulkerBoxes.id(name), block);
    ALL_REGISTERED_BLOCKS.add(out);
    return out;
  }

  private static void registerItem(String name, Item item) {
    Registry.register(BuiltInRegistries.ITEM, IronShulkerBoxes.id(name), item);
    IronShulkerBoxesItems.MOD_ITEMS.add(item);
  }

  public static ImmutableList<Block> allBlocksList() {
    return ImmutableList.copyOf(ALL_REGISTERED_BLOCKS);
  }
}
