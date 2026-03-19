package com.lean.arcaneawakening.effects;

import com.lean.arcaneawakening.registries.AADamageRegistry;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import com.lean.arcaneawakening.ArcaneAwakening;

public class SuffocationEffect extends MagicMobEffect {

    // Slow modifier applied at epic tier (amplifier 4 = spell level 5)
    private static final ResourceLocation SLOW_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "suffocation_slow");

    public SuffocationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            float damage = getDamageForAmplifier(amplifier);
            DamageSource source = serverLevel.damageSources().source(
                    AADamageRegistry.AQUA_MAGIC
            );
            entity.hurt(source, damage);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Deal damage every 20 ticks (once per second)
        return duration % 20 == 0;
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        // Apply slow at epic tier (amplifier 4 = level 5+)
        if (amplifier >= 4) {
            applySlow(entity);
        }
    }

    @Override
    public void onEffectRemoved(LivingEntity entity, int amplifier) {
        // Remove slow when effect ends
        if (amplifier >= 4) {
            removeSlow(entity);
        }
    }

    private void applySlow(LivingEntity entity) {
        var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && !speedAttr.hasModifier(SLOW_MODIFIER_ID)) {
            speedAttr.addTransientModifier(new AttributeModifier(
                    SLOW_MODIFIER_ID,
                    -0.35,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }
    }

    private void removeSlow(LivingEntity entity) {
        var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            speedAttr.removeModifier(SLOW_MODIFIER_ID);
        }
    }

    private float getDamageForAmplifier(int amplifier) {
        // Level 1 = 0.5, scaling up to level 7 = 2.0
        return 0.5f + (amplifier * 0.25f);
    }
}
