package com.github.ilja615.fish_in_planks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.ilja615.fish_in_planks.ModMain.MOD_ID;

@Mod(MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "fish_in_planks";
    public static final Item.Properties ITEM_PROPERTY = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
    public static ModMain INSTANCE;

    public ModMain()
    {
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setupCommon);

        DeferredRegister<Block> blocks = makeRegister(modEventBus, ForgeRegistries.BLOCKS);
        DeferredRegister<Item> items = makeRegister(modEventBus, ForgeRegistries.ITEMS);

        ModBlocks.COD_BARREL = registerBlockAndItem(blocks, items, "cod_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_COD_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.SALMON_BARREL = registerBlockAndItem(blocks, items, "salmon_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_SALMON_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.TROPICAL_FISH_BARREL = registerBlockAndItem(blocks, items, "tropical_fish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_TROPICAL_FISH_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.PUFFERFISH_BARREL = registerBlockAndItem(blocks, items, "pufferfish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_PUFFER_FISH_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));

        if (ModList.get().isLoaded("upgrade_aquatic"))
        {
            ModBlocks.PIKE_BARREL = registerBlockAndItem(blocks, items, "pike_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_SALMON_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
            ModBlocks.LIONFISH_BARREL = registerBlockAndItem(blocks, items, "lionfish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_PUFFER_FISH_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        }
        if (ModList.get().isLoaded("environmental"))
        {
            ModBlocks.KOI_BARREL = registerBlockAndItem(blocks, items, "koi_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_COD_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        }
        if (ModList.get().isLoaded("alexsmobs"))
        {
            ModBlocks.BLOBFISH_BARREL = registerBlockAndItem(blocks, items, "blobfish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_COD_FLOP), block -> new BlockItem(block, ITEM_PROPERTY));
        }
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {
        {
            FireBlock fireblock = (FireBlock) Blocks.FIRE;
            fireblock.setFireInfo(ModBlocks.COD_BARREL.get(), 5, 5);
            fireblock.setFireInfo(ModBlocks.SALMON_BARREL.get(), 5, 5);
            fireblock.setFireInfo(ModBlocks.TROPICAL_FISH_BARREL.get(), 5, 5);
            fireblock.setFireInfo(ModBlocks.PUFFERFISH_BARREL.get(), 5, 5);
            if (ModList.get().isLoaded("upgrade_aquatic"))
            {
                fireblock.setFireInfo(ModBlocks.PIKE_BARREL.get(), 5, 5);
                fireblock.setFireInfo(ModBlocks.LIONFISH_BARREL.get(), 5, 5);
            }
            if (ModList.get().isLoaded("environmental"))
            {
                fireblock.setFireInfo(ModBlocks.KOI_BARREL.get(), 5, 5);
            }
            if (ModList.get().isLoaded("alexsmobs"))
            {
                fireblock.setFireInfo(ModBlocks.BLOBFISH_BARREL.get(), 5, 5);
            }
        }
    }

    static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> makeRegister(IEventBus modBus, IForgeRegistry<T> registry)
    {
        DeferredRegister<T> register = DeferredRegister.create(registry, MOD_ID);
        register.register(modBus);
        return register;
    }

    static <BLOCK extends Block, ITEM extends BlockItem> RegistryObject<BLOCK> registerBlockAndItem(DeferredRegister<Block> blocks, DeferredRegister<Item> items, String name, Supplier<BLOCK> blockSupplier, Function<BLOCK,ITEM> itemFactory)
    {
        RegistryObject<BLOCK> blockObject = blocks.register(name, blockSupplier);
        items.register(name, () -> itemFactory.apply(blockObject.get()));
        return blockObject;
    }
}
