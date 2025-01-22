package net.dav.appletreesrev.util;

import net.dav.appletreesrev.init.BlockInit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {
	public static void registerAll() {
		RecipeHandler.registerCrafting();
	}
	
	public static void registerCrafting() {
		if(ConfigHandler.craftNotchApple){
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID + ":" + "notch_apple_recipe"),
				new ResourceLocation(Reference.MOD_ID + ":" + "saplings"),
				new ItemStack(Items.GOLDEN_APPLE, 1, 1),
                    "BBB",
                    "BAB",
                    "BBB",
                    'B', Blocks.GOLD_BLOCK,
                    'A', Items.APPLE);
		}
		
		if(ConfigHandler.craftGoldAppleSapling) {
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MOD_ID + ":" + "gold_apple_sapling_recipe"),
				new ResourceLocation(Reference.MOD_ID + ":" + "saplings"),
				new ItemStack(BlockInit.SAPLING, 1, 1),
                    "III",
                    "ISI",
                    "III",
                    'I', Items.GOLD_INGOT,
                    'S', new ItemStack(BlockInit.SAPLING, 1, 0));
		}
		
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID + ":" + "apple_sapling_recipe"),
			new ResourceLocation(Reference.MOD_ID + ":" + "saplings"),
			new ItemStack(BlockInit.SAPLING, 1, 0),
                Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 0)),
                Ingredient.fromItem(Items.APPLE));
	}
}
