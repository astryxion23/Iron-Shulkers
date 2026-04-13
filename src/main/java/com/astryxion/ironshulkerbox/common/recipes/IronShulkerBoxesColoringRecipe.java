package com.astryxion.ironshulkerbox.common.recipes;

import com.mojang.serialization.MapCodec;
import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class IronShulkerBoxesColoringRecipe extends CustomRecipe {

  public static final IronShulkerBoxesColoringRecipe INSTANCE = new IronShulkerBoxesColoringRecipe();

  public static final MapCodec<IronShulkerBoxesColoringRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);

  public static final StreamCodec<RegistryFriendlyByteBuf, IronShulkerBoxesColoringRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

  private IronShulkerBoxesColoringRecipe() {
  }

  /**
   * Used to check if a recipe matches current crafting inventory
   */
  @Override
  public boolean matches(CraftingInput craftingInput, Level p_44325_) {
    int i = 0;
    int j = 0;

    for (int k = 0; k < craftingInput.size(); k++) {
      ItemStack itemstack = craftingInput.getItem(k);
      if (!itemstack.isEmpty()) {
        if (Block.byItem(itemstack.getItem()) instanceof AbstractIronShulkerBoxBlock) {
          i++;
        } else {
          if (!itemstack.is(net.neoforged.neoforge.common.Tags.Items.DYES)) {
            return false;
          }

          j++;
        }

        if (j > 1 || i > 1) {
          return false;
        }
      }
    }

    return i == 1 && j == 1;
  }

  /**
   * Returns an Item that is the result of this recipe
   */
  @Override
  public ItemStack assemble(CraftingInput craftingInput) {
    ItemStack itemStack = ItemStack.EMPTY;
    DyeColor dyeColor = DyeColor.WHITE;

    for (int i = 0; i < craftingInput.size(); i++) {
      ItemStack itemStackInInv = craftingInput.getItem(i);

      if (!itemStackInInv.isEmpty()) {
        Item item = itemStackInInv.getItem();

        if (Block.byItem(item) instanceof AbstractIronShulkerBoxBlock) {
          itemStack = itemStackInInv;
        } else {
          DyeColor tmp = DyeColor.getColor(itemStackInInv);
          if (tmp != null) dyeColor = tmp;
        }
      }
    }

    Block block = AbstractIronShulkerBoxBlock.getBlockByColor(dyeColor, AbstractIronShulkerBoxBlock.getTypeFromItem(itemStack.getItem()));
    return itemStack.transmuteCopy(block, 1);
  }

  @Override
  public RecipeSerializer<? extends CustomRecipe> getSerializer() {
    return IronShulkerBoxesRecipes.SHULKER_BOX_COLORING.get();
  }
}
