package com.astryxion.ironshulkerbox.common.data;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.astryxion.ironshulkerbox.common.recipes.IronShulkerBoxesColoringRecipe;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesRecipeProvider extends RecipeProvider {

  public IronShulkerBoxesRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
    super(registries, output);
  }

  @Override
  public void buildRecipes() {
    this.addDefaultShulkerBoxRecipes();
    this.addColoredShulkerBoxRecipes();
    this.addUpgradesRecipes();

    SpecialRecipeBuilder.special(() -> IronShulkerBoxesColoringRecipe.INSTANCE).save(this.output, recipeKey("shulker_box_coloring"));
  }

  private void addDefaultShulkerBoxRecipes() {
    String color = "default/";
    String group = "ironshulkerbox:shulker_box";

    this.registerCopperBoxRecipe(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, Items.SHULKER_BOX, color, group);
    this.registerIronBoxRecipe(IronShulkerBoxesBlocks.IRON_SHULKER_BOX, IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, Items.SHULKER_BOX, color, group);
    this.registerGoldBoxRecipe(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, IronShulkerBoxesBlocks.IRON_SHULKER_BOX, color, group);
    this.registerDiamondBoxRecipe(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, color, group);
    this.registerCrystalBoxRecipe(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, color, group);
    this.registerObsidianBoxRecipe(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, color, group);
  }

  private void addColoredShulkerBoxRecipes() {
    for (DyeColor color : DyeColor.values()) {
      String colorName = color.name().toLowerCase(Locale.ROOT);
      String folder = colorName + "/";
      String group = "ironshulkerbox:" + colorName + "_shulker_box";

      this.registerCopperBoxRecipe(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color), getShulkerBoxItem(color), folder, group);
      this.registerIronBoxRecipe(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color), getShulkerBoxItem(color), folder, group);
      this.registerGoldBoxRecipe(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color), folder, group);
      this.registerDiamondBoxRecipe(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color), folder, group);
      this.registerCrystalBoxRecipe(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), folder, group);
      this.registerObsidianBoxRecipe(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color), folder, group);
    }
  }

  private void addUpgradesRecipes() {
    String folder = "upgrades/";

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER))
        .define('M', ConventionalItemTags.COPPER_INGOTS)
        .define('S', Items.SHULKER_SHELL)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_copper_ingot", has(ConventionalItemTags.COPPER_INGOTS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON))
        .define('M', ConventionalItemTags.IRON_INGOTS)
        .define('S', Items.SHULKER_SHELL)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON))
        .define('I', ConventionalItemTags.IRON_INGOTS)
        .define('C', ConventionalItemTags.COPPER_INGOTS)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .pattern("IGI")
        .pattern("GCG")
        .pattern("IGI")
        .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD))
        .define('S', ConventionalItemTags.IRON_INGOTS)
        .define('M', ConventionalItemTags.GOLD_INGOTS)
        .pattern("MSM")
        .pattern("MMM")
        .pattern("MMM")
        .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND))
        .define('M', ConventionalItemTags.DIAMOND_GEMS)
        .define('S', ConventionalItemTags.GOLD_INGOTS)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .pattern("GMG")
        .pattern("GSG")
        .pattern("GMG")
        .unlockedBy("has_glass", has(ConventionalItemTags.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN))
        .define('M', Blocks.OBSIDIAN)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .pattern("MGM")
        .pattern("MMM")
        .pattern("MMM")
        .unlockedBy("has_glass", has(ConventionalItemTags.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL))
        .define('S', Blocks.OBSIDIAN)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .pattern("GSG")
        .pattern("GGG")
        .pattern("GGG")
        .unlockedBy("has_glass", has(ConventionalItemTags.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL), folder));
  }

  protected static ResourceKey<Recipe<?>> recipeKey(String path) {
    return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, path));
  }

  protected static ResourceKey<Recipe<?>> prefix(ItemLike item, String pref) {
    Identifier registryName = BuiltInRegistries.ITEM.getResourceKey(item.asItem())
        .map(ResourceKey::identifier)
        .orElseThrow(() -> new IllegalStateException("Could not retrieve registry name for output."));
    return ResourceKey.create(Registries.RECIPE, location(pref + registryName.getPath()));
  }

  private static Identifier location(String id) {
    return Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, id);
  }

  private void registerCopperBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('M', ConventionalItemTags.COPPER_INGOTS)
        .define('S', input)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_copper", has(ConventionalItemTags.COPPER_INGOTS))
        .save(this.output, locationKey("shulkerboxes/" + color + "copper/vanilla_copper_shulker_box"));
  }

  private void registerIronBoxRecipe(ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .define('S', input)
        .define('M', ConventionalItemTags.IRON_INGOTS)
        .pattern("MGM")
        .pattern("GSG")
        .pattern("MGM")
        .unlockedBy("has_gold", has(ConventionalItemTags.IRON_INGOTS))
        .save(this.output, locationKey("shulkerboxes/" + color + "iron/copper_iron_shulker_box"));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('S', inputTwo)
        .define('M', ConventionalItemTags.IRON_INGOTS)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_gold", has(ConventionalItemTags.GOLD_INGOTS))
        .save(this.output, locationKey("shulkerboxes/" + color + "iron/vanilla_iron_shulker_box"));
  }

  private void registerGoldBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('S', input)
        .define('M', ConventionalItemTags.GOLD_INGOTS)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_gold", has(ConventionalItemTags.GOLD_INGOTS))
        .save(this.output, locationKey("shulkerboxes/" + color + "gold/iron_gold_shulker_box"));
  }

  private void registerDiamondBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .define('S', input)
        .define('M', ConventionalItemTags.DIAMOND_GEMS)
        .pattern("GGG")
        .pattern("MSM")
        .pattern("GGG")
        .unlockedBy("has_diamonds", has(ConventionalItemTags.DIAMOND_GEMS))
        .save(this.output, locationKey("shulkerboxes/" + color + "diamond/gold_diamond_shulker_box"));
  }

  private void registerCrystalBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', ConventionalItemTags.GLASS_BLOCKS)
        .define('S', input)
        .pattern("GGG")
        .pattern("GSG")
        .pattern("GGG")
        .unlockedBy("has_glass", has(ConventionalItemTags.GLASS_BLOCKS))
        .save(this.output, locationKey("shulkerboxes/" + color + "crystal/diamond_crystal_shulker_box"));
  }

  private void registerObsidianBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('M', Items.OBSIDIAN)
        .define('S', input)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
        .save(this.output, locationKey("shulkerboxes/" + color + "obsidian/diamond_obsidian_shulker_box"));
  }

  private static ResourceKey<Recipe<?>> locationKey(String id) {
    return ResourceKey.create(Registries.RECIPE, location(id));
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

  public static final class Runner extends FabricRecipeProvider {

    public Runner(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
      super(packOutput, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
      return new IronShulkerBoxesRecipeProvider(registries, output);
    }

    @Override
    public String getName() {
      return "Iron Shulker Boxes Recipes";
    }
  }
}
