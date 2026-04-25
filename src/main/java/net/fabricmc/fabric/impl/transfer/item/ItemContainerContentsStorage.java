package net.fabricmc.fabric.impl.transfer.item;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.Iterator;
import java.util.List;

public class ItemContainerContentsStorage implements SlottedStorage<ItemVariant> {
  private final ContainerItemContext context;
  private final InventoryStorage delegate;

  public ItemContainerContentsStorage(ContainerItemContext context, int size) {
    this.context = context;

    NonNullList<ItemStack> stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    context.getItemVariant().toStack(1).getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(stacks);

    SimpleContainer inventory = new SimpleContainer(stacks.toArray(ItemStack[]::new)) {
      @Override
      public void setChanged() {
        super.setChanged();
        saveToContext();
      }
    };

    this.delegate = InventoryStorage.of(inventory, Direction.UP);
  }

  private void saveToContext() {
    ItemStack containerStack = this.context.getItemVariant().toStack(1);
    NonNullList<ItemStack> stacks = NonNullList.withSize(this.delegate.getSlotCount(), ItemStack.EMPTY);
    for (int i = 0; i < this.delegate.getSlotCount(); i++) {
      stacks.set(i, this.delegate.getSlot(i).getResource().toStack((int) this.delegate.getSlot(i).getAmount()));
    }
    containerStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(stacks));

    try (Transaction tx = Transaction.openOuter()) {
      if (this.context.exchange(ItemVariant.of(containerStack), 1, tx) == 1) {
        tx.commit();
      }
    }
  }

  @Override
  public List<SingleSlotStorage<ItemVariant>> getSlots() {
    return this.delegate.getSlots();
  }

  @Override
  public int getSlotCount() {
    return this.delegate.getSlotCount();
  }

  @Override
  public SingleSlotStorage<ItemVariant> getSlot(int slot) {
    return this.delegate.getSlot(slot);
  }

  @Override
  public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
    return this.delegate.insert(resource, maxAmount, transaction);
  }

  @Override
  public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
    return this.delegate.extract(resource, maxAmount, transaction);
  }

  @Override
  public Iterator<StorageView<ItemVariant>> iterator() {
    return this.delegate.iterator();
  }
}
