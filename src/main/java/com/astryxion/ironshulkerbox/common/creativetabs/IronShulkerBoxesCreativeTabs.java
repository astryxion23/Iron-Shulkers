package com.astryxion.ironshulkerbox.common.creativetabs;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public class IronShulkerBoxesCreativeTabs {

  public static final ResourceKey<CreativeModeTab> IRON_SHULKER_BOX_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "ironshulkerbox"));

  static {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, IRON_SHULKER_BOX_TAB_KEY, FabricItemGroup.builder()
        .title(Component.translatable("itemGroup.ironshulkerbox"))
        .icon(() -> new ItemStack(IronShulkerBoxesBlocks.IRON_SHULKER_BOX))
        .displayItems((parameters, output) -> {
          // Match NeoForge: upgrades first, then uncolored tiers, then each tier's colors (DyeColor order).
          for (IronShulkerBoxesUpgradeType type : IronShulkerBoxesUpgradeType.values()) {
            output.accept(IronShulkerBoxesItems.UPGRADES.get(type));
          }
          output.accept(IronShulkerBoxesBlocks.IRON_SHULKER_BOX);
          output.accept(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX);
          output.accept(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX);
          output.accept(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX);
          output.accept(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX);
          output.accept(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX);
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color));
          }
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color));
          }
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color));
          }
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color));
          }
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color));
          }
          for (DyeColor color : DyeColor.values()) {
            output.accept(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color));
          }
        })
        .build());
  }

  private IronShulkerBoxesCreativeTabs() {
  }
}
