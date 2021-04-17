package dav.mod.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DefaultTreeGenerator extends Feature<DefaultFeatureConfig> {
	
	private final ConfiguredFeature<TreeFeatureConfig, ?> Config;
	
	public DefaultTreeGenerator(Codec<DefaultFeatureConfig> configCodec, ConfiguredFeature<TreeFeatureConfig, ?> BaseConfig) {
		super(configCodec);
		this.Config = BaseConfig;
	}

	@Override
	public boolean generate(StructureWorldAccess World, ChunkGenerator chunkGenerator, Random Rand, BlockPos Pos, DefaultFeatureConfig config) {
		return this.Config.generate(World, chunkGenerator, Rand, Pos);
	}
}
