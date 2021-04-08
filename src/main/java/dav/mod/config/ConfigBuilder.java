package dav.mod.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dav.mod.Main;

public class ConfigBuilder {
	
	public static boolean craftGoldAppleSapling;
	public static boolean craftNotchApple;
	
	private static File config;
	
	public static void startConfig() {
		readConfig();
	}
	
	private static void checkConfig() {
		if (config != null) {
			return;
		}
		config = new File(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().toFile(), Main.MODID + "_config.json");
	}
	
	private static void readConfig() {
		checkConfig();
		try {
			if(!config.exists()) {
				writeConfig();
			}
			if(config.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(config));
				JsonObject json = new JsonParser().parse(br).getAsJsonObject();
				if(json.has("recipes")) {
					JsonObject recipes = json.get("recipes").getAsJsonObject();
					if(recipes.has("craftGoldAppleSapling")) craftGoldAppleSapling = recipes.get("craftGoldAppleSapling").getAsBoolean();
					if(recipes.has("craftNotchApple")) craftNotchApple = recipes.get("craftNotchApple").getAsBoolean();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't read configuration file, setting defaults");
			e.printStackTrace();
		}
	}
	
	private static void writeConfig() {
		checkConfig();
		JsonObject jsonConfig = new JsonObject();
		jsonConfig.addProperty("_File", "Apple Trees Rev. Mod - Config File");
		
		JsonObject recipes = new JsonObject();
		recipes.addProperty("_field_A", "Declare if you Want to Craft Golden Apple Saplings [true/false][default:false]");
		recipes.addProperty("craftGoldAppleSapling", false);
		craftGoldAppleSapling = false;
		recipes.addProperty("_field_B", "Declare if you Want to Craft Notch Apples [true/false][default:false]");
		recipes.addProperty("craftNotchApple", false);
		craftNotchApple = false;
		jsonConfig.add("recipes", recipes);
		
		String jsonString = Main.GSON.toJson(jsonConfig);
		try (FileWriter fileWriter = new FileWriter(config)) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			System.err.println("Couldn't save configuration file, setting defaults");
			e.printStackTrace();
		}
	}
}
