package dav.mod.world.gen;

import dav.mod.world.gen.decorator.SurfaceDecorator;
import dav.mod.world.gen.decorator.TreeDecorator;
import dav.mod.world.gen.feature.FruitTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class TreeWorldGen {
	
	public static void addBiomeFeatures() {
		SurfaceDecorator PlainsDecorator = new SurfaceDecorator(0, 20);
		Biomes.PLAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), PlainsDecorator));
		Biomes.SUNFLOWER_PLAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), PlainsDecorator));
		
		SurfaceDecorator ForestDecorator = new SurfaceDecorator(1, 30);
		Biomes.FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), ForestDecorator));
		Biomes.DARK_FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), ForestDecorator));
		
		SurfaceDecorator FlowerDecorator = new SurfaceDecorator(3, 5);
		Biomes.FLOWER_FOREST.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), FlowerDecorator));
		
		SurfaceDecorator MountainDecorator = new SurfaceDecorator(0, 30);
		Biomes.WOODED_MOUNTAINS.addFeature(Feature.VEGETAL_DECORATION, Biome.configureFeature(new FruitTreeFeature(0, true), DefaultFeatureConfig.DEFAULT, new TreeDecorator(), MountainDecorator));
		
	}
}
