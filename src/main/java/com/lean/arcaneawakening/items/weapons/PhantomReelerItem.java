package com.lean.arcaneawakening.items.weapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class PhantomReelerItem extends ExtendedSwordItem {

    // How strong the pull is — higher = more aggressive yank
    private static final double PULL_STRENGTH = 1.8;

    public PhantomReelerItem(AAWeaponTiers tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.fishing != null) {
            // Hook is out
            if (!level.isClientSide) {
                FishingHook hook = player.fishing;
                Entity hookedEntity = hook.getHookedIn();

                if (hookedEntity != null) {
                    // Custom strong pull instead of vanilla's weak one
                    strongPull(player, hookedEntity);
                }

                // Discard the hook without doing vanilla retrieve
                // (vanilla retrieve would try to give fishing loot)
                hook.discard();
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL,
                    1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        } else {
            // Cast the hook
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL,
                    0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (level instanceof ServerLevel) {
                level.addFreshEntity(new FishingHook(player, level, 0, 0));
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void strongPull(Player player, Entity target) {
        // Direction from target toward player
        Vec3 direction = new Vec3(
                player.getX() - target.getX(),
                player.getY() - target.getY(),
                player.getZ() - target.getZ()
        );

        double distance = direction.length();
        if (distance < 0.1) return; // Already basically on top of player

        // Normalize then scale by pull strength
        // We also add a small upward bias so they arc toward player
        // rather than dragging along the ground
        Vec3 pullVelocity = direction.normalize().scale(PULL_STRENGTH)
                .add(0, 0.3, 0);

        target.setDeltaMovement(pullVelocity);
        // Wake up the entity so the velocity actually applies
        target.hasImpulse = true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        if (itemAbility == ItemAbilities.FISHING_ROD_CAST) {
            return true;
        }
        return super.canPerformAction(stack, itemAbility);
    }
}
