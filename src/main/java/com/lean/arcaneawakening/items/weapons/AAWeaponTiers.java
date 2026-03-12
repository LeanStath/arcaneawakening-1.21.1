package com.lean.arcaneawakening.items.weapons;

import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Supplier;

public class AAWeaponTiers implements Tier, IronsWeaponTier {

    public static AAWeaponTiers BLADE_OF_THE_RUINED_KING = new AAWeaponTiers(2031, 16, -2.3f, 16, BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            () -> Ingredient.of(Items.NETHERITE_SCRAP),
            new AttributeContainer(Attributes.ARMOR, 6, AttributeModifier.Operation.ADD_VALUE),
            new AttributeContainer(AttributeRegistry.SPELL_RESIST, 0.30, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
            new AttributeContainer(ALObjects.Attributes.LIFE_STEAL, 0.10, AttributeModifier.Operation.ADD_VALUE)

        );

    public static AAWeaponTiers PHANTOM_REELER = new AAWeaponTiers(1500, 9, -1.9f, 16, BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            () -> Ingredient.of(Items.NETHERITE_SCRAP),
            new AttributeContainer(ALObjects.Attributes.COLD_DAMAGE, 8.0, AttributeModifier.Operation.ADD_VALUE),
            new AttributeContainer(ALObjects.Attributes.CURRENT_HP_DAMAGE, 0.08, AttributeModifier.Operation.ADD_VALUE),
            new AttributeContainer(ALObjects.Attributes.CRIT_CHANCE, 0.20, AttributeModifier.Operation.ADD_VALUE)
            );



    //private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final TagKey<Block> incorrectBlocksForDrops;
    private final Supplier<Ingredient> repairIngredient;
    private final AttributeContainer[] attributeContainers;

    private AAWeaponTiers(int uses, float damage, float speed, int enchantmentValue, TagKey<Block> incorrectBlocksForDrops, Supplier<Ingredient> repairIngredient, AttributeContainer... attributes) {
        //this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.repairIngredient = repairIngredient;
        this.attributeContainers = attributes;
    }

    @Override
    public AttributeContainer[] getAdditionalAttributes() {
        return this.attributeContainers;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}