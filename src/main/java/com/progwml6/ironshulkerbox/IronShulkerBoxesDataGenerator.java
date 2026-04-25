package com.progwml6.ironshulkerbox;

import com.progwml6.ironshulkerbox.common.data.IronShulkerBoxesBlockTags;
import com.progwml6.ironshulkerbox.common.data.IronShulkerBoxesLanguageProvider;
import com.progwml6.ironshulkerbox.common.data.IronShulkerBoxesRecipeProvider;
import com.progwml6.ironshulkerbox.common.data.loot.IronShulkerBoxesLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class IronShulkerBoxesDataGenerator implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider((FabricDataOutput output) -> new IronShulkerBoxesLootTableProvider(output));
    pack.addProvider((FabricDataOutput output) -> new IronShulkerBoxesRecipeProvider(output));
    pack.addProvider(IronShulkerBoxesBlockTags::new);
    pack.addProvider((FabricDataOutput output) -> new IronShulkerBoxesLanguageProvider(output, "en_us"));
  }
}
