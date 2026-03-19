package com.lean.arcaneawakening.events;

import com.lean.arcaneawakening.ArcaneAwakening;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = ArcaneAwakening.MODID)
public class AASummonEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        // If this entity is tracked as a summon, remove it from the manager
        if (SummonManager.getOwner(entity) != null) {
            SummonManager.removeSummon(entity);
        }
    }
}
