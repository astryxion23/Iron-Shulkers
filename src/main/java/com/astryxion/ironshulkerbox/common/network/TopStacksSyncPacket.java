package com.astryxion.ironshulkerbox.common.network;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TopStacksSyncPacket implements CustomPacketPayload {

  public static final Type<TopStacksSyncPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "top_stacks"));
  public static final StreamCodec<RegistryFriendlyByteBuf, TopStacksSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(TopStacksSyncPacket::write, TopStacksSyncPacket::new);

  private final BlockPos blockPos;
  private final NonNullList<ItemStack> topItemStacks;

  public TopStacksSyncPacket(BlockPos blockPos, NonNullList<ItemStack> topItemStacks) {
    this.blockPos = blockPos;
    this.topItemStacks = topItemStacks;
  }

  public TopStacksSyncPacket(RegistryFriendlyByteBuf buf) {
    this.blockPos = buf.readBlockPos();
    List<ItemStack> topItemStacks = ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buf);

    this.topItemStacks = NonNullList.<ItemStack>withSize(topItemStacks.size(), ItemStack.EMPTY);

    for (int i = 0; i < topItemStacks.size(); i++) {
      if (i < this.topItemStacks.size()) {
        this.topItemStacks.set(i, topItemStacks.get(i));
      }
    }
  }

  public void write(RegistryFriendlyByteBuf buf) {
    buf.writeBlockPos(this.blockPos);
    ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buf, this.topItemStacks);
  }

  public BlockPos blockPos() {
    return this.blockPos;
  }

  public NonNullList<ItemStack> topItemStacks() {
    return this.topItemStacks;
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
