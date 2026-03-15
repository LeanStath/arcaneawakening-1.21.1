package com.lean.arcaneawakening.registries;


import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

import static io.redspace.ironsspellbooks.damage.ISSDamageTypes.register;

public class AADamageRegistry {
    public static final ResourceKey<DamageType> SPECTRAL_MAGIC = register("spectral_magic");
    public static final ResourceKey<DamageType> AQUA_MAGIC = register("aqua_magic");
    public static void bootstrap(BootstrapContext<DamageType> context){
        context.register(SPECTRAL_MAGIC, new DamageType(SPECTRAL_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
        context.register(AQUA_MAGIC, new DamageType(AQUA_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
    }
}
