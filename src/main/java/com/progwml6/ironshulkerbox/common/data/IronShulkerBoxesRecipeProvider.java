package com.progwml6.ironshulkerbox.common.data;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.TagKey;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesRecipeProvider extends FabricRecipeProvider {
  private static final TagKey<Item> COPPER_INGOTS = itemTag("copper_ingots");
  private static final TagKey<Item> IRON_INGOTS = itemTag("iron_ingots");
  private static final TagKey<Item> GOLD_INGOTS = itemTag("gold_ingots");
  private static final TagKey<Item> DIAMONDS = itemTag("diamonds");
  private static final TagKey<Item> GLASS_BLOCKS = itemTag("glass_blocks");

  public IronShulkerBoxesRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  @Override
  public void buildRecipes(RecipeOutput output) {
    this.addDefaultShulkerBoxRecipes(output);
    this.addColoredShulkerBoxRecipes(output);

    this.addUpgradesRecipes(output);

    SpecialRecipeBuilder.special((category) -> new com.progwml6.ironshulkerbox.common.recipes.IronShulkerBoxesColoringRecipe(category)).save(output, IronShulkerBoxes.MOD_ID + ":shulker_box_coloring");
  }

  private void addDefaultShulkerBoxRecipes(RecipeOutput output) {
    String color = "default/";
    String group = "ironshulkerbox:shulker_box";

    this.registerCopperBoxRecipe(output, IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, Items.SHULKER_BOX, color, group);
    this.registerIronBoxRecipe(output, IronShulkerBoxesBlocks.IRON_SHULKER_BOX, IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, Items.SHULKER_BOX, color, group);
    this.registerGoldBoxRecipe(output, IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, IronShulkerBoxesBlocks.IRON_SHULKER_BOX, color, group);
    this.registerDiamondBoxRecipe(output, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, color, group);
    this.registerCrystalBoxRecipe(output, IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, color, group);
    this.registerObsidianBoxRecipe(output, IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, color, group);
  }

  private void addColoredShulkerBoxRecipes(RecipeOutput output) {
    for (DyeColor color : DyeColor.values()) {
      String colorName = color.name().toLowerCase(Locale.ROOT);
      String folder = colorName + "/";
      String group = "ironshulkerbox:" + colorName + "_shulker_box";

      this.registerCopperBoxRecipe(output, IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color), getShulkerBoxItem(color), folder, group);
      this.registerIronBoxRecipe(output, IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color), getShulkerBoxItem(color), folder, group);
      this.registerGoldBoxRecipe(output, IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color), folder, group);
      this.registerDiamondBoxRecipe(output, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color), folder, group);
      this.registerCrystalBoxRecipe(output, IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), folder, group);
      this.registerObsidianBoxRecipe(output, IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), folder, group);
    }
  }

  private void addUpgradesRecipes(RecipeOutput output) {
    String folder = "upgrades/";

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER))
      .define('M', COPPER_INGOTS)
      .define('S', Items.SHULKER_SHELL)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_copper_ingot", has(COPPER_INGOTS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON))
      .define('M', IRON_INGOTS)
      .define('S', Items.SHULKER_SHELL)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(IRON_INGOTS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON))
      .define('I', IRON_INGOTS)
      .define('C', COPPER_INGOTS)
      .define('G', GLASS_BLOCKS)
      .pattern("IGI")
      .pattern("GCG")
      .pattern("IGI")
      .unlockedBy("has_iron_ingot", has(IRON_INGOTS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD))
      .define('S', IRON_INGOTS)
      .define('M', GOLD_INGOTS)
      .pattern("MSM")
      .pattern("MMM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(IRON_INGOTS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND))
      .define('M', DIAMONDS)
      .define('S', GOLD_INGOTS)
      .define('G', GLASS_BLOCKS)
      .pattern("GMG")
      .pattern("GSG")
      .pattern("GMG")
      .unlockedBy("has_glass", has(GLASS_BLOCKS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN))
      .define('M', Blocks.OBSIDIAN)
      .define('G', GLASS_BLOCKS)
      .pattern("MGM")
      .pattern("MMM")
      .pattern("MMM")
      .unlockedBy("has_glass", has(GLASS_BLOCKS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL))
      .define('S', Blocks.OBSIDIAN)
      .define('G', GLASS_BLOCKS)
      .pattern("GSG")
      .pattern("GGG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(GLASS_BLOCKS))
      .save(output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL), folder));
  }

  protected static ResourceLocation prefix(ItemLike item, String prefix) {
    ResourceLocation loc = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.asItem()));
    return location(prefix + loc.getPath());
  }

  private static ResourceLocation location(String id) {
    return ResourceLocation.fromNamespaceAndPath(IronShulkerBoxes.MOD_ID, id);
  }

  private static TagKey<Item> itemTag(String path) {
    return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
  }

  private void registerCopperBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('M', COPPER_INGOTS)
      .define('S', input)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_copper", has(COPPER_INGOTS))
      .save(output, location("shulkerboxes/" + color + "copper/vanilla_copper_shulker_box"));
  }

  private void registerIronBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('G', GLASS_BLOCKS)
      .define('S', input)
      .define('M', IRON_INGOTS)
      .pattern("MGM")
      .pattern("GSG")
      .pattern("MGM")
      .unlockedBy("has_gold", has(IRON_INGOTS))
      .save(output, location("shulkerboxes/" + color + "iron/copper_iron_shulker_box"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('S', inputTwo)
      .define('M', IRON_INGOTS)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_gold", has(GOLD_INGOTS))
      .save(output, location("shulkerboxes/" + color + "iron/vanilla_iron_shulker_box"));
  }

  private void registerGoldBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('S', input)
      .define('M', GOLD_INGOTS)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_gold", has(GOLD_INGOTS))
      .save(output, location("shulkerboxes/" + color + "gold/iron_gold_shulker_box"));
  }

  private void registerDiamondBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('G', GLASS_BLOCKS)
      .define('S', input)
      .define('M', DIAMONDS)
      .pattern("GGG")
      .pattern("MSM")
      .pattern("GGG")
      .unlockedBy("has_diamonds", has(DIAMONDS))
      .save(output, location("shulkerboxes/" + color + "diamond/gold_diamond_shulker_box"));
  }

  private void registerCrystalBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('G', GLASS_BLOCKS)
      .define('S', input)
      .pattern("GGG")
      .pattern("GSG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(GLASS_BLOCKS))
      .save(output, location("shulkerboxes/" + color + "crystal/diamond_crystal_shulker_box"));
  }

  private void registerObsidianBoxRecipe(RecipeOutput output, ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
      .group(group)
      .define('M', Items.OBSIDIAN)
      .define('S', input)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
      .save(output, location("shulkerboxes/" + color + "obsidian/diamond_obsidian_shulker_box"));
  }

  private ItemLike getShulkerBoxItem(DyeColor color) {
    return switch (color) {
      case WHITE -> Items.WHITE_SHULKER_BOX;
      case ORANGE -> Items.ORANGE_SHULKER_BOX;
      case MAGENTA -> Items.MAGENTA_SHULKER_BOX;
      case LIGHT_BLUE -> Items.LIGHT_BLUE_SHULKER_BOX;
      case YELLOW -> Items.YELLOW_SHULKER_BOX;
      case LIME -> Items.LIME_SHULKER_BOX;
      case PINK -> Items.PINK_SHULKER_BOX;
      case GRAY -> Items.GRAY_SHULKER_BOX;
      case LIGHT_GRAY -> Items.LIGHT_GRAY_SHULKER_BOX;
      case CYAN -> Items.CYAN_SHULKER_BOX;
      case PURPLE -> Items.PURPLE_SHULKER_BOX;
      case BLUE -> Items.BLUE_SHULKER_BOX;
      case BROWN -> Items.BROWN_SHULKER_BOX;
      case GREEN -> Items.GREEN_SHULKER_BOX;
      case RED -> Items.RED_SHULKER_BOX;
      case BLACK -> Items.BLACK_SHULKER_BOX;
    };
  }
}
