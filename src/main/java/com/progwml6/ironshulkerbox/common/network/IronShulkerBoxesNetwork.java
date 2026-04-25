package com.progwml6.ironshulkerbox.common.network;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class IronShulkerBoxesNetwork {

  public static final ResourceLocation TOP_STACKS_SYNC = IronShulkerBoxes.id("top_stacks_sync");

  public static void registerClient() {
    ClientPlayNetworking.registerGlobalReceiver(TOP_STACKS_SYNC, (client, handler, buf, responseSender) -> {
      PacketTopStacksSync msg = PacketTopStacksSync.decode(buf);
      client.execute(() -> PacketTopStacksSync.handle(msg));
    });
  }
}
