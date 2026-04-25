package com.progwml6.ironshulkerbox.common.jei;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class ShulkerBoxColoringRecipeMaker {

  private static final String group = "ironshulkerboxes.shulker.color";

  public static List<CraftingRecipe> createRecipes() {
    List<CraftingRecipe> list = new ArrayList<>();

    for (IronShulkerBoxesTypes type : IronShulkerBoxesTypes.values()) {
      if (type == IronShulkerBoxesTypes.VANILLA) {
        continue;
      }

      ItemStack baseShulkerStack = new ItemStack(IronShulkerBoxesTypes.get(type, null));
      Ingredient baseShulkerIngredient = Ingredient.of(baseShulkerStack);

      list.addAll(Arrays.stream(DyeColor.values())
        .map(color -> {
          NonNullList<Ingredient> inputs = NonNullList.create();
          inputs.add(baseShulkerIngredient);
          inputs.add(Ingredient.of(new ItemStack(DyeItem.byColor(color))));

          ItemStack output = AbstractIronShulkerBoxBlock.getColoredItemStack(color, AbstractIronShulkerBoxBlock.getTypeFromItem(baseShulkerStack.getItem()));
          ResourceLocation id = new ResourceLocation(IronShulkerBoxes.MOD_ID, "jei_shulker_color_" + type.name().toLowerCase(Locale.ROOT) + "_" + color.getName());
          return new ShapelessRecipe(id, group, CraftingBookCategory.MISC, output, inputs);
        })
        .toList());
    }

    return list;
  }

  private ShulkerBoxColoringRecipeMaker() {
  }
}
