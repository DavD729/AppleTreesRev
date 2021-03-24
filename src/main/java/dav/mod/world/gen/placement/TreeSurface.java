package dav.mod.world.gen.placement;

import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

public class TreeSurface extends Placement<SurfacePlacement>{

	public TreeSurface(Codec<SurfacePlacement> codec) {
		super(codec);
	}

	@Override
	public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, SurfacePlacement config, BlockPos pos) {
		if(rand.nextInt(config.genChance) == 0) {
			int i = rand.nextInt(16);
		    int j = rand.nextInt(16);
		    return Stream.of(pos.add(i, 0, j));
		} else {
			return Stream.empty();
		}
	}
}
