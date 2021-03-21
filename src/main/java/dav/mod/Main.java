package dav.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dav.mod.init.BlockInit;
import dav.mod.init.ItemInit;
import dav.mod.world.gen.TreeWorldGen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Main.MODID)
public class Main {
	
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "appletreesrev";
    public static Main instance;

    public Main() {
    	instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    	TreeWorldGen.setupTreeGeneration();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	
    	@SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
    		itemRegistryEvent.getRegistry().registerAll(
    			ItemInit.APPLE_SAPLING,
    			ItemInit.GAPPLE_SAPLING
    		);
            LOGGER.info("Items Registered");
        }
    	
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().registerAll(
            	BlockInit.APPLE_PLANT,
            	BlockInit.GAPPLE_PLANT,
            	BlockInit.APPLE_SAPLING,
            	BlockInit.GAPPLE_SAPLING
            );
            
            if(FMLEnvironment.dist == Dist.CLIENT) {
            	RenderTypeLookup.setRenderLayer(BlockInit.APPLE_SAPLING, RenderType.getCutout());
            	RenderTypeLookup.setRenderLayer(BlockInit.GAPPLE_SAPLING, RenderType.getCutout());
            	RenderTypeLookup.setRenderLayer(BlockInit.APPLE_PLANT, RenderType.getCutout());
            	RenderTypeLookup.setRenderLayer(BlockInit.GAPPLE_PLANT, RenderType.getCutout());
            }
            LOGGER.info("Blocks Registered");
        }
        
        public static ResourceLocation getPath(String path) {
        	return new ResourceLocation(MODID, path);
        }
    }
}
