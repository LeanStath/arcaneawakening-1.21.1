package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcaneAwakening.MODID);

    public static final Supplier<CreativeModeTab> AA_ITEMS_TAB = CREATIVE_MODE_TAB.register("aa_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegistries.BLADE_OF_THE_RUINED_KING.get()))
                    .title(Component.translatable("creative_tab.arcaneawakening.items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistries.BLADE_OF_THE_RUINED_KING.get().asItem());
                        output.accept(ItemRegistries.PHANTOM_REELER.get());
                        output.accept(ItemRegistries.SPECTRAL_BLADE.get());
                        output.accept(ItemRegistries.TIDECALLER.get());

                        output.accept(ItemRegistries.ARCANE_CRYSTAL.get());
                        output.accept(ItemRegistries.SPECTRAL_RUNE.get());
                        output.accept(ItemRegistries.AQUA_RUNE.get());
                        output.accept(ItemRegistries.AQUA_UPGRADE_ORB.get());
                        output.accept(ItemRegistries.SPECTRAL_UPGRADE_ORB.get());



                        output.accept(ItemRegistries.AQUA_HELMET.get());
                        output.accept(ItemRegistries.AQUA_CHESTPLATE.get());
                        output.accept(ItemRegistries.AQUA_LEGGINGS.get());
                        output.accept(ItemRegistries.AQUA_BOOTS.get());

                        output.accept(ItemRegistries.SPECTRAL_HELMET.get());
                        output.accept(ItemRegistries.SPECTRAL_CHESTPLATE.get());
                        output.accept(ItemRegistries.SPECTRAL_LEGGINGS.get());
                        output.accept(ItemRegistries.SPECTRAL_BOOTS.get());

                        output.accept(AABlocksRegistry.WISEWOOD_PLANKS.get());
                        output.accept(AABlocksRegistry.WISEWOOD_SLAB.get());
                        output.accept(AABlocksRegistry.WISEWOOD_STAIRS.get());
                        output.accept(AABlocksRegistry.WISEWOOD_FENCE.get());
                        output.accept(AABlocksRegistry.WISEWOOD_FENCE_GATE.get());
                        output.accept(AABlocksRegistry.WISEWOOD_DOOR.get());
                        output.accept(AABlocksRegistry.WISEWOOD_TRAPDOOR.get());
                    })

                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }
}
