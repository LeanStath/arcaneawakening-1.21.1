package com.lean.arcaneawakening.registries;


import com.lean.arcaneawakening.ArcaneAwakening;
import io.redspace.ironsspellbooks.api.attribute.MagicPercentAttribute;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.world.entity.ai.attributes.Attribute;

@EventBusSubscriber(modid = ArcaneAwakening.MODID)
public class AAAttributeRegistry {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ArcaneAwakening.MODID);

    public static void register(IEventBus eventBus) {

        ATTRIBUTES.register(eventBus);
    }

    public static final DeferredHolder<Attribute, Attribute> AQUA_MAGIC_RESIST = newResistanceAttribute("aqua");
    public static final DeferredHolder<Attribute, Attribute> SPECTRAL_MAGIC_RESIST = newResistanceAttribute("spectral");

    public static final DeferredHolder<Attribute, Attribute> AQUA_SPELL_POWER = newPowerAttribute("aqua");
    public static final DeferredHolder<Attribute, Attribute> SPECTRAL_SPELL_POWER =  newPowerAttribute("spectral");



    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute)));
    }

    private static DeferredHolder<Attribute, Attribute> newResistanceAttribute(String id) {
        return (DeferredHolder<Attribute, Attribute>) ATTRIBUTES.register(id + "_magic_resist", () -> (new MagicPercentAttribute("attribute.arcaneawakening." + id + "_magic_resist", 1.0D, -100, 100).setSyncable(true)));
    }

    private static DeferredHolder<Attribute, Attribute> newPowerAttribute(String id) {
        return ATTRIBUTES.register(id + "_spell_power", () -> (new MagicPercentAttribute("attribute.arcaneawakening." + id + "_spell_power", 1.0D, -100, 100).setSyncable(true)));
    }
}