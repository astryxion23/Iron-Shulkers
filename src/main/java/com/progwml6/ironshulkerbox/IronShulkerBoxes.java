package com.progwml6.ironshulkerbox;

import com.progwml6.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.progwml6.ironshulkerbox.common.creativetabs.IronShulkerBoxesCreativeTabs;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesItems;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesMenuTypes;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxBlockItem;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxItemStackInvWrapper;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxUpgradeItem;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public class IronShulkerBoxes implements ModInitializer {

  public static final String MOD_ID = "ironshulkerbox";

  @SuppressWarnings("UnstableApiUsage")
  public static final ItemApiLookup<Storage<ItemVariant>, ContainerItemContext> SHULKER_BOX_ITEM_STORAGE =
    ItemApiLookup.get(id("shulker_box_item_storage"), Storage.asClass(), ContainerItemContext.class);

  public static ResourceLocation id(String path) {
    return new ResourceLocation(MOD_ID, path);
  }

  private static final CauldronInteraction SHULKER_BOX = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
    Block block = Block.byItem(itemStack.getItem());

    if (!(block instanceof AbstractIronShulkerBoxBlock shulkerBoxBlock)) {
      return InteractionResult.PASS;
    } else {
      if (!level.isClientSide) {
        IronShulkerBoxesTypes type = AbstractIronShulkerBoxBlock.getTypeFromBlock(block);
        ItemStack itemstack = new ItemStack(Blocks.SHULKER_BOX);

        if (type != null) {
          itemstack = switch (type) {
            case IRON -> new ItemStack(IronShulkerBoxesBlocks.IRON_SHULKER_BOX);
            case GOLD -> new ItemStack(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX);
            case DIAMOND -> new ItemStack(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX);
            case COPPER -> new ItemStack(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX);
            case CRYSTAL -> new ItemStack(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX);
            case OBSIDIAN -> new ItemStack(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX);
            case VANILLA -> new ItemStack(Blocks.SHULKER_BOX);
          };
        }

        if (itemStack.hasTag()) {
          itemstack.setTag(itemStack.getTag().copy());
        }

        player.setItemInHand(interactionHand, itemstack);
        player.awardStat(Stats.CLEAN_SHULKER_BOX);

        LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
    }
  };

  @Override
  public void onInitialize() {
    IronShulkerBoxesBlocks.register();
    IronShulkerBoxesBlockEntityTypes.register();
    IronShulkerBoxesItems.register();
    IronShulkerBoxesMenuTypes.register();
    IronShulkerBoxesRecipes.register();
    IronShulkerBoxesCreativeTabs.register();

    registerItemTransferApi();
    UseBlockCallback.EVENT.register(IronShulkerBoxUpgradeItem::handleBlockUseFirst);

    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
    DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());

    for (DyeColor color : DyeColor.values()) {
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);
      CauldronInteraction.WATER.put(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).asItem(), SHULKER_BOX);

      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.IRON_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.GOLD_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.COPPER_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
      DispenserBlock.registerBehavior(IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOXES.get(color).asItem(), new ShulkerBoxDispenseBehavior());
    }
  }

  @SuppressWarnings("UnstableApiUsage")
  private static void registerItemTransferApi() {
    SHULKER_BOX_ITEM_STORAGE.registerForItems((stack, ctx) -> {
      if (!(stack.getItem() instanceof IronShulkerBoxBlockItem blockItem)) {
        return null;
      }
      return InventoryStorage.of(new IronShulkerBoxItemStackInvWrapper(stack, blockItem.getTypeSupplier()), null);
    }, IronShulkerBoxesBlocks.ALL_SHULKER_BOX_BLOCK_ITEMS.toArray(new IronShulkerBoxBlockItem[0]));
  }

}
