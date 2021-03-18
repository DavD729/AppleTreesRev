package dav.mod.util.handlers;

import dav.mod.init.BlockInit;
import dav.mod.util.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {
	
	public static void registerAll() {
		registerCrafting();
	}
	
	public static void registerCrafting() {
		
		if(ConfigHandler.craftNotchApple){
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + ":" + "notchapple_recipe"), 
				new ResourceLocation(Reference.MODID + ":" + "saplings"), 
				new ItemStack(Items.GOLDEN_APPLE, 1, 1), 
				new Object[] {
					"BBB", 
					"BAB", 
					"BBB", 
					'B', Blocks.GOLD_BLOCK, 
					'A', Items.APPLE
				}
			);
		}
		
		if(ConfigHandler.craftGappleSapling) {
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + ":" + "gapplesapling_recipe"), 
				new ResourceLocation(Reference.MODID + ":" + "saplings"), 
				new ItemStack(BlockInit.GAPPLE_SAPLING), 
				new Object[] {
					"III", 
					"ISI", 
					"III", 
					'I', Items.GOLD_INGOT, 
					'S', BlockInit.APPLE_SAPLING 
				}
			);
		}
		
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID + ":" + "applesapling_recipe"), 
			new ResourceLocation(Reference.MODID + ":" + "saplings"), 
			new ItemStack(BlockInit.APPLE_SAPLING), 
			new Ingredient[] {
				Ingredient.fromStacks(new ItemStack(Blocks.SAPLING, 1, 0)), 
				Ingredient.fromItem(Items.APPLE)
			}
		);
	}
}
