package com.lean.arcaneawakening.spells.spectral;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.registries.AASchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhantomBindSpell extends AbstractSpell {

    private static final ResourceLocation SPELL_ID =
            ResourceLocation.fromNamespaceAndPath(ArcaneAwakening.MODID, "phantom_bind");

    private static final int BASE_DURATION_TICKS = 20 * 60;
    private static final int BASE_MANA = 40;
    private static final float MANA_MULTIPLIER = 2.0f;

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(AASchoolRegistry.SPECTRAL_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(10)
            .build();

    public PhantomBindSpell() {
        this.baseManaCost = BASE_MANA;
        this.manaCostPerLevel = 0;
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

    // Total cast count including initial = spell level
    // So level 1 = 1 summon = 0 recasts
    //    level 2 = 2 summons = 1 recast
    //    level 3 = 3 summons = 2 recasts
    //    level 4 = 4 summons = 3 recasts
    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel - 1;
    }

    @Override
    public int getManaCost(int spellLevel) {
        return BASE_MANA;
    }

    @Override
    public CastResult canBeCastedBy(int spellLevel, CastSource castSource, MagicData playerMagicData, Player player) {
        CastResult base = super.canBeCastedBy(spellLevel, castSource, playerMagicData, player);
        if (!base.isSuccess()) return base;

        int activeSummons = SummonManager.getSummons(player).size();
        int cost = calculateManaCost(activeSummons);

        if (castSource.consumesMana() && playerMagicData.getMana() < cost && !player.isCreative()) {
            return new CastResult(CastResult.Type.FAILURE,
                    Component.translatable("ui.irons_spellbooks.cast_error_mana",
                            getDisplayName(player)).withStyle(ChatFormatting.RED));
        }

        return new CastResult(CastResult.Type.SUCCESS);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        int activeSummons = SummonManager.getSummons(entity).size();

        // Check if we have a recast active — if so, check cap against remaining recasts
        boolean hasRecast = playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId());

        if (!hasRecast && activeSummons >= spellLevel) {
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                        Component.translatable("spell.arcaneawakening.phantom_bind.cap_reached")
                                .withStyle(ChatFormatting.RED), true);
            }
            return false;
        }

        // Check cap for recast too
        if (hasRecast && activeSummons >= spellLevel) {
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                        Component.translatable("spell.arcaneawakening.phantom_bind.cap_reached")
                                .withStyle(ChatFormatting.RED), true);
            }
            return false;
        }

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

        if (!(target instanceof PathfinderMob mob) || !target.getType().is(EntityTypeTags.UNDEAD)) {
            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
            return;
        }

        // Consume dynamic mana
        int activeSummons = SummonManager.getSummons(entity).size();
        int cost = calculateManaCost(activeSummons);
        if (castSource.consumesMana() && entity instanceof ServerPlayer serverPlayer) {
            var magicData = MagicData.getPlayerMagicData(serverPlayer);
            magicData.setMana(Math.max(0, magicData.getMana() - cost));
        }

        // Set up recast if this is the first cast and we have more slots
        var recasts = playerMagicData.getPlayerRecasts();
        if (!recasts.hasRecastForSpell(getSpellId()) && spellLevel > 1) {
            recasts.addRecast(new RecastInstance(
                    getSpellId(),
                    spellLevel,
                    getRecastCount(spellLevel, entity),
                    200, // recast window in ticks (10 seconds to recast)
                    castSource,
                    null
            ), playerMagicData);
        }

        // Convert the mob
        bindMobToPlayer(mob, entity, serverLevel);
        SummonManager.setOwner(mob, entity);
        SummonManager.setDuration(mob, BASE_DURATION_TICKS);

        mob.playSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS.value(), 0.5f, 1.8f);

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    private void bindMobToPlayer(PathfinderMob mob, LivingEntity owner, ServerLevel level) {
        mob.setTarget(null);
        clearHostileTargeting(mob);

        // Only add melee attack goal for non-ranged mobs
        // Skeletons, strays, wither skeletons with bows etc. already have
        // their own ranged attack goals — we just need to clear their targeting
        // and let their natural attack AI take over
        if (!(mob instanceof net.minecraft.world.entity.monster.AbstractSkeleton)) {
            mob.goalSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(mob, 1.2f, true));
        }

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