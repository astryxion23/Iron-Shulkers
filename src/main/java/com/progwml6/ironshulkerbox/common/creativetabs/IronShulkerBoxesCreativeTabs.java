package com.progwml6.ironshulkerbox.common.creativetabs;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IronShulkerBoxesCreativeTabs {

  public static CreativeModeTab IRON_SHULKER_BOX_TAB;

  public static void register() {
    IRON_SHULKER_BOX_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, IronShulkerBoxes.id("ironshulkerbox"), FabricItemGroup.builder()
      .title(Component.translatable("itemGroup.ironshulkerbox"))
      .icon(() -> new ItemStack(IronShulkerBoxesBlocks.IRON_SHULKER_BOX))
      .displayItems((parameters, output) -> {
        for (Item item : IronShulkerBoxesItems.MOD_ITEMS) {
          output.accept(item);
        }
      })
      .build());
  }
}
