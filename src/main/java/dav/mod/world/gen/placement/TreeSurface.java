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
	public Stream<BlockPos> getPositions(WorldDecoratingHelper Helper, Random Rand, SurfacePlacement Config, BlockPos Pos) {
		if(Rand.nextInt(Config.genChance) == 0) {
			int i = Rand.nextInt(16);
		    int j = Rand.nextInt(16);
		    return Stream.of(Pos.add(i, 0, j));
		} else {
			return Stream.empty();
		}
	}
}
