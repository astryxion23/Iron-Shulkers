package com.astryxion.ironshulkerbox.client;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.client.render.IronShulkerBoxRenderer;
import com.astryxion.ironshulkerbox.client.render.special.IronShulkerBoxSpecialRenderer;
import com.astryxion.ironshulkerbox.client.screen.IronShulkerBoxScreen;
import com.astryxion.ironshulkerbox.common.block.entity.ICrystalShulkerBox;
import com.astryxion.ironshulkerbox.common.network.TopStacksSyncPacket;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class IronShulkerBoxesClientRegistration implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientPlayNetworking.registerGlobalReceiver(TopStacksSyncPacket.TYPE, IronShulkerBoxesClientRegistration::handleTopStacksSync);

    SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "shulker_box"), IronShulkerBoxSpecialRenderer.Unbaked.MAP_CODEC);

    MenuScreens.register(IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, IronShulkerBoxScreen::new);

    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.IRON_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.GOLD_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.DIAMOND_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.CRYSTAL_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRendererRegistry.register(IronShulkerBoxesBlockEntityTypes.OBSIDIAN_SHULKER_BOX, IronShulkerBoxRenderer::new);
  }

  private static void handleTopStacksSync(TopStacksSyncPacket msg, ClientPlayNetworking.Context ctx) {
    Level level = ctx.player().level();

    BlockEntity blockEntity = level.getBlockEntity(msg.blockPos());

    if (blockEntity != null) {
      if (blockEntity instanceof ICrystalShulkerBox crystalShulkerBox) {
        crystalShulkerBox.receiveMessageFromServer(msg.topItemStacks());

        Minecraft.getInstance().levelRenderer.blockChanged(null, msg.blockPos(), null, null, 0);
      }
    }
  }
}
