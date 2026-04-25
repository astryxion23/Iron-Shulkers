package com.astryxion.ironshulkerbox.common.jei;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ShulkerBoxColoringRecipeMaker {

  private static final String group = "ironshulkerboxes.shulker.color";

  public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
    List<RecipeHolder<CraftingRecipe>> list = new ArrayList<>();

    for (IronShulkerBoxesTypes type : IronShulkerBoxesTypes.values()) {
      if (type == IronShulkerBoxesTypes.VANILLA) {
        continue;
      }

      ItemStack baseShulkerStack = new ItemStack(IronShulkerBoxesTypes.get(type, null));
      Ingredient baseShulkerIngredient = Ingredient.of(baseShulkerStack.getItem());

      list.addAll(Arrays.stream(DyeColor.values())
          .map(color -> {
            Stream<net.minecraft.world.item.Item> dyeItems = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(color.getTag()).spliterator(), false).map(Holder::value);
            Ingredient colorIngredient = Ingredient.of(dyeItems);
            List<Ingredient> inputs = List.of(baseShulkerIngredient, colorIngredient);
            ItemStack output = AbstractIronShulkerBoxBlock.getColoredItemStack(color, AbstractIronShulkerBoxBlock.getTypeFromItem(baseShulkerStack.getItem()));
            Identifier id = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, group + "." + output.getItem().getDescriptionId().replace(':', '/'));
            CraftingRecipe recipe = new ShapelessRecipe(
                group,
                CraftingBookCategory.MISC,
                output,
                inputs);
            ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
            return new RecipeHolder<>(key, recipe);
          })
          .toList());
    }

    return list;
  }

  private ShulkerBoxColoringRecipeMaker() {
  }
}
