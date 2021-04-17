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
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
	
	public static final String MODID = "appletreesrev";
	public static final Logger LOGGER = LogManager.getLogger();
	public static Main instance;

	public Main() {
		
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
		
		//Config Builder Register
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigBuilder.SPEC);
		LOGGER.info("Apple Trees Rev. Mod Loaded");
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(TreeWorldGen.class);
		LOGGER.info("Common: World Gen Class Registered.");
		
		CraftingHelper.register(new ConditionSerializer(new Condition(ConfigBuilder.RECIPES.GoldAppleSaplingRecipe.get(), getPath("gapplerecipecondition"))));
		CraftingHelper.register(new ConditionSerializer(new Condition(ConfigBuilder.RECIPES.NotchAppleRecipe.get(), getPath("notchrecipecondition"))));
		LOGGER.info("Common: Crafting Conditions Registered.");
	}
	
	public static ResourceLocation getPath(String path) {
		return new ResourceLocation(Main.MODID, path);
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
				ItemInit.GAPPLE_SAPLING,
				ItemInit.APPLE_PLANT,
				ItemInit.GAPPLE_PLANT
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
			BlockInit.onBlockRendering();
			LOGGER.info("AppleTreesRev: Blocks Registered.");
		}
	}
}
