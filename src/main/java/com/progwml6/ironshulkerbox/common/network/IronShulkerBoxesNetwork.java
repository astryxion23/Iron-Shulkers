package com.progwml6.ironshulkerbox.common.network;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class IronShulkerBoxesNetwork {

  public static final net.minecraft.resources.ResourceLocation TOP_STACKS_SYNC = IronShulkerBoxes.id("top_stacks_sync");

  public static void registerClient() {
    PayloadTypeRegistry.playS2C().register(PacketTopStacksSync.TYPE, PacketTopStacksSync.STREAM_CODEC);
    ClientPlayNetworking.registerGlobalReceiver(PacketTopStacksSync.TYPE, (msg, context) -> {
      context.client().execute(() -> PacketTopStacksSync.handle(msg));
    });
  }
}
