package dav.mod.crafting;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dav.mod.Main;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class RecipesBuilder {
	
	public static JsonObject GAPPLE_SAPLING_RECIPE = null;
	public static JsonObject NOTCH_APPLE_RECIPE = null;
	
	public static void recipeManager() {
		if(FabricLoader.getInstance().isModLoaded(Main.MODID)) {
			GAPPLE_SAPLING_RECIPE = createShapedRecipeJson(
				Lists.newArrayList('S', 'I'),
				Lists.newArrayList(new Identifier("appletreesrev", "apple_sapling"), new Identifier("gold_ingot")),
				Lists.newArrayList("item", "item"),
				Lists.newArrayList(
					"III",
					"ISI",
					"III"
				),
				new Identifier("appletreesrev:gapple_sapling")
			);
			
			NOTCH_APPLE_RECIPE = createShapedRecipeJson(
				Lists.newArrayList('B', 'A'),
				Lists.newArrayList(new Identifier("gold_block"), new Identifier("apple")),
				Lists.newArrayList("item", "item"),
				Lists.newArrayList(
					"BBB",
					"BAB",
					"BBB"
				),
				new Identifier("enchanted_golden_apple")
			);
		}
	}
	
	public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "minecraft:crafting_shaped");
		
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(pattern.get(0));
		jsonArray.add(pattern.get(1));
		jsonArray.add(pattern.get(2));
		json.add("pattern", jsonArray);
		
		JsonObject individualKey;
		JsonObject keyList = new JsonObject();
		
		for(int i = 0; i < keys.size(); i++) {
			individualKey = new JsonObject();
			individualKey.addProperty(type.get(i), items.get(i).toString());
			keyList.add(keys.get(i) + "", individualKey);
		}
		json.add("key", keyList);
		
		JsonObject result = new JsonObject();
		result.addProperty("item", output.toString());
		result.addProperty("count", 1);
		json.add("result", result);
		
		return json;
	}
}
