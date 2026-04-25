package com.astryxion.ironshulkerbox;

import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.creativetabs.IronShulkerBoxesCreativeTabs;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesBlockTags;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesLanguageProvider;
import com.astryxion.ironshulkerbox.common.data.IronShulkerBoxesRecipeProvider;
import com.astryxion.ironshulkerbox.common.data.loot.IronShulkerBoxesLootTableProvider;
import com.astryxion.ironshulkerbox.common.network.TopStacksSyncPacket;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesMenuTypes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.data.PackOutput;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.transfer.item.ItemAccessItemHandler;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.concurrent.CompletableFuture;

@Mod(IronShulkerBoxes.MODID)
public class IronShulkerBoxes {

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
          case IRON -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get(), 1);
          case GOLD -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get(), 1);
          case DIAMOND -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get(), 1);
          case COPPER -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get(), 1);
          case CRYSTAL -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.get(), 1);
          case OBSIDIAN -> itemStack.transmuteCopy(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.get(), 1);
          case VANILLA -> itemStack.transmuteCopy(Blocks.SHULKER_BOX, 1);
        };
      }

      player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, newItemStack, false));
      player.awardStat(Stats.CLEAN_SHULKER_BOX);

      LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
    }

    return InteractionResult.SUCCESS;
  };

  public IronShulkerBoxes(IEventBus modEventBus) {
    // General mod setup
    modEventBus.addListener(this::setup);
    modEventBus.addListener(this::gatherDataServer);
    modEventBus.addListener(this::gatherDataClient);
    modEventBus.addListener(this::setupPackets);
    modEventBus.addListener(this::registerCapabilities);
    modEventBus.addListener(IronShulkerBoxesCreativeTabs::buildCreativeModeTabContents);
    modEventBus.addListener(IronShulkerBoxesBlockEntityTypes::addValidBlocks);

    // Registry objects
    IronShulkerBoxesBlocks.BLOCKS.register(modEventBus);
    IronShulkerBoxesItems.ITEMS.register(modEventBus);
    IronShulkerBoxesBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
    IronShulkerBoxesMenuTypes.MENU_TYPES.register(modEventBus);
    IronShulkerBoxesRecipes.RECIPE_SERIALIZERS.register(modEventBus);
    IronShulkerBoxesCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
  }

  private void setup(final FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.get().asItem(), new ShulkerBoxDispenseBehavior());

      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get().asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get().asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get().asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get().asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.get().asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.get().asItem(), SHULKER_BOX);

      for (DyeColor color : DyeColor.values()) {
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);
        CauldronInteraction.WATER.map().put(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).get().asItem(), SHULKER_BOX);

        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
        DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).get().asItem(), new ShulkerBoxDispenseBehavior());
      }
    });
  }

  private void gatherDataServer(GatherDataEvent.Server event) {
    addServerDataProviders(event);
  }

  /**
   * Default {@code runData} uses {@code clientData()}, which only posts {@link GatherDataEvent.Client}.
   * Server-only data (recipes, loot, tags) must be registered here too, or those files are never generated.
   */
  private void gatherDataClient(GatherDataEvent.Client event) {
    PackOutput packOutput = event.getGenerator().getPackOutput();
    event.addProvider(new IronShulkerBoxesLanguageProvider(packOutput, "en_us"));
    addServerDataProviders(event);
  }

  private static void addServerDataProviders(GatherDataEvent event) {
    PackOutput packOutput = event.getGenerator().getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    event.addProvider(new IronShulkerBoxesLootTableProvider(packOutput, lookupProvider));
    event.addProvider(new IronShulkerBoxesRecipeProvider.Runner(packOutput, lookupProvider));
    event.addProvider(new IronShulkerBoxesBlockTags(packOutput, lookupProvider));
  }

  public void setupPackets(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

    registrar.playToClient(TopStacksSyncPacket.TYPE, TopStacksSyncPacket.STREAM_CODEC);
  }

  public void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.IRON_SHULKER_BOX.get(), WorldlyContainerWrapper::new);
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.GOLD_SHULKER_BOX.get(), WorldlyContainerWrapper::new);
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.DIAMOND_SHULKER_BOX.get(), WorldlyContainerWrapper::new);
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX.get(), WorldlyContainerWrapper::new);
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.CRYSTAL_SHULKER_BOX.get(), WorldlyContainerWrapper::new);
    event.registerBlockEntity(Capabilities.Item.BLOCK, IronShulkerBoxesBlockEntityTypes.OBSIDIAN_SHULKER_BOX.get(), WorldlyContainerWrapper::new);

    IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.IRON.size), block.get()));
    IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.GOLD.size), block.get()));
    IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.DIAMOND.size), block.get()));
    IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.COPPER.size), block.get()));
    IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.CRYSTAL.size), block.get()));
    IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.forEach((dyeColor, block) -> event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.OBSIDIAN.size), block.get()));

    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.IRON.size), IronShulkerBoxesBlocks.IRON_SHULKER_BOX.get());
    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.GOLD.size), IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.get());
    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.DIAMOND.size), IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.get());
    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.COPPER.size), IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.get());
    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.CRYSTAL.size), IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.get());
    event.registerItem(Capabilities.Item.ITEM, (stack, access) -> new ItemAccessItemHandler(access, DataComponents.CONTAINER, IronShulkerBoxesTypes.OBSIDIAN.size), IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.get());
  }
}
