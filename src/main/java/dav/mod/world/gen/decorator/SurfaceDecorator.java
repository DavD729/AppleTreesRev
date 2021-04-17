package dav.mod.world.gen.decorator;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SurfaceDecorator implements DecoratorConfig{
	
	public static final Codec<SurfaceDecorator> CODEC = Codec.intRange(1, 1000).fieldOf("genChance").xmap(SurfaceDecorator::new, (SurfaceDecorator) -> {
		return SurfaceDecorator.genChance;
	}).codec();
			
	public final int genChance;
	
	public SurfaceDecorator(int genChance) {
		this.genChance = genChance;
	}
}
