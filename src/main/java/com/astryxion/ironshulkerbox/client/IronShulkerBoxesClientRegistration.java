package com.astryxion.ironshulkerbox.client;

import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.client.render.IronShulkerBoxRenderer;
import com.astryxion.ironshulkerbox.client.render.special.IronShulkerBoxSpecialRenderer;
import com.astryxion.ironshulkerbox.client.screen.IronShulkerBoxScreen;
import com.astryxion.ironshulkerbox.common.network.TopStacksSyncPacket;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.astryxion.ironshulkerbox.common.registraton.IronShulkerBoxesMenuTypes;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = IronShulkerBoxes.MODID, value = Dist.CLIENT)
public class IronShulkerBoxesClientRegistration {

  @SubscribeEvent
  public static void registerSpecialItemRenderers(RegisterSpecialModelRendererEvent event) {
    event.register(Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, "shulker_box"), IronShulkerBoxSpecialRenderer.Unbaked.MAP_CODEC);
  }

  @SubscribeEvent
  public static void registerClientPayloads(RegisterClientPayloadHandlersEvent event) {
    event.register(TopStacksSyncPacket.TYPE, TopStacksSyncPacket::handle);
  }

  @SubscribeEvent
  public static void registerScreens(RegisterMenuScreensEvent event) {
    event.register(IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
    event.register(IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
    event.register(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
    event.register(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
    event.register(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
    event.register(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX.get(), IronShulkerBoxScreen::new);
  }

  @SubscribeEvent
  public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.IRON_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.GOLD_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.DIAMOND_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.CRYSTAL_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
    event.registerBlockEntityRenderer(IronShulkerBoxesBlockEntityTypes.OBSIDIAN_SHULKER_BOX.get(), IronShulkerBoxRenderer::new);
  }
}
