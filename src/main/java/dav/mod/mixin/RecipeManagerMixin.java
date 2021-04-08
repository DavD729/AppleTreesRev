package dav.mod.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;

import dav.mod.config.ConfigBuilder;
import dav.mod.crafting.RecipesBuilder;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Inject(method = "apply", at = @At("HEAD"))
	private void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
		if(RecipesBuilder.GAPPLE_SAPLING_RECIPE != null && ConfigBuilder.craftGoldAppleSapling) {
			map.put(new Identifier("appletreesrev", "gapple_sapling"), RecipesBuilder.GAPPLE_SAPLING_RECIPE);
		}
		if(RecipesBuilder.NOTCH_APPLE_RECIPE != null && ConfigBuilder.craftNotchApple) {
			map.put(new Identifier("enchanted_golden_apple"), RecipesBuilder.NOTCH_APPLE_RECIPE);
		}
	}
}
