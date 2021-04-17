package dav.mod.world.gen;

import com.google.common.collect.ImmutableList;

import dav.mod.Main;
import dav.mod.world.gen.decorator.AppleDecorator;
import dav.mod.world.gen.decorator.SurfaceDecorator;
import dav.mod.world.gen.decorator.TreeDecorator;
import dav.mod.world.gen.feature.DefaultTreeGenerator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

@SuppressWarnings("deprecation")
public class TreeWorldGen {
	
	public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
	public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	
	public static final Decorator<SurfaceDecorator> TREE_DECORATOR = Registry.register(Registry.DECORATOR, Main.getPath("tree_decorator"), new TreeDecorator(SurfaceDecorator.CODEC));
	
	public static final TreeFeatureConfig BASE_TREE = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES),
		new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayersFeatureSize(1, 0, 1))
		.decorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES)).ignoreVines().build();
	
	public static final ConfiguredFeature<TreeFeatureConfig, ?> APPLE_TREE = new ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>>(Feature.TREE, BASE_TREE
		.setTreeDecorators(ImmutableList.of(new AppleDecorator(0, false))));
	
	public static final ConfiguredFeature<TreeFeatureConfig, ?> GAPPLE_TREE = new ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>>(Feature.TREE, BASE_TREE
		.setTreeDecorators(ImmutableList.of(new AppleDecorator(1, false))));
	
	public static final ConfiguredFeature<TreeFeatureConfig, ?> NATURAL_TREE = new ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>>(Feature.TREE, BASE_TREE
		.setTreeDecorators(ImmutableList.of(new AppleDecorator(0, true))));
	
	private static final Feature<DefaultFeatureConfig> NAT_TREE = new DefaultTreeGenerator(DefaultFeatureConfig.CODEC, NATURAL_TREE);
	
	public static final ConfiguredDecorator<SurfaceDecorator> PLAINS_DECORATE = TREE_DECORATOR.configure(new SurfaceDecorator(20));
	public static final ConfiguredDecorator<SurfaceDecorator> FOREST_DECORATE = TREE_DECORATOR.configure(new SurfaceDecorator(30));
	public static final ConfiguredDecorator<SurfaceDecorator> FLOWER_FOREST_DECORATE = TREE_DECORATOR.configure(new SurfaceDecorator(5));
	public static final ConfiguredDecorator<SurfaceDecorator> MOUNTAIN_DECORATE = TREE_DECORATOR.configure(new SurfaceDecorator(30));
	
	public static final ConfiguredFeature<?, ?> PLAINS_APPLE = NAT_TREE.configure(FeatureConfig.DEFAULT).decorate(PLAINS_DECORATE);
	public static final ConfiguredFeature<?, ?> FOREST_APPLE = NAT_TREE.configure(FeatureConfig.DEFAULT).decorate(FOREST_DECORATE);
	public static final ConfiguredFeature<?, ?> FLOWER_FOREST_APPLE = NAT_TREE.configure(FeatureConfig.DEFAULT).decorate(FLOWER_FOREST_DECORATE);
	public static final ConfiguredFeature<?, ?> MOUNTAIN_APPLE = NAT_TREE.configure(FeatureConfig.DEFAULT).decorate(MOUNTAIN_DECORATE);
	
	public static void registerFeatures() {	
		Registry.register(Registry.FEATURE, Main.getPath("default_apple_tree"), NAT_TREE);
		
		RegistryKey<ConfiguredFeature<?, ?>> plainsApple = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Main.getPath("plains_apple"));
		RegistryKey<ConfiguredFeature<?, ?>> forestApple = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Main.getPath("forest_apple"));
		RegistryKey<ConfiguredFeature<?, ?>> flowerForestApple = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Main.getPath("flowerforest_apple"));
		RegistryKey<ConfiguredFeature<?, ?>> mountainApple = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, Main.getPath("mountain_apple"));
		
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, plainsApple.getValue(), PLAINS_APPLE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, forestApple.getValue(), FOREST_APPLE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, flowerForestApple.getValue(), FLOWER_FOREST_APPLE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, mountainApple.getValue(), MOUNTAIN_APPLE);
		
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, plainsApple);
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.DARK_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, forestApple);
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, flowerForestApple);
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WOODED_MOUNTAINS), GenerationStep.Feature.VEGETAL_DECORATION, mountainApple);
	}
}
