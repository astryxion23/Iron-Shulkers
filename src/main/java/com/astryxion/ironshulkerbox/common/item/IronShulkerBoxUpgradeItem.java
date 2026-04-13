package com.astryxion.ironshulkerbox.common.item;

import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class IronShulkerBoxUpgradeItem extends Item {

  private final IronShulkerBoxesUpgradeType type;

  public IronShulkerBoxUpgradeItem(IronShulkerBoxesUpgradeType type, Properties properties) {
    super(properties);
    this.type = type;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Player entityPlayer = context.getPlayer();
    BlockPos blockPos = context.getClickedPos();
    Level world = context.getLevel();
    ItemStack itemStack = context.getItemInHand();
    boolean passed = false;

    if (world.isClientSide()) {
      return InteractionResult.PASS;
    }

    if (entityPlayer == null) {
      return InteractionResult.PASS;
    }

    if (this.type.canUpgrade(IronShulkerBoxesTypes.VANILLA)) {
      if (world.getBlockState(blockPos).getBlock() instanceof ShulkerBoxBlock) {
        passed = true;
      }
    } else {
      if (!(world.getBlockState(blockPos).getBlock() instanceof AbstractIronShulkerBoxBlock block)) {
        passed = true;
      } else {
        if (block.defaultBlockState() == IronShulkerBoxesTypes.get(this.type.source, block.getColor()).defaultBlockState()) {
          passed = true;
        }
      }
    }

    if (!passed) {
      return InteractionResult.PASS;
    }

    BlockEntity blockEntity = world.getBlockEntity(blockPos);

    if (this.type.canUpgrade(IronShulkerBoxesTypes.VANILLA)) {
      if (!(blockEntity instanceof ShulkerBoxBlockEntity)) {
        return InteractionResult.PASS;
      }
    }

    AbstractIronShulkerBoxBlockEntity newShulkerBox = null;
    Component customName = null;
    NonNullList<ItemStack> shulkerBoxContents = NonNullList.withSize(27, ItemStack.EMPTY);
    Direction shulkerBoxFacing;
    DyeColor shulkerBoxColor;
    BlockState iBlockState = IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.defaultBlockState();

    if (blockEntity != null) {
      if (blockEntity instanceof AbstractIronShulkerBoxBlockEntity shulkerBox) {
        BlockState shulkerBoxState = world.getBlockState(blockPos);

        if (AbstractIronShulkerBoxBlockEntity.getOpenCount(world, blockPos) > 0) {
          return InteractionResult.PASS;
        }

        if (!shulkerBox.canOpen(entityPlayer)) {
          return InteractionResult.PASS;
        }

        shulkerBoxContents = shulkerBox.getItems();
        shulkerBoxFacing = shulkerBoxState.getValue(AbstractIronShulkerBoxBlock.FACING);
        customName = shulkerBox.getCustomName();
        shulkerBoxColor = shulkerBox.getColor();
        iBlockState = IronShulkerBoxesTypes.get(this.type.target, shulkerBoxColor).defaultBlockState();
        iBlockState = iBlockState.setValue(AbstractIronShulkerBoxBlock.FACING, shulkerBoxFacing);

        newShulkerBox = this.type.target.makeEntity(blockPos, iBlockState, shulkerBoxColor);
      } else if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBox) {
        BlockState shulkerBoxState = world.getBlockState(blockPos);

        if (!shulkerBox.canOpen(entityPlayer)) {
          return InteractionResult.PASS;
        }

        if (!this.type.canUpgrade(IronShulkerBoxesTypes.VANILLA)) {
          return InteractionResult.PASS;
        }

        shulkerBoxContents = NonNullList.withSize(shulkerBox.getContainerSize(), ItemStack.EMPTY);

        for (int slot = 0; slot < shulkerBoxContents.size(); slot++) {
          shulkerBoxContents.set(slot, shulkerBox.getItem(slot));
        }

        shulkerBoxFacing = shulkerBoxState.getValue(ShulkerBoxBlock.FACING);
        customName = shulkerBox.getCustomName();
        shulkerBoxColor = ((ShulkerBoxBlock) shulkerBoxState.getBlock()).getColor();
        iBlockState = IronShulkerBoxesTypes.get(this.type.target, shulkerBoxColor).defaultBlockState();
        iBlockState = iBlockState.setValue(AbstractIronShulkerBoxBlock.FACING, shulkerBoxFacing);

        shulkerBox.clearContent();

        newShulkerBox = this.type.target.makeEntity(blockPos, iBlockState, shulkerBoxColor);
      }
    }

    if (newShulkerBox == null) {
      return InteractionResult.PASS;
    }

    world.removeBlockEntity(blockPos);
    world.removeBlock(blockPos, false);

    world.setBlock(blockPos, iBlockState, 3);
    world.setBlockEntity(newShulkerBox);

    world.sendBlockUpdated(blockPos, iBlockState, iBlockState, 3);

    BlockEntity tileEntity2 = world.getBlockEntity(blockPos);

    if (tileEntity2 instanceof AbstractIronShulkerBoxBlockEntity upgraded) {
      upgraded.restoreCustomNameAfterUpgrade(customName);

      upgraded.setItems(shulkerBoxContents);
    }

    if (!entityPlayer.getAbilities().instabuild) {
      itemStack.shrink(1);
    }

    return InteractionResult.SUCCESS;
  }

  @Override
  public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, TooltipDisplay pDisplay, Consumer<Component> pTooltip, TooltipFlag pTooltipFlag) {
    Component SOURCE = Component.translatable("ironshulkerbox." + this.type.source.getEnglishName().toLowerCase()).withStyle(ChatFormatting.BOLD);
    Component TARGET = Component.translatable("ironshulkerbox." + this.type.target.getEnglishName().toLowerCase()).withStyle(ChatFormatting.BOLD);

    pTooltip.accept(Component.translatable("item.ironshulkerbox.shulker_box_upgrade.upgrade", SOURCE, TARGET).withStyle(ChatFormatting.DARK_RED));
    pTooltip.accept(Component.translatable("item.ironshulkerbox.shulker_box_upgrade.color").withStyle(ChatFormatting.GOLD));

    super.appendHoverText(pStack, pContext, pDisplay, pTooltip, pTooltipFlag);
  }
}
