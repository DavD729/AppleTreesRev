package dav.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dav.mod.lists.BlockList;
import dav.mod.lists.CustomTree;
import dav.mod.lists.ItemList;
import dav.mod.objects.blocks.tree.AppleBlockPlant;
import dav.mod.objects.blocks.tree.CustomBlockSapling;
import dav.mod.world.gen.TreeWorldGen;
import dav.mod.world.gen.tree.AppleTreeFeature;
import dav.mod.world.gen.tree.GoldAppleTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
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

@Mod(Main.modid)
public class Main {
	
	public static final String modid = "appletreesrev";
	public static Main instance;
	public static final Logger logger = LogManager.getLogger(modid);
	
	public Main() {
		
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) {
		TreeWorldGen.setupTreeGeneration(); //Registry Biome Features
		logger.info("CommonSetup Registered.");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event) {
	}
	
	private void enqueueIMC(final InterModEnqueueEvent event) {
    	}

    	private void processIMC(final InterModProcessEvent event) {
    	}

    	@SubscribeEvent
    	public void onServerStarting(FMLServerStartingEvent event) {
	}
	
	public static ResourceLocation getLocation(String path) {
		return new ResourceLocation(modid, path);
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents{
		
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			event.getRegistry().registerAll(
				ItemList.apple_sapling = new ItemBlock(BlockList.apple_sapling, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockList.apple_sapling.getRegistryName()),
				ItemList.gapple_sapling = new ItemBlock(BlockList.gapple_sapling, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockList.gapple_sapling.getRegistryName())
			);
			logger.info("Items Registered.");
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			event.getRegistry().registerAll(
				BlockList.apple_plant = new AppleBlockPlant(0, Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.4F).sound(SoundType.PLANT)).setRegistryName(getLocation("apple_plant")),
				BlockList.goldapple_plant = new AppleBlockPlant(1, Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.4F).sound(SoundType.PLANT)).setRegistryName(getLocation("gapple_plant")),
				BlockList.apple_sapling = new CustomBlockSapling(new CustomTree(new AppleTreeFeature()), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName(getLocation("apple_sapling")),
				BlockList.gapple_sapling = new CustomBlockSapling(new CustomTree(new GoldAppleTreeFeature()), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName(getLocation("gapple_sapling"))
			);
			logger.info("Blocks Registered.");
		}
	}
}
		

