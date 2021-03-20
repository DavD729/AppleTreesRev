package dav.mod.world.gen;

import dav.mod.world.gen.placement.SurfacePlacement;
import dav.mod.world.gen.placement.TreeSurface;
import dav.mod.world.gen.tree.NaturalAppleTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TreeWorldGen {
	
	public static void setupTreeGeneration() {
		SurfacePlacement PlainsPlacement = new SurfacePlacement(1, 45);
		Biomes.PLAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), PlainsPlacement));
		Biomes.SUNFLOWER_PLAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), PlainsPlacement));
		
		SurfacePlacement ForestPlacement = new SurfacePlacement(1, 25);
		Biomes.FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), ForestPlacement));
		Biomes.DARK_FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), ForestPlacement));
		
		SurfacePlacement FlowerPlacement = new SurfacePlacement(3, 10);
		Biomes.FLOWER_FOREST.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), FlowerPlacement));
		
		SurfacePlacement MountainPlacement = new SurfacePlacement(2, 30);
		Biomes.WOODED_MOUNTAINS.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new NaturalAppleTreeFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(SurfacePlacement::deserialize), MountainPlacement));
	}
}
