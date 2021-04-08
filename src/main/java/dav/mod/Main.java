package dav.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dav.mod.config.ConfigBuilder;
import dav.mod.crafting.Condition;
import dav.mod.crafting.ConditionSerializer;
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
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        
        //Config Builder Register
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigBuilder.SPEC);
        LOGGER.info("Apple Trees Rev. Mod Loaded");
    }

    private void setup(final FMLCommonSetupEvent event) {
    	TreeWorldGen.setupTreeGeneration();
    	LOGGER.info("WorldGen Feature Registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	//Registry Crafting Conditions
    	CraftingHelper.register(new ConditionSerializer(new Condition(ConfigBuilder.RECIPES.GoldAppleSaplingRecipe.get(), getPath("gapple_condition")), getPath("gapplerecipecondition")));
    	CraftingHelper.register(new ConditionSerializer(new Condition(ConfigBuilder.RECIPES.NotchAppleRecipe.get(), getPath("notch_condition")), getPath("notchrecipecondition")));
    	LOGGER.info("Crafting Conditions Registered.");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }
    
    public static ResourceLocation getPath(String path) {
    	return new ResourceLocation(MODID, path);
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
            LOGGER.info("AppleTreesRev: Items Registered.");
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
            LOGGER.info("AppleTreesRev: Blocks Registered.");
        }
    }
}
