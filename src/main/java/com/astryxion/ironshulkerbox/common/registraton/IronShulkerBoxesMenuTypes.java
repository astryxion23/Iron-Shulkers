package com.astryxion.ironshulkerbox.common.registraton;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.inventory.IronShulkerBoxMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class IronShulkerBoxesMenuTypes {

  public static final MenuType<IronShulkerBoxMenu> IRON_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "iron_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createIronContainer, FeatureFlags.REGISTRY.allFlags()));
  public static final MenuType<IronShulkerBoxMenu> GOLD_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "gold_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createGoldContainer, FeatureFlags.REGISTRY.allFlags()));
  public static final MenuType<IronShulkerBoxMenu> DIAMOND_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "diamond_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createDiamondContainer, FeatureFlags.REGISTRY.allFlags()));
  public static final MenuType<IronShulkerBoxMenu> CRYSTAL_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "crystal_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createCrystalContainer, FeatureFlags.REGISTRY.allFlags()));
  public static final MenuType<IronShulkerBoxMenu> COPPER_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "copper_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createCopperContainer, FeatureFlags.REGISTRY.allFlags()));
  public static final MenuType<IronShulkerBoxMenu> OBSIDIAN_SHULKER_BOX = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "obsidian_shulker_box"), new MenuType<>(IronShulkerBoxMenu::createObsidianContainer, FeatureFlags.REGISTRY.allFlags()));
}
