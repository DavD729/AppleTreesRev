package dav.mod.world.gen.decorator;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SurfaceDecorator implements DecoratorConfig{
	
	public static final Codec<SurfaceDecorator> CODEC = Codec.INT.fieldOf("genChance").xmap(SurfaceDecorator::new, (SurfaceDecorator) -> {
		return SurfaceDecorator.genChance;
	}).codec();
			
	public final int genChance;
	
	public SurfaceDecorator(int genChance) {
		this.genChance = genChance;
	}
	
	
	/* Old Method
	 * 
	 * public static final Codec<SurfaceDecorator> CODEC = RecordCodecBuilder.create((instance) -> {
	      return instance.group(Codec.INT.fieldOf("decoratorChance").forGetter((SurfaceDecorator) -> {
	          return SurfaceDecorator.decoratorChance;
	       }), Codec.INT.fieldOf("genChance").forGetter((SurfaceDecorator) -> {
	          return SurfaceDecorator.genChance;
	       })).apply(instance, SurfaceDecorator::new);
	    });
	public final int decoratorChance;
	public final int genChance;
	
	public SurfaceDecorator(int decoratorChance, int genChance) {
		this.decoratorChance = decoratorChance;
		this.genChance = genChance;
	}*/
	
}
