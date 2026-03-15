package com.lean.arcaneawakening.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AquaArmorItem extends AAArmorItem {

    public AquaArmorItem(Holder<ArmorMaterial> material, Type type,
                         DeferredHolder<Attribute, Attribute> spellPowerAttr,
                         DeferredHolder<Attribute, Attribute> magicResistAttr,
                         double spellPowerBonus,
                         double magicResistBonus,
                         Properties properties) {
        super(material, type, spellPowerAttr, magicResistAttr,
                spellPowerBonus, magicResistBonus, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide || !(entity instanceof Player player)) return;
        if (isEquippedInCorrectSlot(stack, player) && hasFullSet(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 40, 0, false, false, false));
        }
    }

    private boolean isEquippedInCorrectSlot(ItemStack stack, Player player) {
        return player.getItemBySlot(getEquipmentSlot()) == stack;
    }

    private boolean hasFullSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AquaArmorItem
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AquaArmorItem
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AquaArmorItem
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AquaArmorItem;
    }
}