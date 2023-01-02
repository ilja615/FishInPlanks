package com.github.ilja615.fish_in_planks;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents
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
