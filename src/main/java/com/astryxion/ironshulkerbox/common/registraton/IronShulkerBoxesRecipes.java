package com.astryxion.ironshulkerbox.common.registraton;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.recipes.IronShulkerBoxesColoringRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class IronShulkerBoxesRecipes {

  public static final RecipeSerializer<IronShulkerBoxesColoringRecipe> SHULKER_BOX_COLORING = Registry.register(
      BuiltInRegistries.RECIPE_SERIALIZER,
      Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "shulker_box_coloring"),
      new RecipeSerializer<>(IronShulkerBoxesColoringRecipe.MAP_CODEC, IronShulkerBoxesColoringRecipe.STREAM_CODEC));
}
