package com.progwml6.ironshulkerbox.common.registraton;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.recipes.IronShulkerBoxesColoringRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class IronShulkerBoxesRecipes {

  public static SimpleCraftingRecipeSerializer<IronShulkerBoxesColoringRecipe> SHULKER_BOX_COLORING;

  public static void register() {
    SHULKER_BOX_COLORING = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, IronShulkerBoxes.id("shulker_box_coloring"), new SimpleCraftingRecipeSerializer<>(IronShulkerBoxesColoringRecipe::new));
  }
}
