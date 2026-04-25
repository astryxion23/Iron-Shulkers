package com.progwml6.ironshulkerbox;

import com.progwml6.ironshulkerbox.client.model.inventory.IronShulkerBoxItemStackRenderer;
import com.progwml6.ironshulkerbox.client.render.IronShulkerBoxRenderer;
import com.progwml6.ironshulkerbox.client.screen.IronShulkerBoxScreen;
import com.progwml6.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.progwml6.ironshulkerbox.common.block.entity.CopperShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.CrystalShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.DiamondShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.GoldShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.IronShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.block.entity.ObsidianShulkerBoxBlockEntity;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxBlockItem;
import com.progwml6.ironshulkerbox.common.network.IronShulkerBoxesNetwork;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlockEntityTypes;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesBlocks;
import com.progwml6.ironshulkerbox.common.registraton.IronShulkerBoxesMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class IronShulkerBoxesClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    MenuScreens.register(IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, IronShulkerBoxScreen::new);
    MenuScreens.register(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, IronShulkerBoxScreen::new);

    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.IRON_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.GOLD_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.DIAMOND_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.CRYSTAL_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.COPPER_SHULKER_BOX, IronShulkerBoxRenderer::new);
    BlockEntityRenderers.register(IronShulkerBoxesBlockEntityTypes.OBSIDIAN_SHULKER_BOX, IronShulkerBoxRenderer::new);

    for (IronShulkerBoxBlockItem item : IronShulkerBoxesBlocks.ALL_SHULKER_BOX_BLOCK_ITEMS) {
      BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, displayContext, poseStack, buffer, combinedLight, combinedOverlay) -> {
        Supplier<BlockEntity> modelToUse;
        DyeColor dyeColor = item.getColorSupplier() != null ? item.getColorSupplier().get() : null;
        IronShulkerBoxesTypes t = item.getTypeSupplier().get();
        switch (t) {
          case GOLD -> modelToUse = () -> new GoldShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
          case DIAMOND -> modelToUse = () -> new DiamondShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
          case COPPER -> modelToUse = () -> new CopperShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
          case CRYSTAL -> modelToUse = () -> new CrystalShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
          case OBSIDIAN -> modelToUse = () -> new ObsidianShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
          default -> modelToUse = () -> new IronShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesTypes.get(t, dyeColor).defaultBlockState());
        }
        new IronShulkerBoxItemStackRenderer<>(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), modelToUse)
          .renderByItem(stack, displayContext, poseStack, buffer, combinedLight, combinedOverlay);
      });
    }

    IronShulkerBoxesNetwork.registerClient();
  }
}
