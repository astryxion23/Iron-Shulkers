package com.astryxion.ironshulkerbox.common.jei;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;

@JeiPlugin
public class IronShulkerBoxesJEI implements IModPlugin {

  private static final Identifier ID = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "jei_plugin");

  @Override
  public Identifier getPluginUid() {
    return ID;
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    registration.addRecipes(RecipeTypes.CRAFTING, ShulkerBoxColoringRecipeMaker.createRecipes());
  }
}
