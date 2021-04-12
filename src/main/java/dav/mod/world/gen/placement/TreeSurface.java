package dav.mod.world.gen.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class TreeSurface extends BasePlacement<SurfacePlacement>{

	@Override
	public <C extends IFeatureConfig> boolean generate(IWorld WorldIn, IChunkGenerator<? extends IChunkGenSettings> ChunkGen, Random Rand, BlockPos Pos, 
	SurfacePlacement placementConfig, Feature<C> featureIn, C featureConfig) {
		int Chance = placementConfig.surfaceChance;
		int i = placementConfig.genChance;
		boolean flag = false;
		if(i > 0) {
	    	for(int j = 0; j < i; ++j) {
	    		if(Rand.nextInt(Chance) == 0) {
	    			int k = Rand.nextInt(16);
	    			int l = Rand.nextInt(16);
	    			featureIn.func_212245_a(WorldIn, ChunkGen, Rand, WorldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, Pos.add(k, 0, l)), featureConfig);
	    			flag = true;
	    		}
	    	}
		}
		return flag;
	}
}
