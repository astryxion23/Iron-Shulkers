package com.astryxion.ironshulkerbox.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.astryxion.ironshulkerbox.client.model.IronShulkerBoxesModels;
import com.astryxion.ironshulkerbox.client.model.inventory.ModelItem;
import com.astryxion.ironshulkerbox.common.block.AbstractIronShulkerBoxBlock;
import com.astryxion.ironshulkerbox.common.block.IronShulkerBoxesTypes;
import com.astryxion.ironshulkerbox.common.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.astryxion.ironshulkerbox.common.block.entity.ICrystalShulkerBox;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

public class IronShulkerBoxRenderer implements BlockEntityRenderer<AbstractIronShulkerBoxBlockEntity, IronShulkerBoxRenderer.IronShulkerBoxRenderState> {

  private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(
    new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F)
  );

  private final ShulkerBoxRenderer shulkerDelegate;
  private final ItemModelResolver itemModelResolver;

  public IronShulkerBoxRenderer(BlockEntityRendererProvider.Context context) {
    this.shulkerDelegate = new ShulkerBoxRenderer(context);
    this.itemModelResolver = context.itemModelResolver();
  }

  @Override
  public IronShulkerBoxRenderState createRenderState() {
    return new IronShulkerBoxRenderState();
  }

  @Override
  public void extractRenderState(AbstractIronShulkerBoxBlockEntity blockEntity, IronShulkerBoxRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);

    renderState.partialTick = partialTick;
    Direction direction = Direction.UP;
    if (blockEntity.hasLevel() && blockEntity.getLevel() != null) {
      BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
      if (blockstate.getBlock() instanceof AbstractIronShulkerBoxBlock) {
        direction = blockstate.getValue(AbstractIronShulkerBoxBlock.FACING);
      }
    }
    renderState.direction = direction;

    IronShulkerBoxesTypes boxType = resolveBoxType(blockEntity);
    DyeColor dyecolor = blockEntity.getColor();
    Identifier textureLocation;
    if (dyecolor == null) {
      textureLocation = IronShulkerBoxesModels.chooseShulkerBoxTexture(boxType);
    } else {
      textureLocation = IronShulkerBoxesModels.chooseShulkerBoxTexture(boxType, dyecolor.getId());
    }
    renderState.spriteId = new SpriteId(Sheets.SHULKER_SHEET, textureLocation);
    renderState.progress = blockEntity.getProgress(partialTick);

    renderState.crystalFloatingItems = boxType.isTransparent()
        && blockEntity instanceof ICrystalShulkerBox
        && Vec3.atCenterOf(blockEntity.getBlockPos()).closerThan(cameraPos, 128.0);

    if (renderState.crystalFloatingItems && blockEntity instanceof ICrystalShulkerBox crystal) {
      for (int j = 0; j < renderState.crystalItemStates.length; j++) {
        ItemStack stack = crystal.getTopItems().get(j);
        ItemStackRenderState stackState = renderState.crystalItemStates[j];
        stackState.clear();
        if (!stack.isEmpty()) {
          this.itemModelResolver.updateForTopItem(stackState, stack, ItemDisplayContext.NONE, blockEntity.getLevel(), null, 0);
        }
      }
    } else {
      for (ItemStackRenderState stackState : renderState.crystalItemStates) {
        stackState.clear();
      }
    }
  }

  private static IronShulkerBoxesTypes resolveBoxType(AbstractIronShulkerBoxBlockEntity pBlockEntity) {
    IronShulkerBoxesTypes boxType = IronShulkerBoxesTypes.IRON;
    IronShulkerBoxesTypes typeFromTileEntity = pBlockEntity.getShulkerBoxType();
    BlockState blockState = pBlockEntity.hasLevel() && pBlockEntity.getLevel() != null
        ? pBlockEntity.getBlockState()
        : pBlockEntity.getBlockToUse().defaultBlockState().setValue(AbstractIronShulkerBoxBlock.FACING, Direction.UP);
    Block block = blockState.getBlock();
    IronShulkerBoxesTypes typeFromBlock = AbstractIronShulkerBoxBlock.getTypeFromBlock(block);

    if (typeFromTileEntity != null) {
      boxType = typeFromTileEntity;
    }

    if (boxType != typeFromBlock || typeFromTileEntity != typeFromBlock) {
      if (typeFromBlock != null) {
        boxType = typeFromBlock;
      }
    }
    return boxType;
  }

  @Override
  public void submit(IronShulkerBoxRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
    poseStack.pushPose();
    poseStack.mulPose(ShulkerBoxRenderer.modelTransform(renderState.direction));
    this.shulkerDelegate.submit(
        poseStack,
        submitNodeCollector,
        renderState.lightCoords,
        OverlayTexture.NO_OVERLAY,
        renderState.progress,
        renderState.breakProgress,
        renderState.spriteId,
        0);
    poseStack.popPose();

    if (renderState.crystalFloatingItems) {
      float rotation = (float) (360.0 * ((System.currentTimeMillis() & 0x3FFFL) / (double) 0x3FFFL)) - renderState.partialTick;
      for (int j = 0; j < MODEL_ITEMS.size() - 1; j++) {
        submitCrystalItem(poseStack, submitNodeCollector, renderState.crystalItemStates[j], MODEL_ITEMS.get(j), rotation, renderState.lightCoords);
      }
    }
  }

  private static void submitCrystalItem(
      PoseStack poseStack,
      SubmitNodeCollector collector,
      ItemStackRenderState itemState,
      ModelItem modelItem,
      float rotation,
      int light) {
    if (itemState.isEmpty()) {
      return;
    }
    poseStack.pushPose();
    Vector3f center = modelItem.getCenter();
    poseStack.translate(center.x(), center.y(), center.z());
    poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
    float scale = modelItem.getSizeScaled();
    poseStack.scale(scale, scale, scale);
    itemState.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, 0);
    poseStack.popPose();
  }

  public static final class IronShulkerBoxRenderState extends BlockEntityRenderState {
    Direction direction = Direction.UP;
    float progress;
    SpriteId spriteId = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION;
    boolean crystalFloatingItems;
    float partialTick;
    final ItemStackRenderState[] crystalItemStates = new ItemStackRenderState[MODEL_ITEMS.size() - 1];

    IronShulkerBoxRenderState() {
      for (int i = 0; i < this.crystalItemStates.length; i++) {
        this.crystalItemStates[i] = new ItemStackRenderState();
      }
    }
  }
}
