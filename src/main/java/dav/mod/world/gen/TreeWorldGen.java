package dav.mod.world.gen;

import com.google.common.collect.ImmutableList;

import dav.mod.world.gen.decorator.AppleDecorator;
import dav.mod.world.gen.decorator.SurfaceDecorator;
import dav.mod.world.gen.decorator.TreeDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class TreeWorldGen {
	
	public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
	public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	
	public static final BranchedTreeFeatureConfig APPLE_TREE_CONFIG = (new BranchedTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), 
		new BlobFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(0, false))).build();
	
	public static final BranchedTreeFeatureConfig GOLD_APPLE_TREE_CONFIG = (new BranchedTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), 
		new BlobFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(1, false))).build();
	
	public static final BranchedTreeFeatureConfig NATURAL_APPLE_TREE_CONFIG = (new BranchedTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), 
		new BlobFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(0, true))).build();
	
	public static void setupWorldGen() {
		Biomes.PLAINS.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(1, 60))));
		Biomes.SUNFLOWER_PLAINS.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(1, 55))));
		Biomes.FOREST.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(1, 25))));
		Biomes.DARK_FOREST.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(1, 20))));
		Biomes.FLOWER_FOREST.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(3, 10))));
		Biomes.WOODED_MOUNTAINS.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.NORMAL_TREE.configure(NATURAL_APPLE_TREE_CONFIG).createDecoratedFeature(new TreeDecorator(SurfaceDecorator::deserialize)
			.configure(new SurfaceDecorator(2, 35))));
	}
}
