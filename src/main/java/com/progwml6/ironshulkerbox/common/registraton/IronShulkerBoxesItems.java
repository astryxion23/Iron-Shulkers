package com.progwml6.ironshulkerbox.common.registraton;

import com.google.common.collect.ImmutableMap;
import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxUpgradeItem;
import com.progwml6.ironshulkerbox.common.item.IronShulkerBoxesUpgradeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IronShulkerBoxesItems {

  public static final List<Item> MOD_ITEMS = new ArrayList<>();

  public static ImmutableMap<IronShulkerBoxesUpgradeType, IronShulkerBoxUpgradeItem> UPGRADES;

  public static void register() {
    UPGRADES = ImmutableMap.copyOf(Arrays.stream(IronShulkerBoxesUpgradeType.values()).collect(Collectors.toMap(Function.identity(), type -> {
      String name = type.name().toLowerCase(Locale.ROOT) + "_shulker_box_upgrade";
      IronShulkerBoxUpgradeItem item = Registry.register(BuiltInRegistries.ITEM, IronShulkerBoxes.id(name), new IronShulkerBoxUpgradeItem(type, new Item.Properties().stacksTo(1)));
      MOD_ITEMS.add(item);
      return item;
    })));
  }
}
