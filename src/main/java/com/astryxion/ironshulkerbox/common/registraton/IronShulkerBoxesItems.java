package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableMap;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxUpgradeItem;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IronShulkerBoxesItems {

  public static final ImmutableMap<IronShulkerBoxesUpgradeType, Item> UPGRADES = ImmutableMap.copyOf(Arrays.stream(IronShulkerBoxesUpgradeType.values()).collect(Collectors.toMap(Function.identity(), IronShulkerBoxesItems::registerUpgrade)));

  private static Item registerUpgrade(IronShulkerBoxesUpgradeType type) {
    String name = type.name().toLowerCase(Locale.ROOT) + "_shulker_box_upgrade";
    Identifier rid = Identifier.fromNamespaceAndPath(IronShulkerBoxes.MODID, name);
    ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, rid);
    return Registry.register(BuiltInRegistries.ITEM, itemKey, new IronShulkerBoxUpgradeItem(type, new Item.Properties().setId(itemKey).stacksTo(1)));
  }
}
