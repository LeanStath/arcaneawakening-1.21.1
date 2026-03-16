package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AASpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, ArcaneAwakening.MODID);

   // public static final Supplier<AbstractSpell> RANDOMSPELL = registerSpell(new RandomSpell());

    public static DeferredHolder<AbstractSpell, AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static void register(IEventBus eventBus) { SPELLS.register(eventBus); }




    //public static final Supplier<AbstractSpell> RANDOM_SPELL = registerSpell(new RandomSpell());
}
