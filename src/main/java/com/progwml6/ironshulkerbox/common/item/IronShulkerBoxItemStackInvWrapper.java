package com.progwml6.ironshulkerbox.common.item;

import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import java.util.function.Supplier;

public class IronShulkerBoxItemStackInvWrapper implements Container {

  private final ItemStack stack;
  private final Supplier<IronShulkerBoxesTypes> type;

  private CompoundTag cachedTag;
  private NonNullList<ItemStack> itemStacksCache;

  public IronShulkerBoxItemStackInvWrapper(ItemStack stack, Supplier<IronShulkerBoxesTypes> type) {
    this.stack = stack;
    this.type = type;
  }

  private static boolean canItemStacksStack(ItemStack a, ItemStack b) {
    return !a.isEmpty() && !b.isEmpty() && ItemStack.isSameItemSameTags(a, b);
  }

  private static ItemStack copyStackWithSize(ItemStack stack, int size) {
    if (stack.isEmpty() || size <= 0) {
      return ItemStack.EMPTY;
    }
    ItemStack copy = stack.copy();
    copy.setCount(size);
    return copy;
  }

  @Override
  public int getContainerSize() {
    return this.type.get().size;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemStack : this.getItemList()) {
      if (!itemStack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getItem(int slot) {
    validateSlotIndex(slot);
    return this.getItemList().get(slot);
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    NonNullList<ItemStack> itemStacks = this.getItemList();

    if (amount == 0) {
      return ItemStack.EMPTY;
    }

    validateSlotIndex(slot);

    ItemStack existing = itemStacks.get(slot);

    if (existing.isEmpty()) {
      return ItemStack.EMPTY;
    }

    int toExtract = Math.min(amount, existing.getMaxStackSize());

    if (existing.getCount() <= toExtract) {
      itemStacks.set(slot, ItemStack.EMPTY);
      this.setItemList(itemStacks);
      return existing;
    } else {
      itemStacks.set(slot, copyStackWithSize(existing, existing.getCount() - toExtract));
      this.setItemList(itemStacks);
      return copyStackWithSize(existing, toExtract);
    }
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    validateSlotIndex(slot);
    NonNullList<ItemStack> itemStacks = this.getItemList();
    ItemStack existing = itemStacks.get(slot);
    if (existing.isEmpty()) {
      return ItemStack.EMPTY;
    }
    itemStacks.set(slot, ItemStack.EMPTY);
    this.setItemList(itemStacks);
    return existing;
  }

  @Override
  public void setItem(int slot, ItemStack stack) {
    validateSlotIndex(slot);

    if (!this.isItemValid(slot, stack)) {
      throw new RuntimeException("Invalid stack " + stack + " for slot " + slot + ")");
    }

    NonNullList<ItemStack> itemStacks = this.getItemList();

    itemStacks.set(slot, stack.isEmpty() ? ItemStack.EMPTY : stack.copy());

    this.setItemList(itemStacks);
  }

  @Override
  public void setChanged() {
  }

  @Override
  public boolean stillValid(Player player) {
    return true;
  }

  @Override
  public void clearContent() {
    NonNullList<ItemStack> itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    this.setItemList(itemStacks);
  }

  public boolean isItemValid(int slot, ItemStack stack) {
    return stack.getItem().canFitInsideContainerItems();
  }

  public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
    if (stack.isEmpty()) {
      return ItemStack.EMPTY;
    }

    if (!this.isItemValid(slot, stack)) {
      return stack;
    }

    validateSlotIndex(slot);

    NonNullList<ItemStack> itemStacks = this.getItemList();

    ItemStack existing = itemStacks.get(slot);

    int limit = Math.min(64, stack.getMaxStackSize());

    if (!existing.isEmpty()) {
      if (!canItemStacksStack(stack, existing)) {
        return stack;
      }

      limit -= existing.getCount();
    }

    if (limit <= 0) {
      return stack;
    }

    boolean reachedLimit = stack.getCount() > limit;

    if (!simulate) {
      if (existing.isEmpty()) {
        itemStacks.set(slot, reachedLimit ? copyStackWithSize(stack, limit) : stack.copy());
      } else {
        existing.grow(reachedLimit ? limit : stack.getCount());
      }
      this.setItemList(itemStacks);
    }

    return reachedLimit ? copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
  }

  private void validateSlotIndex(int slot) {
    if (slot < 0 || slot >= this.getContainerSize()) {
      throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.getContainerSize() + ")");
    }
  }

  private NonNullList<ItemStack> getItemList() {
    CompoundTag rootTag = BlockItem.getBlockEntityData(this.stack);

    if (this.cachedTag == null || !this.cachedTag.equals(rootTag)) {
      this.itemStacksCache = this.refreshItemList(rootTag);
    }

    return this.itemStacksCache;
  }

  private NonNullList<ItemStack> refreshItemList(CompoundTag rootTag) {
    NonNullList<ItemStack> itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

    if (rootTag != null && rootTag.contains("Items", CompoundTag.TAG_LIST)) {
      ContainerHelper.loadAllItems(rootTag, itemStacks);
    }

    this.cachedTag = rootTag;

    return itemStacks;
  }

  private void setItemList(NonNullList<ItemStack> itemStacks) {
    CompoundTag existing = BlockItem.getBlockEntityData(this.stack);
    CompoundTag rootTag = ContainerHelper.saveAllItems(existing == null ? new CompoundTag() : existing, itemStacks);

    switch (this.type.get()) {
      case IRON -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.IRON_SHULKER_BOX, rootTag);
      case GOLD -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.GOLD_SHULKER_BOX, rootTag);
      case DIAMOND -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.DIAMOND_SHULKER_BOX, rootTag);
      case COPPER -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX, rootTag);
      case CRYSTAL -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.CRYSTAL_SHULKER_BOX, rootTag);
      case OBSIDIAN -> BlockItem.setBlockEntityData(this.stack, IronShulkerBoxesBlockEntityTypes.OBSIDIAN_SHULKER_BOX, rootTag);
    }

    this.cachedTag = rootTag;
  }
}
