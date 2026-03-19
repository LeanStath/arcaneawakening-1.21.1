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
        } else if (event.getSpell() == AASpellRegistry.PHANTOM_BIND_SPELL.get()) {
            event.setDefaultValue(SpellConfigParameter.SCHOOL, AASchoolRegistry.SPECTRAL.get());
            event.setDefaultValue(SpellConfigParameter.MAX_LEVEL, 4);
            event.setDefaultValue(SpellConfigParameter.COOLDOWN_IN_SECONDS, 10.0);
            event.setDefaultValue(SpellConfigParameter.ENABLED, true);
            event.setDefaultValue(SpellConfigParameter.ALLOW_CRAFTING, true);
        } else if (event.getSpell() == AASpellRegistry.SUFFOCATION_SPELL.get()) {
            event.setDefaultValue(SpellConfigParameter.SCHOOL, AASchoolRegistry.AQUA.get());
            event.setDefaultValue(SpellConfigParameter.MAX_LEVEL, 7);
            event.setDefaultValue(SpellConfigParameter.COOLDOWN_IN_SECONDS, 20.0);
            event.setDefaultValue(SpellConfigParameter.ENABLED, true);
            event.setDefaultValue(SpellConfigParameter.ALLOW_CRAFTING, true);{

        }
    }
}
}