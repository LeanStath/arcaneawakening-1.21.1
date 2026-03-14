package com.lean.arcaneawakening.blocks;

import com.lean.arcaneawakening.ArcaneAwakening;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AABlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArcaneAwakening.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
