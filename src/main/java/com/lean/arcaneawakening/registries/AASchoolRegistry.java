package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AASchoolRegistry extends SchoolRegistry {

    private static final DeferredRegister<SchoolType> AA_SCHOOLS =
            DeferredRegister.create(SCHOOL_REGISTRY_KEY, ArcaneAwakening.MODID);

    // Spectral
    public static final TagKey<Item> SPECTRAL_FOCUS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "spectral_focus"));
    public static final ResourceKey<DamageType> SPECTRAL_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "spectral"));
    public static final ResourceLocation SPECTRAL_RESOURCE =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "spectral");

    // Aqua
    public static final TagKey<Item> AQUA_FOCUS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "aqua_focus"));
    public static final ResourceKey<DamageType> AQUA_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "aqua"));
    public static final ResourceLocation AQUA_RESOURCE =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "aqua");

    // Only one registerSchool method
    private static Supplier<SchoolType> registerSchool(SchoolType type) {
        return AA_SCHOOLS.register(type.getId().getPath(), () -> type);
    }

    public static final Supplier<SchoolType> SPECTRAL = registerSchool(new SchoolType(
            SPECTRAL_RESOURCE,
            SPECTRAL_FOCUS,
            Component.translatable("school.arcaneawakening.spectral")
                    .withStyle(Style.EMPTY.withColor(0xa78bfa)),
            AAAttributeRegistry.SPECTRAL_SPELL_POWER,
            AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.AMETHYST_BLOCK_CHIME),
            SPECTRAL_DAMAGE_TYPE
    ));

    public static final Supplier<SchoolType> AQUA = registerSchool(new SchoolType(
            AQUA_RESOURCE,
            AQUA_FOCUS,
            Component.translatable("school.arcaneawakening.aqua")
                    .withStyle(Style.EMPTY.withColor(0x00bfff)),
            AAAttributeRegistry.AQUA_SPELL_POWER,
            AAAttributeRegistry.AQUA_MAGIC_RESIST,
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.WATER_AMBIENT),
            AQUA_DAMAGE_TYPE
    ));

    public static void register(IEventBus eventBus) {
        AA_SCHOOLS.register(eventBus);
    }
}