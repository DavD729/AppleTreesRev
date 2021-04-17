package dav.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dav.mod.config.ConfigBuilder;
import dav.mod.crafting.Condition;
import dav.mod.crafting.ConditionSerializer;
import dav.mod.lists.BlockList;
import dav.mod.lists.CustomTree;
import dav.mod.lists.ItemList;
import dav.mod.objects.blocks.tree.AppleBlockPlant;
import dav.mod.objects.blocks.tree.CustomBlockSapling;
import dav.mod.world.gen.TreeWorldGen;
import dav.mod.world.gen.tree.FruitTreeFeature;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
	
	public static final String MODID = "appletreesrev";
	public static Main instance;
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public Main() {
		
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
		
		//Config Builder Register
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ConfigBuilder.SPEC);
		LOGGER.info("Apple Trees Rev. Mod Loaded");
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) {
		TreeWorldGen.setupTreeGeneration();
		LOGGER.info("Common: WorldGen Feature Registered.");
		
		CraftingHelper.register(new ConditionSerializer(new Condition(getPath("gapplerecipecondition"), ConfigBuilder.RECIPES.GoldAppleSaplingRecipe.get())));
		CraftingHelper.register(new ConditionSerializer(new Condition(getPath("notchrecipecondition"), ConfigBuilder.RECIPES.NotchAppleRecipe.get())));
		LOGGER.info("Common: Crafting Conditions Registered.");
	}
	
	public static ResourceLocation getPath(String path) {
		return new ResourceLocation(MODID, path);
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			event.getRegistry().registerAll(
				ItemList.APPLE_SAPLING = new BlockItem(BlockList.APPLE_SAPLING, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockList.APPLE_SAPLING.getRegistryName()),
				ItemList.GAPPLE_SAPLING = new BlockItem(BlockList.GAPPLE_SAPLING, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BlockList.GAPPLE_SAPLING.getRegistryName())
			);
			LOGGER.info("AppleTreesRev: Items Registered.");
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			event.getRegistry().registerAll(
				BlockList.APPLE_PLANT = new AppleBlockPlant(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT), Items.APPLE).setRegistryName(getPath("apple_plant")),
				BlockList.GAPPLE_PLANT = new AppleBlockPlant(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT), Items.GOLDEN_APPLE).setRegistryName(getPath("gapple_plant")),
				BlockList.APPLE_SAPLING = new CustomBlockSapling(new CustomTree(new FruitTreeFeature(0, false)), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName(getPath("apple_sapling")),
				BlockList.GAPPLE_SAPLING = new CustomBlockSapling(new CustomTree(new FruitTreeFeature(1, false)), Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT)).setRegistryName(getPath("gapple_sapling"))
			);
			LOGGER.info("AppleTreesRev: Blocks Registered.");
		}
	}
}
