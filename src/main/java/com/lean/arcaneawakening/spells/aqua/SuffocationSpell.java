package com.lean.arcaneawakening.spells.aqua;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.registries.AAMobEffectRegistry;
import com.lean.arcaneawakening.registries.AASchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class SuffocationSpell extends AbstractSpell {

    private static final ResourceLocation SPELL_ID =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "suffocation");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(AASchoolRegistry.AQUA_RESOURCE)
            .setMaxLevel(7)
            .setCooldownSeconds(20)
            .build();

    public SuffocationSpell() {
        this.baseManaCost = 30;
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return SPELL_ID;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 24, 0.25f);
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!(playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetData)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        if (!(world instanceof ServerLevel serverLevel)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        var target = targetData.getTarget(serverLevel);

        if (!(target instanceof LivingEntity livingTarget)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        // Block aquatic mobs — they breathe underwater, suffocation makes no sense
        if (livingTarget.getType().is(EntityTypeTags.AQUATIC)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        // Duration scales with spell level
        // Level 1 = 3s, level 7 = 9s
        int durationTicks = getDurationTicks(spellLevel);

        // Amplifier = spellLevel - 1 so level 1 = amplifier 0
        int amplifier = spellLevel - 1;

        livingTarget.addEffect(new MobEffectInstance(
                AAMobEffectRegistry.SUFFOCATION,
                durationTicks,
                amplifier,
                false,
                true,
                true
        ));

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private int getDurationTicks(int spellLevel) {
        // 3 seconds at level 1, +1 second per level, so level 7 = 9 seconds
        return (3 + (spellLevel - 1)) * 20;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        float damagePerSecond = 0.5f + ((spellLevel - 1) * 0.25f);
        int durationSeconds = 3 + (spellLevel - 1);
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", damagePerSecond + "/s"),
                Component.translatable("ui.irons_spellbooks.duration",
                        Utils.stringTruncation(durationSeconds, 1))
        );
    }
}
