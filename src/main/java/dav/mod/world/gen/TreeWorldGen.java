package dav.mod.world.gen;

import com.google.common.collect.ImmutableList;

import dav.mod.Main;
import dav.mod.world.gen.decorator.AppleDecorator;
import dav.mod.world.gen.placement.SurfacePlacement;
import dav.mod.world.gen.placement.TreeSurface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TreeWorldGen {
	
	public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
	public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
	
	public static final BaseTreeFeatureConfig APPLE_CONFIG = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(OAK_LOG), 
		new SimpleBlockStateProvider(OAK_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), 
		new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build();
	
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> APPLE = new ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>>(Feature.TREE, 
		APPLE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT, new AppleDecorator(0, false))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> GAPPLE = new ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>>(Feature.TREE,
		APPLE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT, new AppleDecorator(1, false))));
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> NATURAL = new ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>>(Feature.TREE,
		APPLE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT, new AppleDecorator(0, true))));
	
	public static final ConfiguredPlacement<SurfacePlacement> PLAINS_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(20));
	public static final ConfiguredPlacement<SurfacePlacement> SUN_PLAINS_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(20));
	public static final ConfiguredPlacement<SurfacePlacement> FOREST_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(30));
	public static final ConfiguredPlacement<SurfacePlacement> DARK_FOREST_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(30));
	public static final ConfiguredPlacement<SurfacePlacement> FLOWER_FOREST_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(5));
	public static final ConfiguredPlacement<SurfacePlacement> MOUNTAIN_PLACEMENT = new ConfiguredPlacement<SurfacePlacement>(new TreeSurface(SurfacePlacement.CODEC), new SurfacePlacement(30));
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void setupTreeGeneration(final BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		String Evt = event.getName().toString();
		switch (event.getCategory()) {

		case PLAINS:	if(Evt.equals(Biomes.PLAINS.getLocation().toString())) {
							registerTo(generation, NATURAL.withPlacement(PLAINS_PLACEMENT));
							Main.LOGGER.info("PLAINS Feature Subscribed");
						} else if(Evt.equals(Biomes.SUNFLOWER_PLAINS.getLocation().toString())) {
							registerTo(generation, NATURAL.withPlacement(SUN_PLAINS_PLACEMENT));
							Main.LOGGER.info("SUNFLOWER_PLAINS Feature Subscribed");
						}
						break;

		case FOREST:	if(Evt.equals(Biomes.FOREST.getLocation().toString())) {
							registerTo(generation, NATURAL.withPlacement(FOREST_PLACEMENT));
							Main.LOGGER.info("FOREST Feature Subscribed");
						} else if(Evt.equals(Biomes.DARK_FOREST.getLocation().toString())) {
							registerTo(generation, NATURAL.withPlacement(DARK_FOREST_PLACEMENT));
							Main.LOGGER.info("DARK FOREST Feature Subscribed");
						} else if(Evt.equals(Biomes.FLOWER_FOREST.getLocation().toString())) {
							registerTo(generation, NATURAL.withPlacement(FLOWER_FOREST_PLACEMENT));
							Main.LOGGER.info("FLOWER FOREST Feature Subscribed");
						}
						break;

		case EXTREME_HILLS:		if(Evt.equals(Biomes.WOODED_MOUNTAINS.getLocation().toString())) {
									registerTo(generation, NATURAL.withPlacement(MOUNTAIN_PLACEMENT));
									Main.LOGGER.info("WOODED MOUNTAINS Feature Subscribed");
								}
		default:	break;
		}
	}
	
	private static void registerTo(BiomeGenerationSettingsBuilder builder, ConfiguredFeature<?, ?> feature) { 
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
	}
}
