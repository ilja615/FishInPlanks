package com.github.ilja615.fish_in_planks;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<SimpleParticleType> COD_PARTICLE = PARTICLES.register("cod_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SALMON_PARTICLE = PARTICLES.register("salmon_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TROPICAL_FISH_PARTICLE = PARTICLES.register("tropical_fish_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PUFFERFISH_PARTICLE = PARTICLES.register("pufferfish_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> PIKE_PARTICLE = PARTICLES.register("pike_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LIONFISH_PARTICLE = PARTICLES.register("lionfish_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PERCH_PARTICLE = PARTICLES.register("perch_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> KOI_PARTICLE = PARTICLES.register("koi_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BLOBFISH_PARTICLE = PARTICLES.register("blobfish_particle", () -> new SimpleParticleType(false));
}
