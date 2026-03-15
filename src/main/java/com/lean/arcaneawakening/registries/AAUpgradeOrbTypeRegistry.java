package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AAUpgradeOrbTypeRegistry {

    // ── Spectral ─────────────────────────────────────────────────
    public static final ResourceKey<UpgradeOrbType> SPECTRAL_SPELL_POWER =
            ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY,
                    ArcaneAwakening.id("spectral_power"));

    // ── Aqua ─────────────────────────────────────────────────────
    public static final ResourceKey<UpgradeOrbType> AQUA_SPELL_POWER =
            ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY,
                    ArcaneAwakening.id("aqua_power"));

    public static void bootstrap(BootstrapContext<UpgradeOrbType> context) {
        context.register(SPECTRAL_SPELL_POWER,
                new UpgradeOrbType(
                        AAAttributeRegistry.SPECTRAL_SPELL_POWER,
                        0.05,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        ItemRegistries.SPECTRAL_UPGRADE_ORB
                ));

        context.register(AQUA_SPELL_POWER,
                new UpgradeOrbType(
                        AAAttributeRegistry.AQUA_SPELL_POWER,
                        0.05,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                        ItemRegistries.AQUA_UPGRADE_ORB
                ));
    }
}