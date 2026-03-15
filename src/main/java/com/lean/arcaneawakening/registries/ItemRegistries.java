package com.lean.arcaneawakening.registries;

import com.lean.arcaneawakening.ArcaneAwakening;
import com.lean.arcaneawakening.items.armor.AAArmorItem;
import com.lean.arcaneawakening.items.armor.AquaArmorItem;
import com.lean.arcaneawakening.items.weapons.AAStaffTiers;
import com.lean.arcaneawakening.items.weapons.AAWeaponTiers;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.ironsspellbooks.render.CinderousRarity;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ArcaneAwakening.MODID);

    // Weapons
    public static final DeferredHolder<Item, Item> BLADE_OF_THE_RUINED_KING;
    public static final DeferredHolder<Item, Item> PHANTOM_REELER;
    static {
        BLADE_OF_THE_RUINED_KING = ITEMS.register("blade_of_the_ruined_king",
                () -> new ExtendedSwordItem(AAWeaponTiers.BLADE_OF_THE_RUINED_KING,
                        ItemPropertiesHelper.equipment()
                                .rarity(CinderousRarity.CINDEROUS_RARITY_PROXY.getValue())
                                .fireResistant()
                                .attributes(ExtendedSwordItem.createAttributes(AAWeaponTiers.BLADE_OF_THE_RUINED_KING))));

        PHANTOM_REELER = ITEMS.register("phantom_reeler",
                () -> new ExtendedSwordItem(AAWeaponTiers.PHANTOM_REELER,
                        ItemPropertiesHelper.equipment(1)
                                .rarity(Rarity.RARE)
                                .attributes(ExtendedSwordItem.createAttributes(AAWeaponTiers.PHANTOM_REELER))));
    }

    // Staffs
    public static final DeferredHolder<Item, Item> TIDECALLER;
    public static final DeferredHolder<Item, Item> SPECTRAL_BLADE;
    static {
        TIDECALLER = ITEMS.register("tidecaller",
                () -> new StaffItem(ItemPropertiesHelper.equipment(1)
                        .attributes(ExtendedSwordItem.createAttributes(AAStaffTiers.TIDECALLER))
                        .rarity(Rarity.RARE)));

        SPECTRAL_BLADE = ITEMS.register("spectral_blade",
                () -> new StaffItem(ItemPropertiesHelper.equipment(1)
                        .attributes(ExtendedSwordItem.createAttributes(AAStaffTiers.SPECTRAL_BlADE))
                        .rarity(Rarity.RARE)));
    }

    // Spectral Armor
    public static final DeferredHolder<Item, AAArmorItem> SPECTRAL_HELMET;
    public static final DeferredHolder<Item, AAArmorItem> SPECTRAL_CHESTPLATE;
    public static final DeferredHolder<Item, AAArmorItem> SPECTRAL_LEGGINGS;
    public static final DeferredHolder<Item, AAArmorItem> SPECTRAL_BOOTS;
    static {
        SPECTRAL_HELMET = ITEMS.register("spectral_helmet",
                () -> new AAArmorItem(
                        AAArmorMaterialRegistry.SPECTRAL,
                        ArmorItem.Type.HELMET,
                        AAAttributeRegistry.SPECTRAL_SPELL_POWER,
                        AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
                        0.05, 0.12,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(37))
                ));

        SPECTRAL_CHESTPLATE = ITEMS.register("spectral_chestplate",
                () -> new AAArmorItem(
                        AAArmorMaterialRegistry.SPECTRAL,
                        ArmorItem.Type.CHESTPLATE,
                        AAAttributeRegistry.SPECTRAL_SPELL_POWER,
                        AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
                        0.05, 0.12,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(37))
                ));

        SPECTRAL_LEGGINGS = ITEMS.register("spectral_leggings",
                () -> new AAArmorItem(
                        AAArmorMaterialRegistry.SPECTRAL,
                        ArmorItem.Type.LEGGINGS,
                        AAAttributeRegistry.SPECTRAL_SPELL_POWER,
                        AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
                        0.05, 0.12,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(37))
                ));

        SPECTRAL_BOOTS = ITEMS.register("spectral_boots",
                () -> new AAArmorItem(
                        AAArmorMaterialRegistry.SPECTRAL,
                        ArmorItem.Type.BOOTS,
                        AAAttributeRegistry.SPECTRAL_SPELL_POWER,
                        AAAttributeRegistry.SPECTRAL_MAGIC_RESIST,
                        0.05, 0.12,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(37))
                ));
    }

    // Aqua Armor
    public static final DeferredHolder<Item, AquaArmorItem> AQUA_HELMET;
    public static final DeferredHolder<Item, AquaArmorItem> AQUA_CHESTPLATE;
    public static final DeferredHolder<Item, AquaArmorItem> AQUA_LEGGINGS;
    public static final DeferredHolder<Item, AquaArmorItem> AQUA_BOOTS;
    static {
        AQUA_HELMET = ITEMS.register("aqua_helmet",
                () -> new AquaArmorItem(
                        AAArmorMaterialRegistry.AQUA,
                        ArmorItem.Type.HELMET,
                        AAAttributeRegistry.AQUA_SPELL_POWER,
                        AAAttributeRegistry.AQUA_MAGIC_RESIST,
                        0.08, 0.05,
                        new Item.Properties()
                ));

        AQUA_CHESTPLATE = ITEMS.register("aqua_chestplate",
                () -> new AquaArmorItem(
                        AAArmorMaterialRegistry.AQUA,
                        ArmorItem.Type.CHESTPLATE,
                        AAAttributeRegistry.AQUA_SPELL_POWER,
                        AAAttributeRegistry.AQUA_MAGIC_RESIST,
                        0.08, 0.05,
                        new Item.Properties()
                ));

        AQUA_LEGGINGS = ITEMS.register("aqua_leggings",
                () -> new AquaArmorItem(
                        AAArmorMaterialRegistry.AQUA,
                        ArmorItem.Type.LEGGINGS,
                        AAAttributeRegistry.AQUA_SPELL_POWER,
                        AAAttributeRegistry.AQUA_MAGIC_RESIST,
                        0.08, 0.05,
                        new Item.Properties()
                ));

        AQUA_BOOTS = ITEMS.register("aqua_boots",
                () -> new AquaArmorItem(
                        AAArmorMaterialRegistry.AQUA,
                        ArmorItem.Type.BOOTS,
                        AAAttributeRegistry.AQUA_SPELL_POWER,
                        AAAttributeRegistry.AQUA_MAGIC_RESIST,
                        0.08, 0.05,
                        new Item.Properties()
                ));
    }

    // Materials
    // Materials
    public static final DeferredHolder<Item, Item> ARCANE_CRYSTAL;
    public static final DeferredHolder<Item, Item> SPECTRAL_RUNE;
    public static final DeferredHolder<Item, Item> AQUA_RUNE;
    public static final DeferredHolder<Item, Item> SPECTRAL_UPGRADE_ORB;
    public static final DeferredHolder<Item, Item> AQUA_UPGRADE_ORB;
    static {
        ARCANE_CRYSTAL = ITEMS.register("arcane_crystal",
                () -> new Item(new Item.Properties()));

        SPECTRAL_RUNE = ITEMS.register("spectral_rune",
                () -> new Item(new Item.Properties()));

        AQUA_RUNE = ITEMS.register("aqua_rune",
                () -> new Item(new Item.Properties()));

        SPECTRAL_UPGRADE_ORB = ITEMS.register("spectral_upgrade_orb",
                () -> new UpgradeOrbItem(
                        ItemPropertiesHelper.material()
                                .rarity(Rarity.UNCOMMON)
                                .component(ComponentRegistry.UPGRADE_ORB_TYPE,
                                        AAUpgradeOrbTypeRegistry.SPECTRAL_SPELL_POWER)
                ));

        AQUA_UPGRADE_ORB = ITEMS.register("aqua_upgrade_orb",
                () -> new UpgradeOrbItem(
                        ItemPropertiesHelper.material()
                                .rarity(Rarity.UNCOMMON)
                                .component(ComponentRegistry.UPGRADE_ORB_TYPE,
                                        AAUpgradeOrbTypeRegistry.AQUA_SPELL_POWER)
                ));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
