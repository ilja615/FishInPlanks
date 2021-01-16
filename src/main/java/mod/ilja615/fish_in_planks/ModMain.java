package mod.ilja615.fish_in_planks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

import static mod.ilja615.fish_in_planks.ModMain.MOD_ID;

@Mod(MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "fish_in_planks";
    public static final Item.Properties ITEM_PROPERTY = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);

    public ModMain()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupCommon);

        //ModItems.ITEMS.register(modEventBus);
        ModBlocks.registerUACompatBlocks();
        ModBlocks.BLOCKS.register(modEventBus);

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
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();
            ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                final BlockItem blockItem = new BlockItem(block, ITEM_PROPERTY);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });
        }
    }
}
