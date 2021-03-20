package dav.mod.world.gen.decorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

public class TreeDecorator extends Decorator<SurfaceDecorator>{

	public TreeDecorator(Function<Dynamic<?>, ? extends SurfaceDecorator> configDeserializer) {
		super(configDeserializer);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, SurfaceDecorator config, BlockPos pos) {
		if(random.nextInt(config.genChance) == 0) {
			int i = random.nextInt(config.decoratorChance) + 1;
			return IntStream.range(0, i).mapToObj((MapObject) -> {
				int j = random.nextInt(16);
				int k = random.nextInt(16);
				return world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos.add(j, 0, k));
			});
		} else {
			return Stream.empty();
		}
	}

}
