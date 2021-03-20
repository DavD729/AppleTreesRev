package dav.mod.world.gen.decorator;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SurfaceDecorator implements DecoratorConfig{
	
	public final int decoratorChance;
	public final int genChance;
	
	public SurfaceDecorator(int decoratorChance, int genChance) {
		this.decoratorChance = decoratorChance;
		this.genChance = genChance;
	}
	
	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<T>(ops, ops.createMap(ImmutableMap.of(ops.createString("decoratorchance"), ops.createInt(this.decoratorChance), ops.createString("genchance"), ops.createInt(this.genChance))));
	}
	
	public static SurfaceDecorator deserialize(Dynamic<?> Map) {
		int d = Map.get("decoratorchance").asInt(0);
	    int g = Map.get("genchance").asInt(0);
	    return new SurfaceDecorator(d, g);
	}
}
