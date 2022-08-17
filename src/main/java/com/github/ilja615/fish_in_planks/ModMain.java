package com.github.ilja615.fish_in_planks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.ilja615.fish_in_planks.ModMain.MOD_ID;

@Mod(MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "fish_in_planks";

    public static ModMain INSTANCE;

    public ModMain()
    {
        INSTANCE = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupCommon);

        ModBlocks.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
    }

    private void setupCommon(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModMain::afterCommonSetup);
    }

    static void afterCommonSetup()
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

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerParticles(RegisterParticleProvidersEvent event) {
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
