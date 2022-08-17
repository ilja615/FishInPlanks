package com.github.ilja615.fish_in_planks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final Item.Properties ITEM_PROPERTY = new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block.Properties BLOCK_PROPERTY = Block.Properties.copy(Blocks.BARREL);
    public static final Block.Properties RANDOMTICK_BLOCK_PROPERTY = BLOCK_PROPERTY.randomTicks();
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MOD_ID);

    public static final RegistryObject<Block> COD_BARREL = registerBlockAndItem("cod_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;
    public static final RegistryObject<Block> SALMON_BARREL= registerBlockAndItem("salmon_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;
    public static final RegistryObject<Block> TROPICAL_FISH_BARREL = registerBlockAndItem("tropical_fish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.TROPICAL_FISH_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;
    public static final RegistryObject<Block> PUFFERFISH_BARREL = registerBlockAndItem("pufferfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.PUFFER_FISH_FLOP, true), block -> new BlockItem(block, ITEM_PROPERTY));;

    public static final RegistryObject<Block> PIKE_BARREL = registerBlockAndItem("pike_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;
    public static final RegistryObject<Block> LIONFISH_BARREL = registerBlockAndItem("lionfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.PUFFER_FISH_FLOP, true), block -> new BlockItem(block, ITEM_PROPERTY));;
    public static final RegistryObject<Block> PERCH_BARREL = registerBlockAndItem("perch_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;

    public static final RegistryObject<Block> KOI_BARREL = registerBlockAndItem("koi_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;

    public static final RegistryObject<Block> BLOBFISH_BARREL = registerBlockAndItem("blobfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));;

    static <BLOCK extends Block, ITEM extends BlockItem> RegistryObject<BLOCK> registerBlockAndItem(String name, Supplier<BLOCK> blockSupplier, Function<BLOCK,ITEM> itemFactory)
    {
        RegistryObject<BLOCK> blockObject = ModBlocks.BLOCKS.register(name, blockSupplier);
        ModBlocks.ITEMS.register(name, () -> itemFactory.apply(blockObject.get()));
        return blockObject;
    }
}
