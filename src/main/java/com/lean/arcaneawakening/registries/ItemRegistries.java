package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.items.weapons.AAStaffTiers;
import com.lean.arcaneawakening.items.weapons.AAWeaponTiers;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.render.CinderousRarity;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistries {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ArcaneAwakening.MODID);

    // Weapons
    public static final DeferredHolder<Item, Item> BLADE_OF_THE_RUINED_KING = ITEMS.register("blade_of_the_ruined_king",
            () -> new ExtendedSwordItem(AAWeaponTiers.BLADE_OF_THE_RUINED_KING,
                    ItemPropertiesHelper.equipment().
                            rarity(CinderousRarity.CINDEROUS_RARITY_PROXY.getValue()).
                            fireResistant().
                            attributes(ExtendedSwordItem.createAttributes(AAWeaponTiers.BLADE_OF_THE_RUINED_KING))));

    public static final DeferredHolder<Item, Item> PHANTOM_REELER = ITEMS.register("phantom_reeler", () ->
                    new ExtendedSwordItem(AAWeaponTiers.PHANTOM_REELER,
                            ItemPropertiesHelper.equipment(1)
                                    .rarity(Rarity.RARE)
                                    .attributes(ExtendedSwordItem.createAttributes(AAWeaponTiers.PHANTOM_REELER))));

    // Staffs
    public static final DeferredHolder<Item, Item> TIDECALLER = ITEMS.register("tidecaller",
            () -> new StaffItem(ItemPropertiesHelper.equipment(1)
                    .attributes(ExtendedSwordItem.createAttributes(AAStaffTiers.TIDECALLER))
                    .rarity(Rarity.RARE)));

    public static final DeferredHolder<Item, Item> SPECTRAL_BLADE = ITEMS.register("spectral_blade",
            () -> new StaffItem(ItemPropertiesHelper.equipment(1)
                    .attributes(ExtendedSwordItem.createAttributes(AAStaffTiers.SPECTRAL_BlADE))
                    .rarity(Rarity.RARE)));

    // Materials
    public static final DeferredHolder<Item, Item> ARCANE_CRYSTAL = ITEMS.register("arcane_crystal",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
