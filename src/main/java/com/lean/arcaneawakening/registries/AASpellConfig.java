package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;

import io.redspace.ironsspellbooks.api.config.ModifyDefaultConfigValuesEvent;
import io.redspace.ironsspellbooks.api.config.SpellConfigParameter;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = ArcaneAwakening.MODID)
public class AASpellConfig {

    @SubscribeEvent
    public static void modifyDefaultConfigs(ModifyDefaultConfigValuesEvent event) {
        if (event.getSpell() == SpellRegistry.SPECTRAL_HAMMER_SPELL.get()) {
            event.setDefaultValue(SpellConfigParameter.SCHOOL, AASchoolRegistry.SPECTRAL.get());
        } else if (event.getSpell() == SpellRegistry.ARROW_VOLLEY_SPELL.get()) {
            event.setDefaultValue(SpellConfigParameter.SCHOOL, AASchoolRegistry.SPECTRAL.get());
        } else if (event.getSpell() == SpellRegistry.SHIELD_SPELL.get()) {
            event.setDefaultValue(SpellConfigParameter.SCHOOL, AASchoolRegistry.SPECTRAL.get());
            {

            }
        }
    }
}