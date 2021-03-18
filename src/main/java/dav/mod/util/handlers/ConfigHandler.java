package dav.mod.util.handlers;

import java.io.File;

import dav.mod.Main;
import dav.mod.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static boolean craftGappleSapling;
	public static boolean craftNotchApple;
	
	public static void init(File file) {	
		config = new Configuration(file);
		String category;
		category = "Recipes";
		config.addCustomCategoryComment(category, "Set the Value for each recipe");
		craftGappleSapling = config.getBoolean("Golden Apple Saplings can be Crafted In-Game", category, false, "Declare if you want to Craft Golden Apple Saplings");
		craftNotchApple = config.getBoolean("Notch Apples can be Crafted In-Game", category, false, "Declare if you want to Craft Notch Apples");
		config.save();
	}

	public static void registerConfig(FMLPreInitializationEvent event) {
		Main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		Main.config.mkdirs();
		init(new File(Main.config.getPath(), Reference.MODID + ".cfg"));
	}
}
