package dav.mod.world.gen;

import dav.mod.world.gen.placement.SurfacePlacement;
import dav.mod.world.gen.placement.TreeSurface;
import dav.mod.world.gen.tree.NaturalAppleTreeFeature;
import net.minecraft.init.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.CompositeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TreeWorldGen {
	
	public static void setupTreeGeneration() {
		SurfacePlacement TreeFlowerForest = new SurfacePlacement(70, 16);
		Biomes.FLOWER_FOREST.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreeFlowerForest));
		
		SurfacePlacement TreeGen = new SurfacePlacement(140, 12);
		Biomes.FOREST.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreeGen));
		Biomes.DARK_FOREST.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreeGen));
		
		SurfacePlacement TreePlainsGen = new SurfacePlacement(500, 12);
		Biomes.PLAINS.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreePlainsGen));
		Biomes.SUNFLOWER_PLAINS.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreePlainsGen));
		
		SurfacePlacement TreeMountGen = new SurfacePlacement(150, 12);
		Biomes.WOODED_MOUNTAINS.addFeature(Decoration.VEGETAL_DECORATION, new CompositeFeature<>(new NaturalAppleTreeFeature(), IFeatureConfig.NO_FEATURE_CONFIG, new TreeSurface(), TreeMountGen));
	}
}
