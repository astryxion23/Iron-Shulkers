package com.astryxion.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableMap;
import com.astryxion.ironshulkerbox.IronShulkerBoxes;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxUpgradeItem;
import com.astryxion.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IronShulkerBoxesItems {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronShulkerBoxes.MODID);

  public static final ImmutableMap<IronShulkerBoxesUpgradeType, DeferredItem<IronShulkerBoxUpgradeItem>> UPGRADES = ImmutableMap.copyOf(Arrays.stream(IronShulkerBoxesUpgradeType.values()).collect(Collectors.toMap(Function.identity(), type -> ITEMS.registerItem(type.name().toLowerCase(Locale.ROOT) + "_shulker_box_upgrade", props -> new IronShulkerBoxUpgradeItem(type, props), p -> p.stacksTo(1)))));
}
