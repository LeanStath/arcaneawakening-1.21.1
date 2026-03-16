package com.lean.arcaneawakening.util;

import com.lean.arcaneawakening.ArcaneAwakening;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AATags {
    public static final TagKey<Item> AQUA_FOCUS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "aqua_focus"));
    public static final TagKey<Item> SPECTRAL_FOCUS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "spectral_focus"));
}
