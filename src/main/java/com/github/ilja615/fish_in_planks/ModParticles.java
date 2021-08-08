package com.github.ilja615.fish_in_planks;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<BasicParticleType> COD_PARTICLE = PARTICLES.register("cod_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SALMON_PARTICLE = PARTICLES.register("salmon_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> TROPICAL_FISH_PARTICLE = PARTICLES.register("tropical_fish_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> PUFFERFISH_PARTICLE = PARTICLES.register("pufferfish_particle", () -> new BasicParticleType(false));

    public static final RegistryObject<BasicParticleType> PIKE_PARTICLE = PARTICLES.register("pike_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> LIONFISH_PARTICLE = PARTICLES.register("lionfish_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> PERCH_PARTICLE = PARTICLES.register("perch_particle", () -> new BasicParticleType(false));

    public static final RegistryObject<BasicParticleType> KOI_PARTICLE = PARTICLES.register("koi_particle", () -> new BasicParticleType(false));

    public static final RegistryObject<BasicParticleType> BLOBFISH_PARTICLE = PARTICLES.register("blobfish_particle", () -> new BasicParticleType(false));
}
