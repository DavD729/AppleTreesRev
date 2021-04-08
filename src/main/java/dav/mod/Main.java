package dav.mod;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dav.mod.config.ConfigBuilder;
import dav.mod.crafting.RecipesBuilder;
import dav.mod.init.BlockInit;
import dav.mod.world.gen.TreeWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	
	public static final String MODID = "appletreesrev";
	public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
	
	@Override
	public void onInitialize() {
		ConfigBuilder.startConfig();
		registerBlocks();
		registerItems();
		registerFeatures();
		RecipesBuilder.recipeManager();
		System.out.println("Apple Trees Rev. Mod Initialized");
	}
	
	public static void registerItems() {
		Registry.register(Registry.ITEM, getPath("apple_sapling"), new BlockItem(BlockInit.APPLE_SAPLING, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		Registry.register(Registry.ITEM, getPath("gapple_sapling"), new BlockItem(BlockInit.GAPPLE_SAPLING, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
		System.out.println("Apple Trees Rev.: Items Registered");
	}
	
	public static void registerBlocks() {
		Registry.register(Registry.BLOCK, getPath("apple_plant"), BlockInit.APPLE_PLANT);
		Registry.register(Registry.BLOCK, getPath("gapple_plant"), BlockInit.GAPPLE_PLANT);
		Registry.register(Registry.BLOCK, getPath("apple_sapling"), BlockInit.APPLE_SAPLING);
		Registry.register(Registry.BLOCK, getPath("gapple_sapling"), BlockInit.GAPPLE_SAPLING);
		BlockInit.renderCutoutBlocks();
		System.out.println("Apple Trees Rev.: Blocks Registered");
	}
	
	public static void registerFeatures() {
		TreeWorldGen.setupWorldGen();
		System.out.println("Apple Trees Rev.: World Gen Feature Registered");
	}
	
	public static Identifier getPath(String path) {
		return new Identifier(MODID, path);
	}
}
