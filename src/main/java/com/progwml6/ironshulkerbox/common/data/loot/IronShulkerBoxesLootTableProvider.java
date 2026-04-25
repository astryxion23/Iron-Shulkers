package com.progwml6.ironshulkerbox.common.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesLootTableProvider extends LootTableProvider {

  public IronShulkerBoxesLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(IronShulkerBoxesBlockLoot::new, LootContextParamSets.BLOCK)), registries);
  }
}
