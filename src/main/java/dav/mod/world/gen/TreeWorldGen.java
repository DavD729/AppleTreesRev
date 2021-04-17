package dav.mod.world.gen;

import dav.mod.world.gen.placement.SurfacePlacement;
import dav.mod.world.gen.placement.TreeSurface;
import dav.mod.world.gen.tree.FruitTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TreeWorldGen {
	
	public static void setupTreeGeneration() {
		SurfacePlacement PlainsPlacement = new SurfacePlacement(0, 20);
		Biomes.PLAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), PlainsPlacement));
		Biomes.SUNFLOWER_PLAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), PlainsPlacement));
		
		SurfacePlacement ForestPlacement = new SurfacePlacement(1, 30);
		Biomes.FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), ForestPlacement));
		Biomes.DARK_FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), ForestPlacement));
		
		SurfacePlacement FlowerPlacement = new SurfacePlacement(3, 5);
		Biomes.FLOWER_FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), FlowerPlacement));
		
		SurfacePlacement MountainPlacement = new SurfacePlacement(0, 30);
		Biomes.WOODED_MOUNTAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new FruitTreeFeature(0, true), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), MountainPlacement));
	}
}
