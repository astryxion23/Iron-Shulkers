package com.progwml6.ironshulkerbox.common.data;

import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class IronShulkerBoxesLanguageProvider extends FabricLanguageProvider {

  public IronShulkerBoxesLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
    super(dataOutput, "en_us", registries);
  }

  @Override
  public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.IRON);
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.GOLD);
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.DIAMOND);
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.COPPER);
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.CRYSTAL);
    this.addShulkerBox(translationBuilder, IronShulkerBoxesTypes.OBSIDIAN);

    translationBuilder.add(IronShulkerBoxesBlocks.IRON_SHULKER_BOX, "Iron Shulker Box");
    translationBuilder.add(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, "Gold Shulker Box");
    translationBuilder.add(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, "Diamond Shulker Box");
    translationBuilder.add(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, "Copper Shulker Box");
    translationBuilder.add(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, "Crystal Shulker Box");
    translationBuilder.add(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, "Obsidian Shulker Box");

    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.IRON_TO_GOLD), "Iron to Gold Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.GOLD_TO_DIAMOND), "Gold to Diamond Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.COPPER_TO_IRON), "Copper to Iron Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_CRYSTAL), "Diamond to Crystal Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_IRON), "Vanilla to Iron Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.VANILLA_TO_COPPER), "Vanilla to Copper Shulker Box Upgrade");
    translationBuilder.add(IronShulkerBoxesItems.UPGRADES.get(IronShulkerBoxesUpgradeType.DIAMOND_TO_OBSIDIAN), "Diamond to Obsidian Shulker Box Upgrade");

    translationBuilder.add("item.ironshulkerbox.shulker_box_upgrade.upgrade", "Used to upgrade a %s Shulker Box to a %s Shulker Box.");
    translationBuilder.add("item.ironshulkerbox.shulker_box_upgrade.color", "The color of the Shulker Box will stay the same.");

    translationBuilder.add("ironshulkerbox.iron", "Iron");
    translationBuilder.add("ironshulkerbox.gold", "Gold");
    translationBuilder.add("ironshulkerbox.diamond", "Diamond");
    translationBuilder.add("ironshulkerbox.crystal", "Crystal");
    translationBuilder.add("ironshulkerbox.copper", "Copper");
    translationBuilder.add("ironshulkerbox.obsidian", "Obsidian");
    translationBuilder.add("ironshulkerbox.vanilla", "Vanilla");

    translationBuilder.add("itemGroup.ironshulkerbox", "Iron Shulker Boxes");
  }

  public void addShulkerBox(TranslationBuilder translationBuilder, IronShulkerBoxesTypes type) {
    for (DyeColor color : DyeColor.values()) {
      translationBuilder.add("block.ironshulkerbox." + type.getEnglishName().toLowerCase() + "_shulker_box_" + color.getName(), WordUtils.capitalize(color.getName().replace('_', ' ')) + " " + WordUtils.capitalize(type.getEnglishName().toLowerCase()) + " Shulker Box");
    }
  }
}
