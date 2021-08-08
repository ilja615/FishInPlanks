package com.github.ilja615.fish_in_planks;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public class FishBarrelBlock extends DirectionalBlock
{
    private SoundEvent sound;
    private boolean poisonous;

    public static final HashMap<Block, ParticleOptions> BLOCK_I_PARTICLE_DATA_HASH_MAP = new HashMap<>();
    public static final HashMap<Block, Item> BLOCK_COOKED_FISH_ITEM_HASH_MAP = new HashMap<>();


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (BLOCK_COOKED_FISH_ITEM_HASH_MAP.containsKey(this) && random.nextFloat() > 0.8f)
        {
            Block block = level.getBlockState(pos.below
                    (
                            level.getBlockState(pos.below()).getMaterial() == Material.AIR ? 2 : 1
                    )).getBlock();

            if (block instanceof BaseFireBlock || block instanceof CampfireBlock)
            {
                ItemStack itemStack = new ItemStack(BLOCK_COOKED_FISH_ITEM_HASH_MAP.get(this), 5 + random.nextInt(4));
                level.removeBlock(pos, false);
                level.addFreshEntity(new ItemEntity(level, pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d, itemStack));
                level.addParticle(ParticleTypes.LAVA, pos.getX()+0.5d+r(random), pos.getY(), pos.getZ()+0.5d+r(random), 0.0d, 0.0d, 0.0d);
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX()+0.5d+r(random), pos.getY(), pos.getZ()+0.5d+r(random), 0.0d, 0.0d, 0.0d);
                return;
            }
        }
        super.randomTick(state, level, pos, random);
    }

    public FishBarrelBlock(Properties properties, SoundEvent sound, boolean poisonous)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
        this.sound = sound;
        this.poisonous = poisonous;
    }

    public static void fillHashMap()
    {
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.COD_BARREL.get(), ModParticles.COD_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.SALMON_BARREL.get(), ModParticles.SALMON_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.TROPICAL_FISH_BARREL.get(), ModParticles.TROPICAL_FISH_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.PUFFERFISH_BARREL.get(), ModParticles.PUFFERFISH_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.PIKE_BARREL.get(), ModParticles.PIKE_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.LIONFISH_BARREL.get(), ModParticles.LIONFISH_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.PERCH_BARREL.get(), ModParticles.PERCH_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.KOI_BARREL.get(), ModParticles.KOI_PARTICLE.get());
        BLOCK_I_PARTICLE_DATA_HASH_MAP.put(ModBlocks.BLOBFISH_BARREL.get(), ModParticles.BLOBFISH_PARTICLE.get());

        BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(ModBlocks.COD_BARREL.get(), Items.COOKED_COD);
        BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(ModBlocks.SALMON_BARREL.get(), Items.COOKED_SALMON);
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation("upgrade_aquatic:cooked_pike")))
            BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(ModBlocks.PIKE_BARREL.get(), ForgeRegistries.ITEMS.getValue(new ResourceLocation("upgrade_aquatic:cooked_pike")));
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation("upgrade_aquatic:cooked_lionfish")))
            BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(ModBlocks.LIONFISH_BARREL.get(), ForgeRegistries.ITEMS.getValue(new ResourceLocation("upgrade_aquatic:cooked_lionfish")));
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation("upgrade_aquatic:cooked_perch")))
            BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(ModBlocks.PERCH_BARREL.get(), ForgeRegistries.ITEMS.getValue(new ResourceLocation("upgrade_aquatic:cooked_perch")));
    }

    protected static final VoxelShape FISH_BARREL_EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_WEST_AABB = Block.box(1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D);
    protected static final VoxelShape FISH_BARREL_NORTH_AABB = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_UP_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_DOWN_AABB = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        switch(state.getValue(FACING)) {
            case DOWN:
                return FISH_BARREL_DOWN_AABB;
            case UP:
                return FISH_BARREL_UP_AABB;
            case NORTH:
                return FISH_BARREL_NORTH_AABB;
            case SOUTH:
                return FISH_BARREL_SOUTH_AABB;
            case WEST:
                return FISH_BARREL_WEST_AABB;
            case EAST:
                return FISH_BARREL_EAST_AABB;
        }
        return FISH_BARREL_UP_AABB;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn)
    {
        Random random = new Random();
        entityIn.setDeltaMovement(new Vec3(entityIn.getDeltaMovement().x()*0.5,entityIn.getDeltaMovement().y(),entityIn.getDeltaMovement().z()*0.5));
        if (!level.isClientSide )
        {
            double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
            double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
            double d2 = Math.abs(entityIn.getY() - entityIn.yOld);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F || d2 >= (double)0.003F)
            {
                if (random.nextInt(16)==0) {
                    level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                }
            }
            if (this.poisonous)
            {
                if (entityIn instanceof LivingEntity && !((LivingEntity) entityIn).hasEffect(MobEffects.POISON))
                {
                    ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.POISON, 200, 2));
                }
            }
        }
    }

    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
        return false;
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (level.getBlockState(pos).getValue(FACING) == Direction.UP)
        {
            if (fallDistance > 3.0f)
            {
                double ySpeed = Math.min(fallDistance / 16.0d, 0.5d);
                for (int c = 0; c < 2 + level.random.nextInt(3); c++)
                {
                    level.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX() + 0.5d + r(level.random), pos.getY() + 1.0d, pos.getZ() + 0.5d + r(level.random), 0.0d, ySpeed, 0.0d);
                }
            }
            entityIn.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
            if (!level.isClientSide )
            {
                Random random = new Random();
                if (fallDistance > 10.0f)
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundSource.BLOCKS, 5.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                else if (fallDistance > 3.0f)
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundSource.BLOCKS, 2.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                else
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundSource.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            }
        }
        else
        {
            super.fallOn(level, blockState, pos, entityIn, fallDistance);
        }
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state)
    {
        for (int c = 0; c < 3 + level.random.nextInt(4); c++)
        {
            level.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX()+0.5d+r(level.random), pos.getY()+0.5d+r(level.random), pos.getZ()+0.5d+r(level.random), 0.0d, 0.1d, 0.0d);
        }
        super.spawnDestroyParticles(level, player, pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(FACING) == Direction.DOWN && rand.nextFloat() > 0.9f)
        {
            level.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0D, -0.1d, 0.0D);
        }
        if (BLOCK_COOKED_FISH_ITEM_HASH_MAP.containsKey(this))
        {
            Block block = level.getBlockState(pos.below
                    (
                            level.getBlockState(pos.below()).getMaterial() == Material.AIR ? 2 : 1
                    )).getBlock();

            if (block instanceof BaseFireBlock || block instanceof CampfireBlock)
            {
                for (int c = -1; c < level.random.nextInt(3); c++)
                {
                    level.addParticle(ParticleTypes.LAVA, pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0d, 0.0d, 0.0d);
                    level.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0d, 0.0d, 0.0d);
                }
            }
        }
    }

    private static double r(Random random)
    {
        return (random.nextDouble() * 0.5f) - 0.25f;
    }
}
