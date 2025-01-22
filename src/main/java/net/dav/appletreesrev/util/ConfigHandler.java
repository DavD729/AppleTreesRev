package net.dav.appletreesrev.util;

import java.io.File;

import net.dav.appletreesrev.AppleTreesRev;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;
	
	public static boolean craftGoldAppleSapling;
	public static boolean craftNotchApple;
	public static boolean easyHarvest;

	private static final String CATEGORY_RECIPES = "Recipes";
	private static final String CATEGORY_MISC = "Misc";

	private static void init(File file) {
		config = new Configuration(file);
		try {
			config.load();

			craftGoldAppleSapling = config.getBoolean(
					"craftGoldAppleSapling",
					CATEGORY_RECIPES,
					false,
					"Enable recipe for Gold Apple Sapling (true/false)"
			);

			craftNotchApple = config.getBoolean(
					"craftNotchApple",
					CATEGORY_RECIPES,
					false,
					"Enable recipe for Enchanted Apple (true/false)"
			);

			easyHarvest = config.getBoolean(
					"easyHarvest",
					CATEGORY_MISC,
					false,
					"Enable easy harvest on Apple plants (true/false)"
			);
		} catch (Exception e) {
			AppleTreesRev.getLogger().error("Error loading configuration file: {}", e.getMessage());
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	public static void registerConfig(FMLPreInitializationEvent event) {
		AppleTreesRev.config = new File(event.getModConfigurationDirectory(), Reference.MOD_ID);
		AppleTreesRev.config.mkdirs();

		ConfigHandler.init(new File(AppleTreesRev.config.getPath(), Reference.MOD_ID + ".cfg"));
	}
}
