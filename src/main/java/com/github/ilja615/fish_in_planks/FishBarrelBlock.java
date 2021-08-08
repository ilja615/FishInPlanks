package com.github.ilja615.fish_in_planks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FishBarrelBlock extends DirectionalBlock
{
    private SoundEvent sound;
    private boolean poisonous;

    public static final HashMap<Block, IParticleData> BLOCK_I_PARTICLE_DATA_HASH_MAP = new HashMap<>();
    public static final HashMap<Block, Item> BLOCK_COOKED_FISH_ITEM_HASH_MAP = new HashMap<>();


    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        if (BLOCK_COOKED_FISH_ITEM_HASH_MAP.containsKey(this) && random.nextFloat() > 0.8f)
        {
            Block block = worldIn.getBlockState(pos.down
                    (
                            worldIn.getBlockState(pos.down()).getMaterial() == Material.AIR ? 2 : 1
                    )).getBlock();

            if (block instanceof AbstractFireBlock || block instanceof CampfireBlock)
            {
                ItemStack itemStack = new ItemStack(BLOCK_COOKED_FISH_ITEM_HASH_MAP.get(this), 5 + random.nextInt(4));
                worldIn.removeBlock(pos, false);
                worldIn.addEntity(new ItemEntity(worldIn, pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d, itemStack));
                worldIn.addParticle(ParticleTypes.LAVA, pos.getX()+0.5d+r(random), pos.getY(), pos.getZ()+0.5d+r(random), 0.0d, 0.0d, 0.0d);
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX()+0.5d+r(random), pos.getY(), pos.getZ()+0.5d+r(random), 0.0d, 0.0d, 0.0d);
                return;
            }
        }
        super.randomTick(state, worldIn, pos, random);
    }

    public FishBarrelBlock(Properties properties, SoundEvent sound, boolean poisonous)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
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

    protected static final VoxelShape FISH_BARREL_EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_WEST_AABB = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D);
    protected static final VoxelShape FISH_BARREL_NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_UP_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape FISH_BARREL_DOWN_AABB = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch(state.get(FACING)) {
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

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        Random random = new Random();
        entityIn.setMotion(new Vector3d(entityIn.getMotion().getX()*0.5,entityIn.getMotion().getY(),entityIn.getMotion().getZ()*0.5));
        if (!worldIn.isRemote )
        {
            double d0 = Math.abs(entityIn.getPosX() - entityIn.lastTickPosX);
            double d1 = Math.abs(entityIn.getPosZ() - entityIn.lastTickPosZ);
            double d2 = Math.abs(entityIn.getPosY() - entityIn.lastTickPosY);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F || d2 >= (double)0.003F)
            {
                if (random.nextInt(16)==0) {
                    worldIn.playSound((PlayerEntity) null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundCategory.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                }
            }
            if (this.poisonous)
            {
                if (entityIn instanceof LivingEntity && !((LivingEntity) entityIn).isPotionActive(Effects.POISON))
                {
                    ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 200, 2));
                }
            }
        }
    }

    public boolean allowsMovement(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (worldIn.getBlockState(pos).get(FACING) == Direction.UP)
        {
            if (fallDistance > 3.0f)
            {
                double ySpeed = Math.min(fallDistance / 16.0d, 0.5d);
                for (int c = 0; c < 2 + worldIn.rand.nextInt(3); c++)
                {
                    worldIn.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX() + 0.5d + r(worldIn.rand), pos.getY() + 1.0d, pos.getZ() + 0.5d + r(worldIn.rand), 0.0d, ySpeed, 0.0d);
                }
            }
            entityIn.onLivingFall(fallDistance, 0.2F);
            if (!worldIn.isRemote )
            {
                Random random = new Random();
                if (fallDistance > 10.0f)
                    worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundCategory.BLOCKS, 5.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                else if (fallDistance > 3.0f)
                    worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundCategory.BLOCKS, 2.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                else
                    worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.sound, SoundCategory.BLOCKS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            }
        }
        else
        {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager)
    {
        for (int c = 0; c < 3 + world.rand.nextInt(4); c++)
        {
            world.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX()+0.5d+r(world.rand), pos.getY()+0.5d+r(world.rand), pos.getZ()+0.5d+r(world.rand), 0.0d, 0.1d, 0.0d);
        }
        return super.addDestroyEffects(state, world, pos, manager);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.get(FACING) == Direction.DOWN && rand.nextFloat() > 0.9f)
        {
            worldIn.addParticle(BLOCK_I_PARTICLE_DATA_HASH_MAP.getOrDefault(this, ModParticles.COD_PARTICLE.get()), pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0D, -0.1d, 0.0D);
        }
        if (BLOCK_COOKED_FISH_ITEM_HASH_MAP.containsKey(this))
        {
            Block block = worldIn.getBlockState(pos.down
                    (
                            worldIn.getBlockState(pos.down()).getMaterial() == Material.AIR ? 2 : 1
                    )).getBlock();

            if (block instanceof AbstractFireBlock || block instanceof CampfireBlock)
            {
                for (int c = -1; c < worldIn.rand.nextInt(3); c++)
                {
                    worldIn.addParticle(ParticleTypes.LAVA, pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0d, 0.0d, 0.0d);
                    worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX()+0.5d+r(rand), pos.getY(), pos.getZ()+0.5d+r(rand), 0.0d, 0.0d, 0.0d);
                }
            }
        }
    }

    private static double r(Random random)
    {
        return (random.nextDouble() * 0.5f) - 0.25f;
    }
}
