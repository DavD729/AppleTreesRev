package dav.mod.world.gen;

import dav.mod.world.gen.decorator.SurfaceDecorator;
import dav.mod.world.gen.decorator.TreeDecorator;
import dav.mod.world.gen.feature.NaturalAppleTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class TreeWorldGen {
	
	public static void addBiomeFeatures() {
		SurfaceDecorator PlainsDecorator = new SurfaceDecorator(1, 45);
		Biomes.PLAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), PlainsDecorator));
		Biomes.SUNFLOWER_PLAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), PlainsDecorator));
		
		SurfaceDecorator ForestDecorator = new SurfaceDecorator(1, 25);
		Biomes.FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), ForestDecorator));
		Biomes.DARK_FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), ForestDecorator));
		
		SurfaceDecorator FlowerDecorator = new SurfaceDecorator(3, 10);
		Biomes.FLOWER_FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), FlowerDecorator));
		
		SurfaceDecorator MountainDecorator = new SurfaceDecorator(2, 30);
		Biomes.WOODED_MOUNTAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new NaturalAppleTreeFeature(DefaultFeatureConfig::deserialize), DefaultFeatureConfig.DEFAULT, new TreeDecorator(SurfaceDecorator::deserialize), MountainDecorator));
		
	}
}
