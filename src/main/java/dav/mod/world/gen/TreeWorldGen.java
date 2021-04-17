package dav.mod.world.gen;

import com.google.common.collect.ImmutableList;

import dav.mod.init.BlockInit;
import dav.mod.world.gen.decorator.AppleDecorator;
import dav.mod.world.gen.placement.SurfacePlacement;
import dav.mod.world.gen.placement.TreeSurface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class TreeWorldGen {
	
	public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
	public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	
	public static final TreeFeatureConfig APPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new BlobFoliagePlacer(2, 0)))
		.baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(0, false)))
		.setSapling((net.minecraftforge.common.IPlantable)BlockInit.APPLE_SAPLING).build();
	
	public static final TreeFeatureConfig GOLD_APPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new BlobFoliagePlacer(2, 0)))
		.baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(1, false)))
		.setSapling((net.minecraftforge.common.IPlantable)BlockInit.GAPPLE_SAPLING).build();
	
	public static final TreeFeatureConfig NATURAL_APPLE_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new BlobFoliagePlacer(2, 0)))
		.baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F), new AppleDecorator(0, true)))
		.setSapling((net.minecraftforge.common.IPlantable)BlockInit.APPLE_SAPLING).build();
	
	public static void setupTreeGeneration() {
		Biomes.PLAINS.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(0, 20))));
		Biomes.SUNFLOWER_PLAINS.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(0, 20))));
		Biomes.FOREST.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(1, 30))));
		Biomes.DARK_FOREST.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(1, 30))));
		Biomes.FLOWER_FOREST.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(3, 5))));
		Biomes.WOODED_MOUNTAINS.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(NATURAL_APPLE_TREE_CONFIG).withPlacement(new TreeSurface()
			.configure(new SurfacePlacement(0, 30))));		
	}
}
