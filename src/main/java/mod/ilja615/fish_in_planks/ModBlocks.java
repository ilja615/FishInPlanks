package mod.ilja615.fish_in_planks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MOD_ID);
    public static final RegistryObject<Block> COD_BARREL = BLOCKS.register("cod_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_COD_FLOP));
    public static final RegistryObject<Block> SALMON_BARREL = BLOCKS.register("salmon_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_SALMON_FLOP));
    public static final RegistryObject<Block> TROPICAL_FISH_BARREL = BLOCKS.register("tropical_fish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_TROPICAL_FISH_FLOP));
    public static final RegistryObject<Block> PUFFERFISH_BARREL = BLOCKS.register("pufferfish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_PUFFER_FISH_FLOP));

    public static RegistryObject<Block> PIKE_BARREL;
    public static RegistryObject<Block> LIONFISH_BARREL;
    public static void registerUACompatBlocks()
    {
        if (ModList.get().isLoaded("upgrade_aquatic"))
        {
            PIKE_BARREL = BLOCKS.register("pike_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_SALMON_FLOP));
            LIONFISH_BARREL = BLOCKS.register("lionfish_barrel", () -> new FishBarrelBlock(Block.Properties.from(Blocks.BARREL), SoundEvents.ENTITY_PUFFER_FISH_FLOP));
        }
    }
}
