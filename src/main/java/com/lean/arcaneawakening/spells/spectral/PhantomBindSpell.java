package com.lean.arcaneawakening.spells.aqua;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.registries.AASchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


import java.util.List;

public class PhantomBindSpell extends AbstractSpell {

    private static final ResourceLocation SPELL_ID =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "phantom_bind");

    // Duration in ticks (20 ticks = 1 second)
    private static final int BASE_DURATION_TICKS = 20 * 60; // 60 seconds base

    // Base mana cost and multiplier per additional summon
    private static final int BASE_MANA = 40;
    private static final float MANA_MULTIPLIER = 2.0f; // doubles per additional summon

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(AASchoolRegistry.SPECTRAL_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(10)
            .build();

    public PhantomBindSpell() {
        this.baseManaCost = BASE_MANA;
        this.manaCostPerLevel = 0; // we handle mana scaling manually
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 0;
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

    // Scale recast count with spell level
    // getRecastCount returns TOTAL cast count including initial,
    // so level 1 = 1 summon = 0 recasts, level 2 = 2 summons = 1 recast, etc.
    @Override
    public int getRecastCount(int spellLevel, LivingEntity entity) {
        return spellLevel - 1;
    }

    // Mana cost scales exponentially per active summon
    @Override
    public int getManaCost(int spellLevel) {
        return BASE_MANA;
        // Note: dynamic mana is handled in canBeCastedBy override below
    }

    @Override
    public CastResult canBeCastedBy(int spellLevel, CastSource castSource, MagicData playerMagicData, Player player) {
        // Check base conditions first (cooldown, etc.)
        CastResult base = super.canBeCastedBy(spellLevel, castSource, playerMagicData, player);
        if (!base.isSuccess()) return base;

        // Calculate mana cost based on current summon count
        int activeSummons = SummonManager.getSummons(player).size();
        int cost = calculateManaCost(activeSummons);

        if (castSource.consumesMana() && playerMagicData.getMana() < cost
                && !(player.isCreative())) {
            return new CastResult(CastResult.Type.FAILURE,
                    Component.translatable("ui.irons_spellbooks.cast_error_mana",
                            getDisplayName(player)).withStyle(ChatFormatting.RED));
        }

        return new CastResult(CastResult.Type.SUCCESS);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        // Check summon cap — active summons must be less than spell level
        int activeSummons = SummonManager.getSummons(entity).size();
        if (activeSummons >= spellLevel) {
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                        Component.translatable("spell.arcaneawakening.phantom_bind.cap_reached")
                                .withStyle(ChatFormatting.RED), true);
            }
            return false;
        }

        // Raycast for undead target within 16 blocks
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 16, 0.25f);
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

        // Validate target is a living undead mob
        if (!(target instanceof PathfinderMob mob) || !target.getType().is(EntityTypeTags.UNDEAD)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        // Consume dynamic mana cost
        int activeSummons = SummonManager.getSummons(entity).size();
        int cost = calculateManaCost(activeSummons);
        if (castSource.consumesMana() && entity instanceof ServerPlayer serverPlayer) {
            var magicData = MagicData.getPlayerMagicData(serverPlayer);
            magicData.setMana(Math.max(0, magicData.getMana() - cost));
        }

        // Convert the mob to fight for us
        bindMobToPlayer(mob, entity, serverLevel);

        // Register with SummonManager for duration tracking
        SummonManager.setOwner(mob, entity);
        SummonManager.setDuration(mob, BASE_DURATION_TICKS);

        // Visual/audio feedback
        world.broadcastEntityEvent(mob, (byte) 16); // wolf shake effect - visible indication
        mob.playSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS.value(), 0.5f, 1.8f); // eerie high-pitched sound

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private void bindMobToPlayer(PathfinderMob mob, LivingEntity owner, ServerLevel level) {
        mob.setTarget(null);
        clearHostileTargeting(mob);

        mob.goalSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(mob, 1.2f, true));
        mob.goalSelector.addGoal(7, new io.redspace.ironsspellbooks.entity.mobs.goals.GenericFollowOwnerGoal(
                mob, () -> owner, 1.0f, 15, 5, false, 20));

        mob.targetSelector.addGoal(1, new io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal(
                mob, () -> owner));
        mob.targetSelector.addGoal(2, new io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal(
                mob, () -> owner));
        mob.targetSelector.addGoal(3, new io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal(
                mob, () -> owner));
        mob.targetSelector.addGoal(4, new io.redspace.ironsspellbooks.entity.mobs.goals.GenericProtectOwnerTargetGoal(
                mob, () -> owner));
    }

    private void clearHostileTargeting(PathfinderMob mob) {
        mob.targetSelector.getAvailableGoals().clear();
        mob.setTarget(null);
        mob.setLastHurtByMob(null);
    }

    private int calculateManaCost(int activeSummons) {
        // 1st summon = BASE_MANA, 2nd = BASE_MANA * 2, 3rd = BASE_MANA * 4, etc.
        return (int) (BASE_MANA * Math.pow(MANA_MULTIPLIER, activeSummons));
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        int activeSummons = caster != null ? SummonManager.getSummons(caster).size() : 0;
        return List.of(
                Component.translatable("ui.irons_spellbooks.max_summons", spellLevel),
                Component.translatable("spell.arcaneawakening.phantom_bind.next_cost",
                        calculateManaCost(activeSummons))
        );
    }
}