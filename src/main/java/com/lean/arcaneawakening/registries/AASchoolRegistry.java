package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.utils.AATags;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class AASchoolRegistry extends SchoolRegistry {

    private static final DeferredRegister<SchoolType> AA_SCHOOLS =
            DeferredRegister.create(SCHOOL_REGISTRY_KEY, ArcaneAwakening.MODID);

    public static final ResourceLocation SPECTRAL_RESOURCE =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "spectral");

    public static final ResourceLocation AQUA_RESOURCE =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "aqua");

    private static Supplier<SchoolType> registerSchool(SchoolType type) {
        return AA_SCHOOLS.register(type.getId().getPath(), () -> type);
    }

    @Nullable
    public static SchoolType getSchoolFromFocus(ItemStack focusStack) {
        for (SchoolType school : REGISTRY) {
            if (school.isFocus(focusStack)) {
                return school;
            }
        }
        return null;
    }

    public static final Supplier<SchoolType> SPECTRAL = registerSchool(new SchoolType(
            SPECTRAL_RESOURCE,
            AATags.SPECTRAL_FOCUS,
            Component.translatable("school.arcaneawakening.spectral")
                    .withStyle(Style.EMPTY.withColor(0xa78bfa)),
            AAAttributeRegistry.SPECTRAL_SPELL_POWER,
            AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.AMETHYST_BLOCK_CHIME),
            AADamageRegistry.SPECTRAL_MAGIC
    ));

    public static final Supplier<SchoolType> AQUA = registerSchool(new SchoolType(
            AQUA_RESOURCE,
            AATags.AQUA_FOCUS,
            Component.translatable("school.arcaneawakening.aqua")
                    .withStyle(Style.EMPTY.withColor(0xa78bfa)),
            //TODO: ^^^^^
            AAAttributeRegistry.AQUA_SPELL_POWER,
            AAAttributeRegistry.AQUA_MAGIC_RESIST,
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.AMETHYST_BLOCK_CHIME),
            //TODO: ^^^^
            AADamageRegistry.AQUA_MAGIC
    ));

    public static void register(IEventBus eventBus) {
        AA_SCHOOLS.register(eventBus);
    }
}