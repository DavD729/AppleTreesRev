package dav.mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigBuilder {
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final SaplingRecipes RECIPES = new SaplingRecipes(BUILDER);
	public static final ForgeConfigSpec SPEC = BUILDER.build();
	
	public static class SaplingRecipes {
		
		public final ForgeConfigSpec.ConfigValue<Boolean> GoldAppleSaplingRecipe;
		public final ForgeConfigSpec.ConfigValue<Boolean> NotchAppleRecipe;
		
		public SaplingRecipes(ForgeConfigSpec.Builder builder) {
			builder.comment("[Apple Trees Rev. Mod - Config. File]");
			builder.push("Recipes");
			
			GoldAppleSaplingRecipe = builder
				.comment("[Enable/Disable] Gold Apple Sapling Recipe [true/false] | [false by Default]")
				.define("GoldAppleSaplingRecipe", false);
			NotchAppleRecipe = builder
				.comment("[Enable/Disable] Notch Apple Recipe [true/false] | [false by Default]")
				.define("NotchAppleRecipe", false);
			
			builder.pop();
		}
	}
}
