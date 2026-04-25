package com.astryxion.ironshulkerbox.common.creativetabs;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IronShulkerBoxesCreativeTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronShulkerBoxes.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> IRON_SHULKER_BOX_TAB = CREATIVE_MODE_TABS.register("ironshulkerbox", () -> CreativeModeTab.builder()
    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
    .title(Component.translatable("itemGroup.ironshulkerbox"))
    .icon(() -> new ItemStack(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get()))
    .displayItems((parameters, output) -> {
    })
    .build());

  public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (!event.getTabKey().equals(IRON_SHULKER_BOX_TAB.getKey())) {
      return;
    }
    for (var holder : IronShulkerBoxesItems.ITEMS.getEntries()) {
      event.accept(holder.get());
    }
  }
}
