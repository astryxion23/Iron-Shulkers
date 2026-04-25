package com.astryxion.ironshulkerbox.client.model;

import com.google.common.collect.ImmutableList;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.stream.Stream;

public class IronShulkerBoxesModels {

  public static final List<Identifier> IRON_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("iron", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> GOLD_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("gold", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> DIAMOND_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("diamond", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> COPPER_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("copper", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> CRYSTAL_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("crystal", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> OBSIDIAN_COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((color) -> getShulkerBoxIdentifier("obsidian", color)).collect(ImmutableList.toImmutableList());
  public static final List<Identifier> COLORED_SHULKER_TEXTURE_LOCATION = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map(IronShulkerBoxesModels::getShulkerBoxIdentifier).collect(ImmutableList.toImmutableList());
  public static final Identifier IRON_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_iron");
  public static final Identifier GOLD_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_gold");
  public static final Identifier DIAMOND_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_diamond");
  public static final Identifier COPPER_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_copper");
  public static final Identifier CRYSTAL_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_crystal");
  public static final Identifier OBSIDIAN_SHULKER_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/default/shulker_obsidian");
  public static final Identifier SHULKER_TEXTURE_LOCATION = Identifier.withDefaultNamespace("entity/shulker/shulker");

  private static Identifier getShulkerBoxIdentifier(String typeName, String colorName) {
    return Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "model/" + colorName + "/shulker_" + colorName + "_" + typeName);
  }

  private static Identifier getShulkerBoxIdentifier(String colorName) {
    return Identifier.withDefaultNamespace("entity/shulker/shulker_" + colorName);
  }

  public static Identifier chooseShulkerBoxTexture(IronShulkerBoxesTypes type, int dyeColor) {
    return switch (type) {
      case IRON -> IRON_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      case GOLD -> GOLD_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      case DIAMOND -> DIAMOND_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      case COPPER -> COPPER_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      case CRYSTAL -> CRYSTAL_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      case OBSIDIAN -> OBSIDIAN_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
      default -> COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
    };
  }

  public static Identifier chooseShulkerBoxTexture(IronShulkerBoxesTypes type) {
    return switch (type) {
      case IRON -> IRON_SHULKER_TEXTURE_LOCATION;
      case GOLD -> GOLD_SHULKER_TEXTURE_LOCATION;
      case DIAMOND -> DIAMOND_SHULKER_TEXTURE_LOCATION;
      case COPPER -> COPPER_SHULKER_TEXTURE_LOCATION;
      case CRYSTAL -> CRYSTAL_SHULKER_TEXTURE_LOCATION;
      case OBSIDIAN -> OBSIDIAN_SHULKER_TEXTURE_LOCATION;
      default -> SHULKER_TEXTURE_LOCATION;
    };
  }
}
