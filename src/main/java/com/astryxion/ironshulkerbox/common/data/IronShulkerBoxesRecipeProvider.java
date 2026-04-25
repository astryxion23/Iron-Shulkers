package com.astryxion.ironshulkerbox.common.data;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.astryxion.ironshulkerbox.common.recipes.IronShulkerBoxesColoringRecipe;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
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
import net.neoforged.neoforge.common.Tags;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesRecipeProvider extends RecipeProvider {

  public IronShulkerBoxesRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
    super(registries, output);
  }

  @Override
  protected void buildRecipes() {
    this.addDefaultShulkerBoxRecipes();
    this.addColoredShulkerBoxRecipes();
    this.addUpgradesRecipes();

    SpecialRecipeBuilder.special(IronShulkerBoxesColoringRecipe::new).save(this.output, recipeKey("shulker_box_coloring"));
  }

  private void addDefaultShulkerBoxRecipes() {
    String color = "default/";
    String group = "ironshulkerbox:shulker_box";

    this.registerCopperBoxRecipe(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get(), Items.SHULKER_BOX, color, group);
    this.registerIronBoxRecipe(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get(), IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get(), Items.SHULKER_BOX, color, group);
    this.registerGoldBoxRecipe(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get(), IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get(), color, group);
    this.registerDiamondBoxRecipe(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get(), IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get(), color, group);
    this.registerCrystalBoxRecipe(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.get(), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get(), color, group);
    this.registerObsidianBoxRecipe(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.get(), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get(), color, group);
  }

  private void addColoredShulkerBoxRecipes() {
    for (DyeColor color : DyeColor.values()) {
      String colorName = color.name().toLowerCase(Locale.ROOT);
      String folder = colorName + "/";
      String group = "ironshulkerbox:" + colorName + "_shulker_box";

      this.registerCopperBoxRecipe(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).get(), getShulkerBoxItem(color), folder, group);
      this.registerIronBoxRecipe(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).get(), IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).get(), getShulkerBoxItem(color), folder, group);
      this.registerGoldBoxRecipe(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).get(), IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).get(), folder, group);
      this.registerDiamondBoxRecipe(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).get(), IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).get(), folder, group);
      this.registerCrystalBoxRecipe(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).get(), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).get(), folder, group);
      this.registerObsidianBoxRecipe(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).get(), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).get(), folder, group);
    }
  }

  private void addUpgradesRecipes() {
    String folder = "upgrades/";

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER).get())
        .define('M', Tags.Items.INGOTS_COPPER)
        .define('S', Items.SHULKER_SHELL)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON).get())
        .define('M', Tags.Items.INGOTS_IRON)
        .define('S', Items.SHULKER_SHELL)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON).get())
        .define('I', Tags.Items.INGOTS_IRON)
        .define('C', Tags.Items.INGOTS_COPPER)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .pattern("IGI")
        .pattern("GCG")
        .pattern("IGI")
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD).get())
        .define('S', Tags.Items.INGOTS_IRON)
        .define('M', Tags.Items.INGOTS_GOLD)
        .pattern("MSM")
        .pattern("MMM")
        .pattern("MMM")
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND).get())
        .define('M', Tags.Items.GEMS_DIAMOND)
        .define('S', Tags.Items.INGOTS_GOLD)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .pattern("GMG")
        .pattern("GSG")
        .pattern("GMG")
        .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN).get())
        .define('M', Blocks.OBSIDIAN)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .pattern("MGM")
        .pattern("MMM")
        .pattern("MMM")
        .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN).get(), folder));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL).get())
        .define('S', Blocks.OBSIDIAN)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .pattern("GSG")
        .pattern("GGG")
        .pattern("GGG")
        .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
        .save(this.output, prefix(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL).get(), folder));
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
        .define('M', Tags.Items.INGOTS_COPPER)
        .define('S', input)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
        .save(this.output, locationKey("shulkerboxes/" + color + "copper/vanilla_copper_shulker_box"));
  }

  private void registerIronBoxRecipe(ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .define('S', input)
        .define('M', Tags.Items.INGOTS_IRON)
        .pattern("MGM")
        .pattern("GSG")
        .pattern("MGM")
        .unlockedBy("has_gold", has(Tags.Items.INGOTS_IRON))
        .save(this.output, locationKey("shulkerboxes/" + color + "iron/copper_iron_shulker_box"));

    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('S', inputTwo)
        .define('M', Tags.Items.INGOTS_IRON)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD))
        .save(this.output, locationKey("shulkerboxes/" + color + "iron/vanilla_iron_shulker_box"));
  }

  private void registerGoldBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('S', input)
        .define('M', Tags.Items.INGOTS_GOLD)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD))
        .save(this.output, locationKey("shulkerboxes/" + color + "gold/iron_gold_shulker_box"));
  }

  private void registerDiamondBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .define('S', input)
        .define('M', Tags.Items.GEMS_DIAMOND)
        .pattern("GGG")
        .pattern("MSM")
        .pattern("GGG")
        .unlockedBy("has_diamonds", has(Tags.Items.GEMS_DIAMOND))
        .save(this.output, locationKey("shulkerboxes/" + color + "diamond/gold_diamond_shulker_box"));
  }

  private void registerCrystalBoxRecipe(ItemLike result, ItemLike input, String color, String group) {
    ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, result)
        .group(group)
        .define('G', Tags.Items.GLASS_BLOCKS)
        .define('S', input)
        .pattern("GGG")
        .pattern("GSG")
        .pattern("GGG")
        .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
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

  public static final class Runner extends RecipeProvider.Runner {

    public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
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
