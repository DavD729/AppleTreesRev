package dav.mod.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import dav.mod.config.ConfigBuilder;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class GappleRecipeCondition implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		return () -> ConfigBuilder.RECIPES.GoldAppleSaplingRecipe.get();
	}
}
