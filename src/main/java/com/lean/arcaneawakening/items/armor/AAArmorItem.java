package com.lean.arcaneawakening.items.armor;

import com.lean.arcaneawakening.registries.AAAttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AAArmorItem extends ArmorItem {

    private final DeferredHolder<Attribute, Attribute> spellPowerAttr;
    private final DeferredHolder<Attribute, Attribute> magicResistAttr;
    private final double spellPowerBonus;
    private final double magicResistBonus;

    public AAArmorItem(Holder<ArmorMaterial> material, Type type,
                       DeferredHolder<Attribute, Attribute> spellPowerAttr,
                       DeferredHolder<Attribute, Attribute> magicResistAttr,
                       double spellPowerBonus,
                       double magicResistBonus,
                       Properties properties) {
        super(material, type, properties);
        this.spellPowerAttr = spellPowerAttr;
        this.magicResistAttr = magicResistAttr;
        this.spellPowerBonus = spellPowerBonus;
        this.magicResistBonus = magicResistBonus;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        var base = super.getDefaultAttributeModifiers();
        var builder = ItemAttributeModifiers.builder();

        // Copy base armor modifiers
        base.modifiers().forEach(entry ->
                builder.add(entry.attribute(), entry.modifier(), entry.slot()));

        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(getEquipmentSlot());

        // Spell power bonus
        if (spellPowerBonus != 0) {
            builder.add(
                    spellPowerAttr,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath("arcaneawakening",
                                    "armor_spell_power_" + getType().getName()),
                            spellPowerBonus,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    slot
            );
        }

        // Magic resist bonus
        if (magicResistBonus != 0) {
            builder.add(
                    magicResistAttr,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath("arcaneawakening",
                                    "armor_magic_resist_" + getType().getName()),
                            magicResistBonus,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    slot
            );
        }

        return builder.build();
    }
}