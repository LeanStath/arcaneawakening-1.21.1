package com.lean.arcaneawakening.registries;

import com.google.common.util.concurrent.Striped;
import com.lean.arcaneawakening.ArcaneAwakening;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AABlocksRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArcaneAwakening.MODID);

    private static BlockBehaviour.Properties woodProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS);
    }
    private static BlockBehaviour.Properties logProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG);
    }
    private static BlockBehaviour.Properties strippedlogProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG);
    }
    private static BlockBehaviour.Properties strippedWoodProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD);
    }
    private static BlockBehaviour.Properties woodProps2() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD);
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemRegistries.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    // Wisewood


    public static final DeferredBlock<RotatedPillarBlock> WISEWOOD_LOG =
            BLOCKS.registerBlock("wisewood_log",
                    RotatedPillarBlock::new, logProps());


    public static final DeferredBlock<RotatedPillarBlock> WISEWOOD_STRIPPED_LOG =
            BLOCKS.registerBlock("wisewood_stripped_log",
                    RotatedPillarBlock::new, strippedlogProps());

    public static final DeferredBlock<Block> WISEWOOD_PLANKS =
            BLOCKS.registerSimpleBlock("wisewood_planks", woodProps());

    public static final DeferredBlock<SlabBlock> WISEWOOD_SLAB =
            BLOCKS.registerBlock("wisewood_slab", SlabBlock::new, woodProps());

    public static final DeferredBlock<StairBlock> WISEWOOD_STAIRS =
            BLOCKS.registerBlock("wisewood_stairs", props ->
                    new StairBlock(WISEWOOD_PLANKS.get().defaultBlockState(), props), woodProps());

    public static final DeferredBlock<FenceBlock> WISEWOOD_FENCE =
            BLOCKS.registerBlock("wisewood_fence", FenceBlock::new, woodProps());

    public static final DeferredBlock<FenceGateBlock> WISEWOOD_FENCE_GATE =
            BLOCKS.registerBlock("wisewood_fence_gate", props ->
                    new FenceGateBlock(WoodType.OAK, props), woodProps());

    public static final DeferredBlock<DoorBlock> WISEWOOD_DOOR =
            BLOCKS.registerBlock("wisewood_door", props ->
                    new DoorBlock(BlockSetType.OAK, props), woodProps()
                    .noOcclusion());

    public static final DeferredBlock<TrapDoorBlock> WISEWOOD_TRAPDOOR =
            BLOCKS.registerBlock("wisewood_trapdoor", props ->
                    new TrapDoorBlock(BlockSetType.OAK, props), woodProps()
                    .noOcclusion());

    static {
        registerBlockItem("wisewood_log", WISEWOOD_LOG);
        registerBlockItem("wisewood_stripped_log", WISEWOOD_STRIPPED_LOG);
        registerBlockItem("wisewood_planks", WISEWOOD_PLANKS);
        registerBlockItem("wisewood_slab", WISEWOOD_SLAB);
        registerBlockItem("wisewood_stairs", WISEWOOD_STAIRS);
        registerBlockItem("wisewood_fence", WISEWOOD_FENCE);
        registerBlockItem("wisewood_fence_gate", WISEWOOD_FENCE_GATE);
        registerBlockItem("wisewood_door", WISEWOOD_DOOR);
        registerBlockItem("wisewood_trapdoor", WISEWOOD_TRAPDOOR);
        registerBlockItem("wisewood_stripped", WISEWOOD_STAIRS);
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.getAxeStrippingState(
                    AABlocksRegistry.WISEWOOD_LOG.get().defaultBlockState()
            );
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
