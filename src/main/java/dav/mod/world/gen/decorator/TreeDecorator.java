package dav.mod.world.gen.decorator;

import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class TreeDecorator extends Decorator<SurfaceDecorator>{

	public TreeDecorator(Codec<SurfaceDecorator> configCodec) {
		super(configCodec);
	}

	@Override
	public Stream<BlockPos> getPositions(DecoratorContext context, Random random, SurfaceDecorator config, BlockPos pos) {
		if(random.nextInt(config.genChance) == 0) {
			int i = random.nextInt(16);
			int j = random.nextInt(16);
			return Stream.of(pos.add(i, 0, j));
		} else {
			return Stream.empty();
		}
	}
}
