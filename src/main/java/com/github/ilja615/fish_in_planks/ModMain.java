package com.github.ilja615.fish_in_planks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
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
    public static final Item.Properties ITEM_PROPERTY = new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Block.Properties BLOCK_PROPERTY = Block.Properties.copy(Blocks.BARREL);
    public static final Block.Properties RANDOMTICK_BLOCK_PROPERTY = BLOCK_PROPERTY.randomTicks();

    public static ModMain INSTANCE;

    public ModMain()
    {
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setupCommon);

        DeferredRegister<Block> blocks = makeRegister(modEventBus, ForgeRegistries.BLOCKS);
        DeferredRegister<Item> items = makeRegister(modEventBus, ForgeRegistries.ITEMS);

        ModBlocks.COD_BARREL = registerBlockAndItem(blocks, items, "cod_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.SALMON_BARREL = registerBlockAndItem(blocks, items, "salmon_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.TROPICAL_FISH_BARREL = registerBlockAndItem(blocks, items, "tropical_fish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.TROPICAL_FISH_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.PUFFERFISH_BARREL = registerBlockAndItem(blocks, items, "pufferfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.PUFFER_FISH_FLOP, true), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.PIKE_BARREL = registerBlockAndItem(blocks, items, "pike_barrel", () -> new FishBarrelBlock(RANDOMTICK_BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.LIONFISH_BARREL = registerBlockAndItem(blocks, items, "lionfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.PUFFER_FISH_FLOP, true), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.PERCH_BARREL = registerBlockAndItem(blocks, items, "perch_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.SALMON_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.KOI_BARREL = registerBlockAndItem(blocks, items, "koi_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));
        ModBlocks.BLOBFISH_BARREL = registerBlockAndItem(blocks, items, "blobfish_barrel", () -> new FishBarrelBlock(BLOCK_PROPERTY, SoundEvents.COD_FLOP, false), block -> new BlockItem(block, ITEM_PROPERTY));

        ModParticles.PARTICLES.register(modEventBus);
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {
        FishBarrelBlock.fillHashMap();

        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(ModBlocks.COD_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.SALMON_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.TROPICAL_FISH_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.PUFFERFISH_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.PIKE_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.LIONFISH_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.PERCH_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.KOI_BARREL.get(), 5, 5);
        fireblock.setFlammable(ModBlocks.BLOBFISH_BARREL.get(), 5, 5);
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

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerParticles(ParticleFactoryRegisterEvent event) {
            Minecraft.getInstance().particleEngine.register(ModParticles.COD_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.SALMON_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.TROPICAL_FISH_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.PUFFERFISH_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.PIKE_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.LIONFISH_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.PERCH_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.KOI_PARTICLE.get(), FishParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.BLOBFISH_PARTICLE.get(), FishParticle.Factory::new);
        }
    }
}
