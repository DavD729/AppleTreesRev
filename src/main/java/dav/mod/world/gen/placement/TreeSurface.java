package dav.mod.world.gen.placement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.Placement;

public class TreeSurface extends Placement<SurfacePlacement>{

	public TreeSurface() {
		super(SurfacePlacement::deserialize);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, SurfacePlacement configIn, BlockPos pos) {
		if(random.nextInt(configIn.genChance) == 0) {
			int i = random.nextInt(configIn.surfaceChance + 1) + 1;
			return IntStream.range(0, i).mapToObj((MapObject) -> {
				int j = random.nextInt(16);
				int k = random.nextInt(16);
				return worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.add(j, 0, k));
			});
		} else {
			return Stream.empty();
		}
	}
}
