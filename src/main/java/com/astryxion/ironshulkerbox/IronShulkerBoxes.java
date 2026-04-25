package com.astryxion.ironshulkerbox;

import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.creativetabs.IronShulkerBoxesCreativeTabs;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesBlockTags;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesLanguageProvider;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesRecipeProvider;
import com.astryxion.ironshulkerbox.common.data.loot.IronShulkerBoxesBlockLoot;
import com.astryxion.ironshulkerbox.common.network.TopStacksSyncPacket;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.impl.transfer.item.ItemContainerContentsStorage;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;

public class IronShulkerBoxes implements ModInitializer, DataGeneratorEntrypoint {

  public static final String MODID = "ironshulkerbox";

  private static final CauldronInteraction SHULKER_BOX = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
    Block block = Block.byItem(itemStack.getItem());

    if (!(block instanceof AbstractIronShulkerBoxBlock)) {
      return InteractionResult.PASS;
    }

    if (!level.isClientSide()) {
      IronShulkerBoxesTypes type = AbstractIronShulkerBoxBlock.getTypeFromBlock(block);
      ItemStack newItemStack = itemStack.transmuteCopy(Blocks.SHULKER_BOX, 1);

      if (type != null) {
        newItemStack = switch (type) {
          case IRON -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.IRON_SHULKER_BOX, 1);
          case GOLD -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, 1);
          case DIAMOND -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, 1);
          case COPPER -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, 1);
          case CRYSTAL -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, 1);
          case OBSIDIAN -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, 1);
          case VANILLA -> itemStack.transmuteCopy(Blocks.SHULKER_BOX, 1);
        };
      }

      player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, newItemStack, false));
      player.awardStat(Stats.CLEAN_SHULKER_BOX);

      LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
    }

    return InteractionResult.SUCCESS;
  };

  @Override
  public void onInitialize() {
    PayloadTypeRegistry.playS2C().register(TopStacksSyncPacket.TYPE, TopStacksSyncPacket.STREAM_CODEC);

    // Ensure class loading order: registries and creative tab
    var ignoredBlocks = IronShulkerBoxesBlocks.IRON_SHULKER_BOX;
    var ignoredItems = IronShulkerBoxesItems.UPGRADES;
    var ignoredTab = IronShulkerBoxesCreativeTabs.IRON_SHULKER_BOX_TAB_KEY;

    registerItemStorages();

    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());

    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.asItem(), SHULKER_BOX);
    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.asItem(), SHULKER_BOX);
    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.asItem(), SHULKER_BOX);
    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.asItem(), SHULKER_BOX);
    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.asItem(), SHULKER_BOX);
    CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.asItem(), SHULKER_BOX);

    for (DyeColor color : DyeColor.values()) {
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);

      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
    }
  }

  private static void registerItemStorages() {
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.IRON.size), itemsForType(IronShulkerBoxesBlocks.IRON_SHULKER_BOX, IronShulkerBoxesBlocks.IRON_SHULKER_BOXES));
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.GOLD.size), itemsForType(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES));
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.DIAMOND.size), itemsForType(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES));
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.COPPER.size), itemsForType(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES));
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.CRYSTAL.size), itemsForType(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES));
    ItemStorage.ITEM.registerForItems((stack, ctx) -> new ItemContainerContentsStorage(ctx, IronShulkerBoxesTypes.OBSIDIAN.size), itemsForType(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES));
  }

  private static Item[] itemsForType(Block base, ImmutableMap<DyeColor, ? extends Block> colored) {
    List<Item> list = new ArrayList<>();
    list.add(base.asItem());
    colored.values().forEach(b -> list.add(b.asItem()));
    return list.toArray(Item[]::new);
  }

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(IronShulkerBoxesLanguageProvider::new);
    pack.addProvider(IronShulkerBoxesBlockLoot::new);
    pack.addProvider(IronShulkerBoxesRecipeProvider.Runner::new);
    pack.addProvider(IronShulkerBoxesBlockTags::new);
  }
}
