package com.progwml6.ironshulkerbox.common.registraton;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.inventory.IronShulkerBoxMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class IronShulkerBoxesMenuTypes {

  public static MenuType<IronShulkerBoxMenu> IRON_SHULKER_BOX;
  public static MenuType<IronShulkerBoxMenu> GOLD_SHULKER_BOX;
  public static MenuType<IronShulkerBoxMenu> DIAMOND_SHULKER_BOX;
  public static MenuType<IronShulkerBoxMenu> CRYSTAL_SHULKER_BOX;
  public static MenuType<IronShulkerBoxMenu> COPPER_SHULKER_BOX;
  public static MenuType<IronShulkerBoxMenu> OBSIDIAN_SHULKER_BOX;

  public static void register() {
    IRON_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("iron_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createIronContainer, FeatureFlags.REGISTRY.allFlags()));
    GOLD_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("gold_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createGoldContainer, FeatureFlags.REGISTRY.allFlags()));
    DIAMOND_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("diamond_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createDiamondContainer, FeatureFlags.REGISTRY.allFlags()));
    CRYSTAL_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("crystal_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createCrystalContainer, FeatureFlags.REGISTRY.allFlags()));
    COPPER_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("copper_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createCopperContainer, FeatureFlags.REGISTRY.allFlags()));
    OBSIDIAN_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, IronShulkerBoxes.id("obsidian_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createObsidianContainer, FeatureFlags.REGISTRY.allFlags()));
  }
}
