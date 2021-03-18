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
	public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, 
		SurfacePlacement placementConfig, Feature<C> featureIn, C featureConfig) {
		int chance = placementConfig.surfaceChance;
		int i = placementConfig.genChance;
		boolean flag = false;
		if(i > 0) {
	    		for(int j = 0; j < i; ++j) {
	    			if(random.nextInt(chance) == 0) {
	    				int k = random.nextInt(16);
	    				int l = random.nextInt(16);
	    				featureIn.func_212245_a(worldIn, chunkGenerator, random, worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(k, 0, l)), featureConfig);
	    				flag = true;
	    			}
	    		}
		}
		return flag;
	}
}
