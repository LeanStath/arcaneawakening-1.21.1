package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.effects.SuffocationEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AAMobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, ArcaneAwakening.MODID);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

    public static final DeferredHolder<MobEffect, SuffocationEffect> SUFFOCATION =
            MOB_EFFECTS.register("suffocation",
                    () -> new SuffocationEffect(
                            MobEffectCategory.HARMFUL,
                            0x1ca3ec // aqua blue color
                    ));
}