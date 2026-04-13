package com.astryxion.ironshulkerbox.client.render.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;

/**
 * Same idea as {@link net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer}, but the texture id is the
 * <em>full</em> sprite path on {@link Sheets#SHULKER_SHEET} (e.g. {@code ironshulkerbox:model/default/shulker_iron}).
 * Vanilla's shulker_box special type uses {@link net.minecraft.client.renderer.SpriteMapper} with prefix
 * {@code entity/shulker/}, which does not match this mod's {@code textures/model/...} layout from 1.21.
 */
public class IronShulkerBoxSpecialRenderer implements NoDataSpecialModelRenderer {

  private final ShulkerBoxRenderer shulkerBoxRenderer;
  private final float openness;
  private final SpriteId sprite;

  public IronShulkerBoxSpecialRenderer(ShulkerBoxRenderer shulkerBoxRenderer, float openness, SpriteId sprite) {
    this.shulkerBoxRenderer = shulkerBoxRenderer;
    this.openness = openness;
    this.sprite = sprite;
  }

  @Override
  public void submit(
      PoseStack poseStack,
      SubmitNodeCollector submitNodeCollector,
      int lightCoords,
      int overlayCoords,
      boolean hasFoil,
      int outlineColor) {
    this.shulkerBoxRenderer.submit(
        poseStack, submitNodeCollector, lightCoords, overlayCoords, this.openness, null, this.sprite, outlineColor);
  }

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    this.shulkerBoxRenderer.getExtents(this.openness, output);
  }

  public record Unbaked(Identifier texture, float openness) implements NoDataSpecialModelRenderer.Unbaked {

    public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
        i -> i.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture),
                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(Unbaked::openness))
            .apply(i, Unbaked::new));

    @Override
    public MapCodec<? extends Unbaked> type() {
      return MAP_CODEC;
    }

    @Override
    public SpecialModelRenderer<Void> bake(SpecialModelRenderer.BakingContext context) {
      SpriteId spriteId = new SpriteId(Sheets.SHULKER_SHEET, this.texture);
      return new IronShulkerBoxSpecialRenderer(new ShulkerBoxRenderer(context), this.openness, spriteId);
    }
  }
}
