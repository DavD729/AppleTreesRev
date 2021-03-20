package dav.mod;

import java.io.File;

import dav.mod.config.RecipesConfig;
import dav.mod.init.BlockInit;
import dav.mod.world.gen.TreeWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	
	public static File config;
	public static final String MODID = "appletreesrev";
	
	@Override
	public void onInitialize() {
		registerBlocks();
		registerItems();
		registerFeatures();
		RecipesConfig.recipeManager();
		System.out.println("Mod Registered");
	}
	
	private static void registerItems() {
		Registry.register(Registry.ITEM, getPath("apple_sapling"), new BlockItem(BlockInit.APPLE_SAPLING, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		Registry.register(Registry.ITEM, getPath("gapple_sapling"), new BlockItem(BlockInit.GAPPLE_SAPLING, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		System.out.println("Items Registered");
	}
	
	private static void registerBlocks() {
		Registry.register(Registry.BLOCK, getPath("apple_plant"), BlockInit.APPLE_PLANT);
		Registry.register(Registry.BLOCK, getPath("gapple_plant"), BlockInit.GAPPLE_PLANT);
		Registry.register(Registry.BLOCK, getPath("apple_sapling"), BlockInit.APPLE_SAPLING);
		Registry.register(Registry.BLOCK, getPath("gapple_sapling"), BlockInit.GAPPLE_SAPLING);
		System.out.println("Blocks Registered");
	}
	
	private static void registerFeatures() {
		TreeWorldGen.addBiomeFeatures();
	}
	
	public static Identifier getPath(String path) {
		return new Identifier(MODID, path);
	}
}