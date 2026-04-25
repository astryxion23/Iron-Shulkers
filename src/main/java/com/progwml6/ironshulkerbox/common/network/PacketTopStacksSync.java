package com.progwml6.ironshulkerbox.common.network;

import com.progwml6.ironshulkerbox.common.block.entity.ICrystalShulkerBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.nbt.NbtOps;

import java.util.stream.IntStream;

public class PacketTopStacksSync implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<PacketTopStacksSync> TYPE = CustomPacketPayload.createType("top_stacks_sync");
  public static final StreamCodec<FriendlyByteBuf, PacketTopStacksSync> STREAM_CODEC = CustomPacketPayload.codec(PacketTopStacksSync::encode, PacketTopStacksSync::decode);

  private final BlockPos blockPos;
  private final NonNullList<ItemStack> topItemStacks;

  public PacketTopStacksSync(BlockPos blockPos, NonNullList<ItemStack> topItemStacks) {
    this.blockPos = blockPos;
    this.topItemStacks = topItemStacks;
  }

  public static void encode(PacketTopStacksSync msg, FriendlyByteBuf buf) {
    buf.writeBlockPos(msg.blockPos);
    buf.writeInt(msg.topItemStacks.size());
    msg.topItemStacks.forEach((stack) -> buf.writeWithCodec(NbtOps.INSTANCE, ItemStack.OPTIONAL_CODEC, stack));
  }

  public static PacketTopStacksSync decode(FriendlyByteBuf buf) {
    BlockPos blockPos = buf.readBlockPos();
    int size = buf.readInt();
    NonNullList<ItemStack> topItemStacks = NonNullList.withSize(size, ItemStack.EMPTY);

    IntStream.range(0, size).forEach(item -> {
      ItemStack itemStack = buf.readWithCodecTrusted(NbtOps.INSTANCE, ItemStack.OPTIONAL_CODEC);
      topItemStacks.set(item, itemStack);
    });

    return new PacketTopStacksSync(blockPos, topItemStacks);
  }

  public static void handle(PacketTopStacksSync msg) {
    ClientLevel level = Minecraft.getInstance().level;

    if (level != null) {
      BlockEntity blockEntity = level.getBlockEntity(msg.blockPos);

      if (blockEntity != null) {
        if (blockEntity instanceof ICrystalShulkerBox crystalShulkerBox) {
          crystalShulkerBox.receiveMessageFromServer(msg.topItemStacks);

          Minecraft.getInstance().levelRenderer.blockChanged(null, msg.blockPos, null, null, 0);
        }
      }
    }
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
